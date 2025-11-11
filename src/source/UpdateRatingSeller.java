//Classe para atualizar as notas dos vendedores

//SQL para calcular os valores

//SELECT
//COUNT(n.id) AS review_count,
//AVG(n.rating) AS average_rating
//FROM negotiations n
//JOIN user_products up ON n.product_id = up.id
//WHERE up.user_id = :sellerUserId
//AND n.completed = true;

//SQL para atualizar a tabela seller_ratings
//UPDATE seller_ratings sr
//        SET review_count = stats.review_count,
//                average_rating = stats.average_rating
//FROM (
//        SELECT
//                up.user_id AS seller_id,
//        COUNT(n.id) AS review_count,
//AVG(n.rating) AS average_rating
//FROM negotiations n
//JOIN user_products up ON n.product_id = up.id
//WHERE up.user_id = :sellerUserId
//AND n.completed = true
//GROUP BY up.user_id
//) AS stats
//WHERE sr.user_id = stats.seller_id;



public class UpdateRatingSeller {

//    Criar um método no NegotiationRepository para calcular a média e contagem:
    @Entity
    @Table(name = "seller_ratings")
    public class SellerRating {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "average_rating")
        private Double averageRating;

        @Column(name = "review_count")
        private Integer reviewCount;

        @Column(name = "user_id")
        private Long userId; // vendedor

        // getters/setters
    }

    // Criar o SellerRatingRepository para atualizar o registro:

    public interface NegotiationRepository extends JpaRepository<Negotiation, Long> {

        @Query("SELECT COUNT(n), AVG(n.rating) FROM Negotiation n " +
                "JOIN n.product p " +
                "WHERE p.user.id = :sellerUserId AND n.completed = true")
        Object[] calcStatsPerVendor(@Param("sellerUserId") Long sellerUserId);
    }

    public interface SellerRatingRepository extends JpaRepository<SellerRating, Long> {

        SellerRating findByUserId(Long userId);
    }

    // Criar um serviço para atualizar os campos:

    @Service
    public class SellerRatingService {

        private final NegotiationRepository negotiationRepository;
        private final SellerRatingRepository sellerRatingRepository;

        public SellerRatingService(NegotiationRepository negotiationRepository,
                                   SellerRatingRepository sellerRatingRepository) {
            this.negotiationRepository = negotiationRepository;
            this.sellerRatingRepository = sellerRatingRepository;
        }

        @Transactional
        public void updateSellerRating(Long sellerUserId) {
            Object[] stats = negotiationRepository.calcStatsPerVendor(sellerUserId);
            Long count = (Long) stats[0];
            Double average = (Double) stats[1];

            SellerRating sr = sellerRatingRepository.findByUserId(sellerUserId);
            if (sr == null) {
                sr = new SellerRating();
                sr.setUserId(sellerUserId);
            }

            sr.setReviewCount(count.intValue());

            // arredonda para 1 casa decimal
            if (average != null) {
                double rounded = Math.ceil(average * 10.0) / 10.0;
                sr.setAverageRating(rounded);
            } else {
                sr.setAverageRating(0.0);
            }

            sellerRatingRepository.save(sr);
        }
    }

    // Entidade SellerRating

    @Entity
    @Table(name = "seller_ratings")
    public class SellerRating {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "average_rating")
        private Double averageRating;

        @Column(name = "review_count")
        private Integer reviewCount;

        @Column(name = "user_id")
        private Long userId; // vendedor

        // getters/setters
    }
//    Quando salvar ou atualizar uma resenha (Negotiation):
//    Calcule COUNT e AVG de todas as resenhas completas dos produtos do vendedor.
//    Atualize a linha em seller_ratings com review_count e average_rating.
//    No Java, faça isso com um serviço que chama um @Query no repository + salva em SellerRating.

}
