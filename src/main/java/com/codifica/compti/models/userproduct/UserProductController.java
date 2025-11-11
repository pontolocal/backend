package com.codifica.compti.models.userproduct;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserProductController {

    private final UserProductServiceImpl userProductService;

    public UserProductController(UserProductServiceImpl userProductService) {
        this.userProductService = userProductService;
    }

    @PostMapping("/products/{user_id}/{category_id}")
    public ResponseEntity<?> saveProduct(
            @RequestBody @Valid UserProduct userProduct,
            @PathVariable("user_id") Long user_id,
            @PathVariable("category_id") Long category_id) {

        try {
            UserProduct savedProduct = userProductService.create(userProduct, user_id, category_id);
            UserProductDTO productDTO = new UserProductDTO(savedProduct);
            return ResponseEntity.ok(productDTO);
        } catch (IllegalArgumentException e) {
            // Retorna erro 403 se usuário não for BUSINESS
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        } catch (RuntimeException e) {
            // Outros erros (usuário ou categoria não encontrados)
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/products/user/{user_id}")
    public ResponseEntity<?> getUserProducts(@PathVariable("user_id") Long user_id) {
        try {
            return ResponseEntity.ok(userProductService.findByUserId(user_id));
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        }
    }

    @PutMapping("/products/{product_id}/user/{user_id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable("product_id") Long product_id,
            @PathVariable("user_id") Long user_id,
            @RequestBody @Valid UserProduct userProduct) {

        try {
            UserProduct updatedProduct = userProductService.update(product_id, userProduct, user_id);
            UserProductDTO productDTO = new UserProductDTO(updatedProduct);
            return ResponseEntity.ok(productDTO);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @DeleteMapping("/products/{product_id}/user/{user_id}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable("product_id") Long product_id,
            @PathVariable("user_id") Long user_id) {

        try {
            userProductService.delete(product_id, user_id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Produto deletado com sucesso");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

}
