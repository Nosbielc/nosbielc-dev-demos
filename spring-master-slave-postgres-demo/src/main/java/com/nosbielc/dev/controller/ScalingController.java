package com.nosbielc.dev.controller;

import com.nosbielc.dev.entity.Product;
import com.nosbielc.dev.entity.User;
import com.nosbielc.dev.service.ProductService;
import com.nosbielc.dev.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ScalingController {

    private final UserService userService;
    private final ProductService productService;

    public ScalingController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    // ===== USER ENDPOINTS =====

    // READ Operations (vão para Slave)
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/users/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String username) {
        List<User> users = userService.searchUsersByUsername(username);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/count")
    public ResponseEntity<Map<String, Long>> getUserCount() {
        long count = userService.getUserCount();
        return ResponseEntity.ok(Map.of("count", count));
    }

    // WRITE Operations (vão para Master)
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody Map<String, String> request) {
        User user = userService.createUser(
                request.get("username"),
                request.get("email")
        );
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            User user = userService.updateUser(
                    id,
                    request.get("username"),
                    request.get("email")
            );
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ===== PRODUCT ENDPOINTS =====

    // READ Operations (vão para Slave)
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String name) {
        List<Product> products = productService.searchProductsByName(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/price-range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice) {
        List<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }

    // WRITE Operations (vão para Master)
    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Map<String, Object> request) {
        Product product = productService.createProduct(
                (String) request.get("name"),
                (String) request.get("description"),
                new BigDecimal(request.get("price").toString()),
                (Integer) request.get("stockQuantity")
        );
        return ResponseEntity.ok(product);
    }

    @PutMapping("/products/{id}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable Long id, @RequestBody Map<String, Integer> request) {
        try {
            Product product = productService.updateStock(id, request.get("stockQuantity"));
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ===== DEMO ENDPOINTS =====

    @GetMapping("/demo/load-test")
    public ResponseEntity<Map<String, Object>> loadTest() {
        long startTime = System.currentTimeMillis();

        // Simula operações de leitura intensiva (vão para Slave)
        List<User> users = userService.getAllUsers();
        List<Product> products = productService.getAllProducts();
        long userCount = userService.getUserCount();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        return ResponseEntity.ok(Map.of(
                "users_found", users.size(),
                "products_found", products.size(),
                "total_users", userCount,
                "duration_ms", duration,
                "message", "Operações de leitura executadas no Slave DB"
        ));
    }

    @PostMapping("/demo/bulk-operations")
    public ResponseEntity<Map<String, Object>> bulkOperations() {
        long startTime = System.currentTimeMillis();

        // Simula operações de escrita (vão para Master)
        User newUser = userService.createUser("test_user_" + System.currentTimeMillis(), "test@demo.com");
        Product newProduct = productService.createProduct(
                "Demo Product " + System.currentTimeMillis(),
                "Demo description",
                new BigDecimal("99.99"),
                10
        );

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        return ResponseEntity.ok(Map.of(
                "created_user_id", newUser.getId(),
                "created_product_id", newProduct.getId(),
                "duration_ms", duration,
                "message", "Operações de escrita executadas no Master DB"
        ));
    }
}
