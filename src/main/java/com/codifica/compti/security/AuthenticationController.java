package com.codifica.compti.security;


import com.codifica.compti.models.user.User;
import com.codifica.compti.models.user.UserRepository;
import com.codifica.compti.models.user.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


/**
 * Controlador REST para autenticação e registro de usuários.
 * <p>
 * Este controlador fornece endpoints para login e registro de novos usuários,
 * incluindo a geração de tokens JWT para autenticação.
 * </p>
 *
 * @version 2.0
 * @since 2025
 */
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * Realiza a autenticação de um usuário e retorna um token JWT se as credenciais forem válidas.
     *
     * @param data objeto {@link AuthenticationDTO} contendo as credenciais do usuário
     * @return uma resposta HTTP com o token JWT, as autoridades do usuário e seu ID se a autenticação for bem-sucedida
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        User user = (User) userRepository.findByEmail(data.login());
        //System.out.println(auth.getPrincipal());
        //System.out.println(auth.getDetails());
        //System.out.println(auth.isAuthenticated());

        // Gera um token JWT para o usuário autenticado
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token, auth.getAuthorities(), user.getId()));
    }

    /**
     * Registra um novo usuário no sistema com as informações fornecidas.
     *
     * @param data objeto {@link RegisterDTO} contendo as informações de login, senha e papel do usuário
     * @return uma resposta HTTP com o ID e o login do novo usuário, ou erro 400 se o login já existir
     */
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if (this.userRepository.findByEmail(data.login()) != null) return ResponseEntity.badRequest().build();
        String crippass = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), crippass, data.role());// Login já existe
        newUser.setName(data.name());
        newUser.setWhatsapp(data.whatsapp());
        newUser.setDocument(data.document());
        newUser.setZipCode(data.zip_code());
        newUser.setAddressComplement(data.address_complement());
        newUser.setPhoto(data.photo());
        newUser.setSocialMediaLink(data.social_media_link());
        newUser.setCity(data.city());
        newUser.setState(data.state());
        newUser.setAddress(data.address());

        this.userRepository.save(newUser);
        return ResponseEntity.ok(new RegisterResponseDTO(newUser.getId(), newUser.getEmail()));
    }

    //String login,String name, String whatsapp, String social_media_link,
    //                             String zip_code, String address_complement, String city, String address, String state, String document, String photo
    @PutMapping("update/{user_id}")
    public ResponseEntity<GetResponseDTO> update(@RequestBody User user, @PathVariable("user_id") Long user_id) {
       User updatedUser = userServiceImpl.update(user, user_id);
        return ResponseEntity.ok(new GetResponseDTO(updatedUser.getEmail(),updatedUser.getName(),updatedUser.getWhatsapp(),updatedUser.getSocialMediaLink(),updatedUser.getZipCode(),updatedUser.getAddressComplement()
        ,updatedUser.getCity(),updatedUser.getAddress(),updatedUser.getState(),updatedUser.getDocument(),updatedUser.getPhoto()));
    }

    @DeleteMapping("delete/{user_id}")
    public ResponseEntity<User> delete(@PathVariable("user_id") Long id) {
        userServiceImpl.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("get/{user_id}")
    public ResponseEntity<GetResponseDTO> get(@PathVariable("user_id") Long userId) {
        User getUser = userServiceImpl.view(userId);
        return ResponseEntity.ok(new GetResponseDTO(getUser.getEmail(),getUser.getName(),getUser.getWhatsapp(),getUser.getSocialMediaLink(),getUser.getZipCode(),getUser.getAddressComplement()
                ,getUser.getCity(),getUser.getAddress(),getUser.getState(),getUser.getDocument(),getUser.getPhoto()));
    }
}


