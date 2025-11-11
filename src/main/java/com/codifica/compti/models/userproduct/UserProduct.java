package com.codifica.compti.models.userproduct;


import com.codifica.compti.models.favorite.Favorites;
import com.codifica.compti.models.productcategory.ProductCategory;
import com.codifica.compti.models.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "UserProducts")
@Getter
@Setter
public class UserProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    //@JsonBackReference("user-products")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    //@JsonBackReference("category-products")
    @JsonIgnore
    private ProductCategory category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonManagedReference("product-favorites")
    @JsonIgnore
    private List<Favorites> favorites;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean type; // true = Product, false = Service

    @Column(nullable = false)
    private Double price;

    @Column(length = 1000)
    private String description;

    private String image;
}
