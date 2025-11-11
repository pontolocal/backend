public class SearchItem {
    // Realizando a conexão com as tabelas

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
        private User user;
        // getters/setters
    }

    @Table(name = "users")
    public class User {
        @Id
        private Long id;

        private String name;
        private String zipCode;
        // getters/setters
    }

    @Table(name = "product_categories")
    public class ProductCategory {
        @Id
        private Long id;
        private String name;
        // getters/setters
    }

    // Usando a Query em um repositório

    public interface UserProductRepository extends JpaRepository<UserProduct, Long> {

        @Query("SELECT up FROM UserProduct up " +
                "JOIN up.user u " +
                "WHERE up.category.id = :categoryId " +
                "AND up.type = true " +
                "AND u.zipCode = :zipCode")
        List<UserProduct> findByCategoryAndTypeAndUserZip(@Param("categoryId") Long categoryId,
                                                          @Param("zipCode") String zipCode);
    }

    // Usando no Service/Controller

    @RestController
    @RequestMapping("/produtos")
    public class UserProductController {

        private final UserProductRepository userProductRepository;

        public UserProductController(UserProductRepository userProductRepository) {
            this.userProductRepository = userProductRepository;
        }

        @GetMapping("/filtro")
        public List<UserProduct> buscarProdutos(@RequestParam Long categoryId,
                                                @RequestParam String zipCode) {
            return userProductRepository.findByCategoryAndTypeAndUserZip(categoryId, zipCode);
        }
    }




}
