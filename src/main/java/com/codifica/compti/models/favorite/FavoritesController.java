package com.codifica.compti.models.favorite;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoritesController {
    private final FavoritesServiceImpl favoritesService;

    /**
     * Adiciona um produto aos favoritos do usuário
     * POST /api/favorites/user/{user_id}/product/{product_id}
     *
     * @param userId ID do usuário
     * @param productId ID do produto a ser favoritado
     * @return FavoritesDTO com dados do favorito criado ou erro
     */
    @PostMapping("/user/{user_id}/product/{product_id}")
    public ResponseEntity<?> addFavorite(
            @PathVariable("user_id") Long userId,
            @PathVariable("product_id") Long productId) {

        try {
            FavoritesDTO favorite = favoritesService.addFavorite(userId, productId);
            return ResponseEntity.status(HttpStatus.CREATED).body(favorite);

        } catch (IllegalArgumentException e) {
            // Erros de validação (já favoritado, próprio produto, etc)
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

        } catch (RuntimeException e) {
            // Erros de não encontrado (usuário ou produto)
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Remove um produto dos favoritos do usuário
     * DELETE /api/favorites/user/{user_id}/product/{product_id}
     *
     * @param userId ID do usuário
     * @param productId ID do produto a ser removido dos favoritos
     * @return Mensagem de sucesso ou erro
     */
    @DeleteMapping("/user/{user_id}/product/{product_id}")
    public ResponseEntity<?> removeFavorite(
            @PathVariable("user_id") Long userId,
            @PathVariable("product_id") Long productId) {

        try {
            favoritesService.removeFavorite(userId, productId);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Produto removido dos favoritos com sucesso");
            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Lista todos os favoritos de um usuário
     * GET /api/favorites/user/{user_id}
     *
     * @param userId ID do usuário
     * @return Lista de FavoritesDTO com os produtos favoritados ou erro
     */
    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getUserFavorites(@PathVariable("user_id") Long userId) {
        try {
            List<FavoritesDTO> favorites = favoritesService.getUserFavorites(userId);
            return ResponseEntity.ok(favorites);

        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Verifica se um produto está nos favoritos do usuário
     * GET /api/favorites/user/{user_id}/product/{product_id}/check
     *
     * @param userId ID do usuário
     * @param productId ID do produto
     * @return JSON com campo "isFavorite" (true/false)
     */
    @GetMapping("/user/{user_id}/product/{product_id}/check")
    public ResponseEntity<?> checkFavorite(
            @PathVariable("user_id") Long userId,
            @PathVariable("product_id") Long productId) {

        boolean isFavorite = favoritesService.isFavorite(userId, productId);

        Map<String, Boolean> response = new HashMap<>();
        response.put("isFavorite", isFavorite);
        return ResponseEntity.ok(response);
    }

    /**
     * Alterna o estado de favorito (adiciona se não existe, remove se existe)
     * PUT /api/favorites/user/{user_id}/product/{product_id}/toggle
     *
     * @param userId ID do usuário
     * @param productId ID do produto
     * @return Status do favorito após a operação
     */
    @PutMapping("/user/{user_id}/product/{product_id}/toggle")
    public ResponseEntity<?> toggleFavorite(
            @PathVariable("user_id") Long userId,
            @PathVariable("product_id") Long productId) {

        try {
            boolean isFavorite = favoritesService.isFavorite(userId, productId);

            if (isFavorite) {
                // Remove dos favoritos
                favoritesService.removeFavorite(userId, productId);

                Map<String, Object> response = new HashMap<>();
                response.put("message", "Produto removido dos favoritos");
                response.put("isFavorite", false);
                return ResponseEntity.ok(response);

            } else {
                // Adiciona aos favoritos
                FavoritesDTO favorite = favoritesService.addFavorite(userId, productId);

                Map<String, Object> response = new HashMap<>();
                response.put("message", "Produto adicionado aos favoritos");
                response.put("isFavorite", true);
                response.put("favorite", favorite);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }

        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}
