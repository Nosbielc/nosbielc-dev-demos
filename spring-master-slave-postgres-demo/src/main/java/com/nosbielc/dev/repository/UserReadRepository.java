package com.nosbielc.dev.repository;

import com.nosbielc.dev.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Repository customizado para operações de leitura (Slave)
@Repository
public class UserReadRepository {

    @PersistenceContext(unitName = "slaveEntityManagerFactory")
    private EntityManager entityManager;

    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }

    public Optional<User> findById(Long id) {
        User user = entityManager.find(User.class, id);
        return Optional.ofNullable(user);
    }

    public Optional<User> findByEmail(String email) {
        List<User> users = entityManager.createQuery(
                        "SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();

        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    public List<User> findByUsernameLike(String username) {
        return entityManager.createQuery(
                        "SELECT u FROM User u WHERE u.username LIKE :username", User.class)
                .setParameter("username", "%" + username + "%")
                .getResultList();
    }

    public long count() {
        return entityManager.createQuery("SELECT COUNT(u) FROM User u", Long.class)
                .getSingleResult();
    }
}
