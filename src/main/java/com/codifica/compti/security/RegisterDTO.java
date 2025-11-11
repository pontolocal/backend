package com.codifica.compti.security;

import com.codifica.compti.models.user.UserRole;

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
 * @since 2025
 */
public record RegisterDTO(String login, String password, UserRole role, String name, String whatsapp, String social_media_link,
                          String zip_code, String address_complement,String city, String address, String state, String document,String photo) {
}
