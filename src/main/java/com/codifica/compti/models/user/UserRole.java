package com.codifica.compti.models.user;

import lombok.Getter;

/**
 * Enumeração que representa os diferentes papéis (roles) dos usuários no sistema.
 * <p>
 * Cada papel define um nível específico de acesso e funcionalidades disponíveis para o usuário.
 * </p>
 *
 * @version 2.0
 * @since 2024
 */
@Getter
public enum UserRole {
    /**
     * Papel de administrador, com permissões completas no sistema.
     */
    ADMIN("admin"),
    /**
     * Papel de vítima, com acesso limitado a funcionalidades específicas.
     */
    INDIVIDUAL("individual"),
    /**
     * Papel de empresa, com permissões para gerenciar recursos de empresa.
     */
    BUSINESS("business");
    /**
     * Papel de órgão, com permissões específicas para gerenciar operações governamentais ou regulatórias.
     */

    private String role;

    /**
     * Construtor para associar uma string a cada papel.
     *
     * @param role o texto que representa o papel
     */
    UserRole(String role) {
        this.role = role;
    }

}
