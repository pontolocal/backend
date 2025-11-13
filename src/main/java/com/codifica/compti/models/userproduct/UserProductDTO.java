package com.codifica.compti.models.userproduct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProductDTO {
    private Long id;
    private String name;
    private Boolean type; // true = Produto, false = Serviço
    private Double price;
    private String description;
    private String image; // URL da imagem

    //campos para os filtros
    private Long categoryId;
    private String categoryName;
    private Long userId;
    private String userName;
    private String userCity;
    private String userState;
    private String userZipCode;

    public UserProductDTO(UserProduct product) {
        this.id = product.getId();
        this.name = product.getName();
        this.type = product.getType();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.image = product.getImage();

        // Preenche dados da categoria
        if (product.getCategory() != null) {
            this.categoryId = product.getCategory().getId();
            this.categoryName = product.getCategory().getName();
        }

        // Preenche dados do usuário/vendedor
        if (product.getUser() != null) {
            this.userId = product.getUser().getId();
            this.userName = product.getUser().getName();
            this.userCity = product.getUser().getCity();
            this.userState = product.getUser().getState();
            this.userZipCode = product.getUser().getZipCode();
        }

    }
}
