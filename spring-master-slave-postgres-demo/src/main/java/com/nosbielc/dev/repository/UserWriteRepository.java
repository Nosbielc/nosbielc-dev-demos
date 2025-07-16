package com.nosbielc.dev.repository;

import com.nosbielc.dev.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository para operações de escrita (Master)
@Repository
public interface UserWriteRepository extends JpaRepository<User, Long> {
    // Operações de escrita vão automaticamente para o master
}
