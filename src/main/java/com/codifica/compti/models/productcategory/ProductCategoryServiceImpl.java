package com.codifica.compti.models.productcategory;

import com.codifica.compti.models.userproduct.UserProduct;
import com.codifica.compti.models.userproduct.UserProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public List<ProductCategoryDTO> getAllCategoriesAsDTO() {
        return productCategoryRepository.findAll().stream()
                .map(ProductCategoryDTO::new)
                .collect(Collectors.toList());
    }




}
