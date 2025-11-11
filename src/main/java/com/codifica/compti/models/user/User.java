package com.codifica.compti.models.user;

import com.codifica.compti.models.favorite.Favorites;
import com.codifica.compti.models.userproduct.UserProduct;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User implements UserDetails {
    /**
     * Identificador único do usuário.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome de login do usuário, pode ser tanto o cpf como email.
     */
    private String email;
    /**
     * Senha do usuário, armazenada em formato criptografado.
     */
    private String password;

    private String name;

    /**
     * Papel do usuário, que define suas permissões e acessos.
     */
    private UserRole role;
    private String whatsapp;
    private String socialMediaLink;
    private String zipCode;
    private String addressComplement;
    private String document; // CPF ou CNPJ
    private String city;
    private String state;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Favorites> favorites;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore  // ✅ Mesmo nome usado em UserProduct
    private List<UserProduct> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getSocialMediaLink() {
        return socialMediaLink;
    }

    public void setSocialMediaLink(String socialMediaLink) {
        this.socialMediaLink = socialMediaLink;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddressComplement() {
        return addressComplement;
    }

    public void setAddressComplement(String addressComplement) {
        this.addressComplement = addressComplement;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    private String photo; // Opcional

    /**
     * Construtor para criar um usuário com login, senha e papel.
     *
     * @param email    nome de login do usuário
     * @param password senha do usuário
     * @param role     papel atribuído ao usuário
     */
    public User(String email, String password, UserRole role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * Retorna as autoridades concedidas ao usuário com base em seu papel.
     *
     * @return uma coleção de {@link GrantedAuthority} representando as permissões do usuário
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),
                new SimpleGrantedAuthority("ROLE_INDIVIDUAL"),
                new SimpleGrantedAuthority("ROLE_BUSINESS"));
        if (this.role == UserRole.INDIVIDUAL) return List.of(new SimpleGrantedAuthority("ROLE_INDIVIDUAL"));
        if (this.role == UserRole.BUSINESS) return List.of(new SimpleGrantedAuthority("ROLE_BUSINESS"));


        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * Retorna o nome de login do usuário.
     *
     * @return o nome de login do usuário
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Indica se a conta do usuário não expirou.
     *
     * @return {@code true} se a conta não expirou; caso contrário, {@code false}
     */
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    /**
     * Indica se a conta do usuário não está bloqueada.
     *
     * @return {@code true} se a conta não está bloqueada; caso contrário, {@code false}
     */
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    /**
     * Indica se as credenciais do usuário (senha) não expiraram.
     *
     * @return {@code true} se as credenciais não expiraram; caso contrário, {@code false}
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    /**
     * Indica se a conta do usuário está habilitada.
     *
     * @return {@code true} se a conta está habilitada; caso contrário, {@code false}
     */
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }


}
