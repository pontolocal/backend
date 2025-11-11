package com.codifica.compti.models.userproduct;

import java.util.List;

public interface UserProductService {
    UserProduct create(UserProduct userProduct, Long user_id, Long category_id);
    UserProduct update(Long productId, UserProduct userProduct, Long userId);
    void delete(Long productId, Long userId);
    UserProduct findById(Long id);
    List<UserProduct> findAll();
    List<UserProductDTO> findByUserId(Long userId);
}
