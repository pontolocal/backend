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

    public UserProductDTO(UserProduct product) {
        this.id = product.getId();
        this.name = product.getName();
        this.type = product.getType();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.image = product.getImage();
    }
}
