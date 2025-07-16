package com.nosbielc.dev.service;

import com.nosbielc.dev.entity.User;
import com.nosbielc.dev.repository.UserReadRepository;
import com.nosbielc.dev.repository.UserWriteRepository;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserWriteRepository userWriteRepository;
    private final UserReadRepository userReadRepository;

    public UserService(UserWriteRepository userWriteRepository, UserReadRepository userReadRepository) {
        this.userWriteRepository = userWriteRepository;
        this.userReadRepository = userReadRepository;
    }

    // Operações de ESCRITA (vão para o Master)
    @Transactional("masterTransactionManager")
    public User createUser(String username, String email) {
        logger.info("Creating user with username: {} (WRITE operation -> Master DB)", username);

        User user = new User(username, email);
        return userWriteRepository.save(user);
    }

    @Transactional("masterTransactionManager")
    public User updateUser(Long id, String username, String email) {
        logger.info("Updating user with id: {} (WRITE operation -> Master DB)", id);

        Optional<User> existingUser = userWriteRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setUsername(username);
            user.setEmail(email);
            return userWriteRepository.save(user);
        }
        throw new RuntimeException("User not found with id: " + id);
    }

    @Transactional("masterTransactionManager")
    public void deleteUser(Long id) {
        logger.info("Deleting user with id: {} (WRITE operation -> Master DB)", id);
        userWriteRepository.deleteById(id);
    }

    // Operações de LEITURA (vão para o Slave)
    @Transactional(value = "slaveTransactionManager", readOnly = true)
    public List<User> getAllUsers() {
        logger.info("Fetching all users (READ operation -> Slave DB)");
        return userReadRepository.findAll();
    }

    @Transactional(value = "slaveTransactionManager", readOnly = true)
    public Optional<User> getUserById(Long id) {
        logger.info("Fetching user by id: {} (READ operation -> Slave DB)", id);
        return userReadRepository.findById(id);
    }

    @Transactional(value = "slaveTransactionManager", readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        logger.info("Fetching user by email: {} (READ operation -> Slave DB)", email);
        return userReadRepository.findByEmail(email);
    }

    @Transactional(value = "slaveTransactionManager", readOnly = true)
    public List<User> searchUsersByUsername(String username) {
        logger.info("Searching users by username: {} (READ operation -> Slave DB)", username);
        return userReadRepository.findByUsernameLike(username);
    }

    @Transactional(value = "slaveTransactionManager", readOnly = true)
    public long getUserCount() {
        logger.info("Counting users (READ operation -> Slave DB)");
        return userReadRepository.count();
    }
}
