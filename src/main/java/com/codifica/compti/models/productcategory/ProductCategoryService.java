package com.codifica.compti.models.productcategory;

import com.codifica.compti.models.userproduct.UserProduct;
import com.codifica.compti.models.userproduct.UserProductDTO;

import java.util.List;

public interface ProductCategoryService {

    List<ProductCategoryDTO> getAllCategoriesAsDTO();


}
