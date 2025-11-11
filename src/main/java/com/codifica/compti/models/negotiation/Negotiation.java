package com.codifica.compti.models.negotiation;

import com.codifica.compti.models.user.User;
import com.codifica.compti.models.userproduct.UserProduct;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "Negotiations")
@Getter
@Setter
public class Negotiation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    @JsonBackReference
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private UserProduct product;

    @Temporal(TemporalType.TIMESTAMP)
    private Date clickDate;

    private Boolean completed; // Indicates if the negotiation was finalized
    private Integer rating; // Rating given by the user
    private String comment; // Optional comment
}
