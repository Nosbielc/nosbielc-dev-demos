package com.nosbielc.dev.repository;

import com.nosbielc.dev.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository para Product com leitura/escrita
@Repository
public interface ProductWriteRepository extends JpaRepository<Product, Long> {
}
