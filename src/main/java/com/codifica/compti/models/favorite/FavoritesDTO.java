package com.codifica.compti.models.favorite;

import com.codifica.compti.models.userproduct.UserProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoritesDTO {
    private Long id;
    private Long userId;
    private UserProductDTO product;
    private LocalDateTime createdAt;

    public FavoritesDTO(Favorites favorite) {
        this.id = favorite.getId();
        this.userId = favorite.getUser().getId();
        this.product = new UserProductDTO(favorite.getProduct());
        this.createdAt = favorite.getCreatedAt();

    }
}
