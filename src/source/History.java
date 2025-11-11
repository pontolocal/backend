public class History {

//    Tabela para histórico de buscas
//    CREATE TABLE search_history (
//            id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
//            user_id BIGINT,              -- quem fez a busca (FK para users)
//    search_term VARCHAR(255),    -- termo buscado
//    category_id BIGINT,          -- se usou filtro de categoria
//    zip_code VARCHAR(20),        -- se usou filtro por zip
//    created_at TIMESTAMP DEFAULT now()

//    Explicação
//
//    user_id → opcional, caso queira saber quem buscou.
//    search_term → o que ele digitou.
//    category_id → filtro usado (se houver).
//    zip_code → filtro usado (se houver).
//    created_at → data/hora da busca.

//    Entidade JPA
    @Entity
    @Table(name = "search_history")
    public class SearchHistory {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private User user;

        @Column(name = "search_term")
        private String searchTerm;

        @Column(name = "category_id")
        private Long categoryId;

        @Column(name = "zip_code")
        private String zipCode;

        @Column(name = "created_at")
        private LocalDateTime createdAt = LocalDateTime.now();

        // getters/setters
    }

//    Repository para salvar buscas
    public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

        // ex.: top 5 termos mais buscados no geral
        @Query("SELECT sh.searchTerm, COUNT(sh.id) FROM SearchHistory sh " +
                "GROUP BY sh.searchTerm ORDER BY COUNT(sh.id) DESC")
        List<Object[]> termosMaisBuscados();

        // ex.: top termos de um usuário específico
        @Query("SELECT sh.searchTerm, COUNT(sh.id) FROM SearchHistory sh " +
                "WHERE sh.user.id = :userId GROUP BY sh.searchTerm ORDER BY COUNT(sh.id) DESC")
        List<Object[]> termosMaisBuscadosPorUsuario(@Param("userId") Long userId);
    }

//    Serviço para registrar
    @Service
    public class SearchService {

        private final SearchHistoryRepository searchHistoryRepository;

        public SearchService(SearchHistoryRepository searchHistoryRepository) {
            this.searchHistoryRepository = searchHistoryRepository;
        }

        public void registrarBusca(User user, String termo, Long categoryId, String zipCode) {
            SearchHistory sh = new SearchHistory();
            sh.setUser(user);
            sh.setSearchTerm(termo);
            sh.setCategoryId(categoryId);
            sh.setZipCode(zipCode);
            sh.setCreatedAt(LocalDateTime.now());
            searchHistoryRepository.save(sh);
        }
    }

//    Como usar no controller de busca
    @GetMapping("/buscar")
    public List<UserProduct> buscar(
            @RequestParam String termo,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String zipCode,
            Principal principal) {

        // 1. salva histórico
        User user = userRepository.findByEmail(principal.getName()); // exemplo
        searchService.registrarBusca(user, termo, categoryId, zipCode);

        // 2. executa a busca real nos produtos
        return userProductRepository.buscarProdutos(termo, categoryId, zipCode);
    }

}
