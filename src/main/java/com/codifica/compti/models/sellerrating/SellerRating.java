package com.codifica.compti.models.sellerrating;



import com.codifica.compti.models.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "SellerRatings")
@Getter
@Setter
public class SellerRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Double totalRating; // Average rating of the seller

    private Integer reviewCount; // Total number of reviews received
}
