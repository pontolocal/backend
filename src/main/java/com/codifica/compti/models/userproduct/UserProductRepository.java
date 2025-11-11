package com.codifica.compti.models.userproduct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserProductRepository extends JpaRepository<UserProduct, Long> {
    /**
     * Busca todos os produtos de um usuário específico
     * @param userId ID do usuário
     * @return Lista de produtos do usuário
     */
    List<UserProduct> findByUserId(Long userId);

    /**
     * Busca todos os produtos de uma categoria específica
     * @param categoryId ID da categoria
     * @return Lista de produtos da categoria
     */
    List<UserProduct> findByCategoryId(Long categoryId);

    /**
     * Busca produtos de um usuário filtrados por tipo (produto ou serviço)
     * @param userId ID do usuário
     * @param type true = Produto, false = Serviço
     * @return Lista de produtos/serviços do usuário
     */
    List<UserProduct> findByUserIdAndType(Long userId, Boolean type);

    /**
     * Busca produtos de uma categoria filtrados por tipo
     * @param categoryId ID da categoria
     * @param type true = Produto, false = Serviço
     * @return Lista de produtos/serviços da categoria
     */
    List<UserProduct> findByCategoryIdAndType(Long categoryId, Boolean type);

    /**
     * Busca produtos por nome (case insensitive)
     * @param name Nome ou parte do nome do produto
     * @return Lista de produtos encontrados
     */
    List<UserProduct> findByNameContainingIgnoreCase(String name);

    /**
     * Busca produtos de um usuário por nome
     * @param userId ID do usuário
     * @param name Nome ou parte do nome
     * @return Lista de produtos encontrados
     */
    List<UserProduct> findByUserIdAndNameContainingIgnoreCase(Long userId, String name);

    /**
     * Busca produtos em uma faixa de preço
     * @param minPrice Preço mínimo
     * @param maxPrice Preço máximo
     * @return Lista de produtos na faixa de preço
     */
    List<UserProduct> findByPriceBetween(Double minPrice, Double maxPrice);

    /**
     * Busca produtos de um usuário em uma faixa de preço
     * @param userId ID do usuário
     * @param minPrice Preço mínimo
     * @param maxPrice Preço máximo
     * @return Lista de produtos na faixa de preço
     */
    List<UserProduct> findByUserIdAndPriceBetween(Long userId, Double minPrice, Double maxPrice);

    /**
     * Conta quantos produtos um usuário possui
     * @param userId ID do usuário
     * @return Quantidade de produtos
     */
    Long countByUserId(Long userId);

    /**
     * Conta quantos produtos existem em uma categoria
     * @param categoryId ID da categoria
     * @return Quantidade de produtos
     */
    Long countByCategoryId(Long categoryId);

    /**
     * Verifica se um usuário possui algum produto
     * @param userId ID do usuário
     * @return true se possui produtos, false caso contrário
     */
    boolean existsByUserId(Long userId);

    /**
     * Busca os produtos mais recentes de um usuário (limitado)
     * Usando query customizada para ordenação
     */
    @Query("SELECT p FROM UserProduct p WHERE p.user.id = :userId ORDER BY p.id DESC")
    List<UserProduct> findLatestProductsByUserId(@Param("userId") Long userId);

    /**
     * Busca produtos por categoria e usuário
     * @param userId ID do usuário
     * @param categoryId ID da categoria
     * @return Lista de produtos
     */
    List<UserProduct> findByUserIdAndCategoryId(Long userId, Long categoryId);

}
