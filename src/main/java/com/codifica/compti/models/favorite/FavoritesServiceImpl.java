package com.codifica.compti.models.favorite;

import com.codifica.compti.models.user.User;
import com.codifica.compti.models.user.UserRepository;
import com.codifica.compti.models.userproduct.UserProduct;
import com.codifica.compti.models.userproduct.UserProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoritesServiceImpl implements FavoritesService{

    private final FavoritesRepository favoritesRepository;
    private final UserRepository userRepository;
    private final UserProductRepository productRepository;

    public FavoritesServiceImpl(FavoritesRepository favoritesRepository, UserRepository userRepository, UserProductRepository productRepository) {
        this.favoritesRepository = favoritesRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public FavoritesDTO addFavorite(Long userId, Long productId) {
        // Verifica se o usuário existe
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verifica se o produto existe
        UserProduct product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Verifica se já existe nos favoritos
        if (favoritesRepository.existsByUserIdAndProductId(userId, productId)) {
            throw new IllegalArgumentException("Este produto já está nos seus favoritos");
        }

        // Verifica se o usuário está tentando favoritar seu próprio produto
        if (product.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Você não pode favoritar seus próprios produtos");
        }

        // Cria o favorito
        Favorites favorite = Favorites.builder()
                .user(user)
                .product(product)
                .build();

        Favorites savedFavorite = favoritesRepository.save(favorite);
        return new FavoritesDTO(savedFavorite);
    }

    @Override
    @Transactional
    public void removeFavorite(Long userId, Long productId) {
        // Verifica se o usuário existe
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("Usuário não encontrado");
        }

        // Verifica se o produto existe
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Produto não encontrado");
        }

        // Verifica se o favorito existe
        Favorites favorite = favoritesRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new RuntimeException("Favorito não encontrado"));

        favoritesRepository.delete(favorite);
    }

    @Override
    public List<FavoritesDTO> getUserFavorites(Long userId) {
        // Verifica se o usuário existe
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("Usuário não encontrado");
        }
        // Busca os favoritos ordenados por data (mais recentes primeiro)
        return favoritesRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(FavoritesDTO::new)
                .collect(Collectors.toList());

    }

    @Override
    public boolean isFavorite(Long userId, Long productId) {
        return favoritesRepository.existsByUserIdAndProductId(userId, productId);
    }
}
