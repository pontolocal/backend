package com.codifica.compti.models.userproduct;

import com.codifica.compti.models.productcategory.ProductCategory;
import com.codifica.compti.models.productcategory.ProductCategoryRepository;
import com.codifica.compti.models.user.User;
import com.codifica.compti.models.user.UserRepository;
import com.codifica.compti.models.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProductServiceImpl implements UserProductService {

    private final UserRepository userRepository;
    private final UserProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Override
    public UserProduct create(UserProduct userProduct, Long user_id, Long category_id) {
        // Busca o usuário
        User user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Validação: Apenas BUSINESS pode criar produtos
        //if (user.getRole() != UserRole.BUSINESS) {
        //    throw new IllegalArgumentException(
        //            "Apenas usuários do tipo BUSINESS (CNPJ) podem criar produtos. " +
        //                    "Usuários INDIVIDUAL (CPF) podem apenas favoritar produtos."
        //    );
        //}

        // Busca a categoria
        ProductCategory category = productCategoryRepository.findById(category_id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        // Configura o produto
        userProduct.setUser(user);
        userProduct.setCategory(category);

        // Salva e retorna o produto
        return productRepository.save(userProduct);
    }

    @Override
    public UserProduct update(Long productId, UserProduct userProduct, Long userId) {
        // Busca o usuário
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Valida se é BUSINESS
        //if (user.getRole() != UserRole.BUSINESS) {
        //    throw new IllegalArgumentException(
        //            "Apenas usuários do tipo BUSINESS (CNPJ) podem atualizar produtos."
        //    );
        //}

        // Busca o produto existente
        UserProduct existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Verifica se o produto pertence ao usuário
        if (!existingProduct.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException(
                    "Você não tem permissão para atualizar este produto."
            );
        }

        // Atualiza os campos
        existingProduct.setName(userProduct.getName());
        existingProduct.setType(userProduct.getType());
        existingProduct.setPrice(userProduct.getPrice());
        existingProduct.setDescription(userProduct.getDescription());

        if (userProduct.getImage() != null) {
            existingProduct.setImage(userProduct.getImage());
        }

        // Se a categoria foi alterada
        if (userProduct.getCategory() != null && userProduct.getCategory().getId() != null) {
            ProductCategory newCategory = productCategoryRepository
                    .findById(userProduct.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            existingProduct.setCategory(newCategory);
        }

        return productRepository.save(existingProduct);
    }

    @Override
    public void delete(Long productId, Long userId) {
        // Busca o usuário
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Valida se é BUSINESS
        //if (user.getRole() != UserRole.BUSINESS) {
        //    throw new IllegalArgumentException(
        //            "Apenas usuários do tipo BUSINESS (CNPJ) podem deletar produtos."
        //    );
        //}

        // Busca o produto
        UserProduct product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Verifica se o produto pertence ao usuário
        if (!product.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException(
                    "Você não tem permissão para deletar este produto."
            );
        }

        productRepository.delete(product);
    }

    @Override
    public UserProduct findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    @Override
    public List<UserProduct> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<UserProductDTO> findByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Valida se é BUSINESS
        //if (user.getRole() != UserRole.BUSINESS) {
        //    throw new IllegalArgumentException(
        //            "Usuários INDIVIDUAL (CPF) não possuem produtos."
        //    );
        //}

        return productRepository.findByUserId(userId).stream()
                .map(UserProductDTO::new)
                .collect(Collectors.toList());
    }
    }



