package com.codifica.compti.security;

import com.codifica.compti.models.user.UserRole;

public record GetResponseDTO(String login,String name, String whatsapp, String social_media_link,
                             String zip_code, String address_complement, String city, String address, String state, String document, String photo) {
}
