public class AverageRatingItem {
//    Cálculo para nota média do item. O cálculo é feito com o somatório de todos os itens da tabela de negociações
//    que atendam os seguintes requisitos:
//
//    negotiations prduct.id = código do produto passado por parametro
//    E
//    negotiations completed = TRUE

    import jakarta.persistence.*;
    import java.time.LocalDateTime;

//  Entidade Negotiation
    @Entity
    @Table(name = "negotiations")
    public class Negotiation {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "click_date")
        private LocalDateTime clickDate;

        private String comment;

        private Boolean completed;

        private Integer rating;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "buyer_id") // chave estrangeira para users
        private User buyer;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "product_id") // chave estrangeira para user_products
        private UserProduct product;

        // Getters e Setters

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public LocalDateTime getClickDate() { return clickDate; }
        public void setClickDate(LocalDateTime clickDate) { this.clickDate = clickDate; }

        public String getComment() { return comment; }
        public void setComment(String comment) { this.comment = comment; }

        public Boolean getCompleted() { return completed; }
        public void setCompleted(Boolean completed) { this.completed = completed; }

        public Integer getRating() { return rating; }
        public void setRating(Integer rating) { this.rating = rating; }

        public User getBuyer() { return buyer; }
        public void setBuyer(User buyer) { this.buyer = buyer; }

        public UserProduct getProduct() { return product; }
        public void setProduct(UserProduct product) { this.product = product; }
    }

//    Entidade UserProduct (resumo)
    @Entity
    @Table(name = "user_products")
    public class UserProduct {
        @Id
        private Long id;

        private String description;
        private String image;
        private String name;
        private Double price;
        private Boolean type;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id")
        private ProductCategory category;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private User user; // dono do produto
        // getters/setters
    }

//    Entidade User (resumo)
    @Entity
    @Table(name = "users")
    public class User {
        @Id
        private Long id;

        private String name;
        private String zipCode; // mapeia zip_code
        // outros campos
        // getters/setters
    }

//  Repositório para média
    public interface NegotiationRepository extends JpaRepository<Negotiation, Long> {

        @Query("SELECT CEIL(AVG(n.rating) * 10) / 10 FROM Negotiation n " +
                "WHERE n.product.id = :productId AND n.completed = true")
        Double calcMediaPerProduct(@Param("productId") Long productId);

        @Query("SELECT n.product.id, CEIL(AVG(n.rating) * 10) / 10 " +
                "FROM Negotiation n WHERE n.completed = true GROUP BY n.product.id")
        List<Object[]> calcMediaForAllProducts();
    }


}
