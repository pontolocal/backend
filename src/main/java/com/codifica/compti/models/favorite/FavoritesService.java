package com.codifica.compti.models.favorite;

import java.util.List;

public interface FavoritesService {
    FavoritesDTO addFavorite(Long userId, Long productId);
    void removeFavorite(Long userId, Long productId);
    List<FavoritesDTO> getUserFavorites(Long userId);
    boolean isFavorite(Long userId, Long productId);
}
