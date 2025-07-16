package com.nosbielc.dev.repository;

import com.nosbielc.dev.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductReadRepository {

    @PersistenceContext(unitName = "slaveEntityManagerFactory")
    private EntityManager entityManager;

    public List<Product> findAll() {
        return entityManager.createQuery("SELECT p FROM Product p", Product.class)
                .getResultList();
    }

    public Optional<Product> findById(Long id) {
        Product product = entityManager.find(Product.class, id);
        return Optional.ofNullable(product);
    }

    public List<Product> findByNameContaining(String name) {
        return entityManager.createQuery(
                        "SELECT p FROM Product p WHERE p.name LIKE :name", Product.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }

    public List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        return entityManager.createQuery(
                        "SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice", Product.class)
                .setParameter("minPrice", minPrice)
                .setParameter("maxPrice", maxPrice)
                .getResultList();
    }
}
