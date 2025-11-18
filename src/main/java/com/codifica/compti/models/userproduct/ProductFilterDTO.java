package com.codifica.compti.models.userproduct;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilterDTO {
    // Filtro de localização
    private String searchBy;  // "endereco", "cep", "cidade"
    private String cep;       // CEP do usuário ou para buscar
    private String city;      // Cidade para buscar
    private String state;     // Estado (opcional)

    // Filtro de categoria
    private Long categoryId;  // ID da categoria (null = todas)

    // Filtro de preço
    private Double minPrice;
    private Double maxPrice;

    // Filtro de tipo
    private Boolean type;     // true = Produto, false = Serviço, null = ambos

    // Ordenação
    private String sortBy;    // "relevance", "price_asc", "price_desc", "recent"

    // Busca por texto
    private String search;    // Busca por nome do produto

    // Paginação
    private Integer page;     // Número da página (default: 0)
    private Integer size;     // Itens por página (default: 20)
}
