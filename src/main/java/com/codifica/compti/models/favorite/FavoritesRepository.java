package com.codifica.compti.models.favorite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, Long> {
    /**
     * Busca todos os favoritos de um usuário
     */
    List<Favorites> findByUserId(Long userId);

    /**
     * Busca um favorito específico de um usuário e produto
     */
    Optional<Favorites> findByUserIdAndProductId(Long userId, Long productId);

    /**
     * Verifica se um produto já está nos favoritos do usuário
     */
    boolean existsByUserIdAndProductId(Long userId, Long productId);

    /**
     * Deleta um favorito específico
     */
    void deleteByUserIdAndProductId(Long userId, Long productId);

    List<Favorites> findByUserIdOrderByCreatedAtDesc(Long userId);
}
