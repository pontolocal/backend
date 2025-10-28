package com.codifica.compti.models.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codifica.compti.dto.RegisterDTO;
import com.codifica.compti.exceptions.UserAlreadyExistsException;

import java.util.Optional;

/**
 * Implementação do serviço {@link UserService} para operações relacionadas à entidade {@link User}.
 * <p>
 * Esta classe fornece a lógica de negócios para criação, visualização e atualização de usuários,
 * incluindo criptografia de senha antes de salvar.
 * </p>
 *
 * @version 2.0
 * @since 2024
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    /**
     * Cria um novo usuário no sistema com senha criptografada.
     *
     * @param user o objeto {@link User} contendo os dados do novo usuário
     * @return o objeto {@link User} recém-criado e salvo
     */
    @Override
    public User register(RegisterDTO registerDTO) {
        if (userRepository.findByEmail(registerDTO.login()) != null) {
            throw new UserAlreadyExistsException(registerDTO.login() + " Already exists");
        }

        User newUser = new User(registerDTO.login(), passwordEncoder.encode(registerDTO.password()), registerDTO.role());// Login já existe
        newUser.setName(registerDTO.name());
        newUser.setWhatsapp(registerDTO.whatsapp());
        newUser.setDocument(registerDTO.document());
        newUser.setZipCode(registerDTO.zip_code());
        newUser.setAddressComplement(registerDTO.address_complement());
        newUser.setSocialMediaLink(registerDTO.social_media_link());
        
        return userRepository.save(newUser);
    }

    /**
     * Recupera os dados de um usuário específico pelo ID.
     *
     * @param id o ID do usuário a ser visualizado
     * @return o objeto {@link User} correspondente ao ID fornecido, ou null se não encontrado
     */
    @Override
    public User view(Long id) {
        Optional<User> opuser = userRepository.findById(id);
        User user = opuser.get();
        // Check if the user exists and if the password is correct
        if (user.getId() != null) {
            return user;
        }
        return null;
    }

    /**
     * Atualiza a senha de um usuário existente pelo ID, criptografando-a antes de salvar.
     *
     * @param user o objeto {@link User} contendo os dados atualizados do usuário
     * @param id   o ID do usuário a ser atualizado
     * @return o objeto {@link User} atualizado
     * @throws RuntimeException se o usuário com o ID fornecido não for encontrado
     */
    @Override
    public User update(User user, Long id) {
        User edit_user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));
        edit_user.setName(user.getName());
        edit_user.setWhatsapp(user.getWhatsapp());
        edit_user.setDocument(user.getDocument());
        edit_user.setZipCode(user.getZipCode());
        edit_user.setAddressComplement(user.getAddressComplement());
        edit_user.setPhoto(user.getPhoto());
        edit_user.setSocialMediaLink(user.getSocialMediaLink());
        return userRepository.save(edit_user);
        //dar upgrade em tudo menos no password
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }


}
