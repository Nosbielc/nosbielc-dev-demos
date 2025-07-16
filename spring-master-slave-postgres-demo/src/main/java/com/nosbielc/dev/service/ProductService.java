package com.nosbielc.dev.service;

import com.nosbielc.dev.entity.Product;
import com.nosbielc.dev.repository.ProductReadRepository;
import com.nosbielc.dev.repository.ProductWriteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductWriteRepository productWriteRepository;
    private final ProductReadRepository productReadRepository;

    public ProductService(ProductWriteRepository productWriteRepository, ProductReadRepository productReadRepository) {
        this.productWriteRepository = productWriteRepository;
        this.productReadRepository = productReadRepository;
    }

    // Operações de ESCRITA
    @Transactional("masterTransactionManager")
    public Product createProduct(String name, String description, BigDecimal price, Integer stockQuantity) {
        logger.info("Creating product: {} (WRITE operation -> Master DB)", name);

        Product product = new Product(name, description, price, stockQuantity);
        return productWriteRepository.save(product);
    }

    @Transactional("masterTransactionManager")
    public Product updateStock(Long productId, Integer newStock) {
        logger.info("Updating stock for product id: {} (WRITE operation -> Master DB)", productId);

        Optional<Product> existingProduct = productWriteRepository.findById(productId);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setStockQuantity(newStock);
            return productWriteRepository.save(product);
        }
        throw new RuntimeException("Product not found with id: " + productId);
    }

    // Operações de LEITURA
    @Transactional(value = "slaveTransactionManager", readOnly = true)
    public List<Product> getAllProducts() {
        logger.info("Fetching all products (READ operation -> Slave DB)");
        return productReadRepository.findAll();
    }

    @Transactional(value = "slaveTransactionManager", readOnly = true)
    public Optional<Product> getProductById(Long id) {
        logger.info("Fetching product by id: {} (READ operation -> Slave DB)", id);
        return productReadRepository.findById(id);
    }

    @Transactional(value = "slaveTransactionManager", readOnly = true)
    public List<Product> searchProductsByName(String name) {
        logger.info("Searching products by name: {} (READ operation -> Slave DB)", name);
        return productReadRepository.findByNameContaining(name);
    }

    @Transactional(value = "slaveTransactionManager", readOnly = true)
    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        logger.info("Fetching products in price range: {} - {} (READ operation -> Slave DB)", minPrice, maxPrice);
        return productReadRepository.findByPriceBetween(minPrice, maxPrice);
    }
}
