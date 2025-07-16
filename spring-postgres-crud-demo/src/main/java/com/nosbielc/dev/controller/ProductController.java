package com.nosbielc.dev.controller;

import com.nosbielc.dev.dto.ProductCreateDTO;
import com.nosbielc.dev.dto.ProductUpdateDTO;
import com.nosbielc.dev.entity.Product;
import com.nosbielc.dev.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductCreateDTO productCreateDTO) {
        log.info("REST request to create product: {}", productCreateDTO.getName());
        Product product = productService.createProduct(productCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        log.info("REST request to get product by ID: {}", id);
        Product product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        log.info("REST request to get all products");
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Product>> getAllProducts(Pageable pageable) {
        log.info("REST request to get all products with pagination");
        Page<Product> products = productService.findAll(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Product>> getActiveProducts() {
        log.info("REST request to get active products");
        List<Product> products = productService.findActiveProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProductsByName(@RequestParam String name) {
        log.info("REST request to search products by name: {}", name);
        List<Product> products = productService.findByNameContaining(name);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(@RequestParam BigDecimal minPrice,
                                                                @RequestParam BigDecimal maxPrice) {
        log.info("REST request to get products by price range: {} - {}", minPrice, maxPrice);
        List<Product> products = productService.findByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<Product>> getLowStockProducts(@RequestParam(defaultValue = "10") Integer threshold) {
        log.info("REST request to get low stock products with threshold: {}", threshold);
        List<Product> products = productService.findLowStockProducts(threshold);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countActiveProducts() {
        log.info("REST request to count active products");
        long count = productService.countActiveProducts();
        return ResponseEntity.ok(count);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                @Valid @RequestBody ProductUpdateDTO productUpdateDTO) {
        log.info("REST request to update product with ID: {}", id);
        Product product = productService.updateProduct(id, productUpdateDTO);
        return ResponseEntity.ok(product);
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable Long id, @RequestParam Integer quantity) {
        log.info("REST request to update stock for product ID: {} to quantity: {}", id, quantity);
        Product product = productService.updateStock(id, quantity);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.info("REST request to delete product with ID: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateProduct(@PathVariable Long id) {
        log.info("REST request to deactivate product with ID: {}", id);
        productService.deactivateProduct(id);
        return ResponseEntity.ok().build();
    }
}