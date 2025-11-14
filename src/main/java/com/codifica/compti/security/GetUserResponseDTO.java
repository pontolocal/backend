package com.codifica.compti.security;

public record GetUserResponseDTO(String login,String name, String bio,int role,String whatsapp, String social_media_link,
                                 String zip_code, String address_complement, String city, String address, String state, String document, String photo) {
}
