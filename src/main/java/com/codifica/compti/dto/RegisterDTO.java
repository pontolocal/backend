package com.codifica.compti.dto;

import org.springframework.web.multipart.MultipartFile;

import com.codifica.compti.models.user.UserRole;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) para representar os dados de registro de um novo usuário.
 * <p>
 * Esta classe encapsula as informações necessárias para registrar um usuário, incluindo
 * o login, a senha e o papel (role) do usuário.
 * </p>
 *
 * @param login    o nome de login do usuário
 * @param password a senha do usuário
 * @param role     o papel do usuário, representado por {@link UserRole}
 * @version 2.0
 * @since 2024
 */
public record RegisterDTO(
    @NotBlank
    String login, 
    @NotBlank
    String password, 
    @NotNull
    UserRole role, 
    @NotBlank
    String name, 
    @NotBlank
    String whatsapp,
    @NotBlank
    String social_media_link,
    @NotBlank
    String zip_code,
    @NotBlank
    String address_complement, 
    @NotBlank
    String document,
    MultipartFile photo) {
}
