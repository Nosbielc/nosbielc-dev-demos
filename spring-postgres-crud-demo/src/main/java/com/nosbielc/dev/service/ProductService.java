package com.nosbielc.dev.service;

import com.nosbielc.dev.dto.ProductCreateDTO;
import com.nosbielc.dev.dto.ProductUpdateDTO;
import com.nosbielc.dev.entity.Product;
import com.nosbielc.dev.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public Product createProduct(ProductCreateDTO productCreateDTO) {
        log.info("Creating product: {}", productCreateDTO.getName());
        
        Product product = new Product();
        product.setName(productCreateDTO.getName());
        product.setDescription(productCreateDTO.getDescription());
        product.setPrice(productCreateDTO.getPrice());
        product.setStockQuantity(productCreateDTO.getStockQuantity());
        product.setActive(true);

        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        log.info("Finding product by ID: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        log.info("Finding all products");
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Product> findAll(Pageable pageable) {
        log.info("Finding all products with pagination");
        return productRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Product> findActiveProducts() {
        log.info("Finding active products");
        return productRepository.findByActiveTrue();
    }

    @Transactional(readOnly = true)
    public List<Product> findByNameContaining(String name) {
        log.info("Finding products by name containing: {}", name);
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public List<Product> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        log.info("Finding products by price range: {} - {}", minPrice, maxPrice);
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Transactional(readOnly = true)
    public List<Product> findLowStockProducts(Integer threshold) {
        log.info("Finding low stock products with threshold: {}", threshold);
        return productRepository.findLowStockProducts(threshold);
    }

    @Transactional(readOnly = true)
    public long countActiveProducts() {
        log.info("Counting active products");
        return productRepository.countByActiveTrue();
    }

    public Product updateProduct(Long id, ProductUpdateDTO productUpdateDTO) {
        log.info("Updating product with ID: {}", id);
        
        Product product = findById(id);

        if (productUpdateDTO.getName() != null) {
            product.setName(productUpdateDTO.getName());
        }
        if (productUpdateDTO.getDescription() != null) {
            product.setDescription(productUpdateDTO.getDescription());
        }
        if (productUpdateDTO.getPrice() != null) {
            product.setPrice(productUpdateDTO.getPrice());
        }
        if (productUpdateDTO.getStockQuantity() != null) {
            product.setStockQuantity(productUpdateDTO.getStockQuantity());
        }
        if (productUpdateDTO.getActive() != null) {
            product.setActive(productUpdateDTO.getActive());
        }

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        log.info("Deleting product with ID: {}", id);
        Product product = findById(id);
        productRepository.delete(product);
    }

    public void deactivateProduct(Long id) {
        log.info("Deactivating product with ID: {}", id);
        Product product = findById(id);
        product.setActive(false);
        productRepository.save(product);
    }

    public Product updateStock(Long id, Integer quantity) {
        log.info("Updating stock for product ID: {} to quantity: {}", id, quantity);
        Product product = findById(id);
        product.setStockQuantity(quantity);
        return productRepository.save(product);
    }
}