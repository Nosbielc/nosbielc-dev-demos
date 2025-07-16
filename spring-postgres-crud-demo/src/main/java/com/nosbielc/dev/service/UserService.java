package com.nosbielc.dev.service;

import com.nosbielc.dev.dto.UserCreateDTO;
import com.nosbielc.dev.dto.UserUpdateDTO;
import com.nosbielc.dev.entity.User;
import com.nosbielc.dev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public User createUser(UserCreateDTO userCreateDTO) {
        log.info("Creating user with email: {}", userCreateDTO.getEmail());
        
        if (userRepository.existsByEmail(userCreateDTO.getEmail())) {
            throw new IllegalArgumentException("Email já está em uso: " + userCreateDTO.getEmail());
        }

        User user = new User();
        user.setName(userCreateDTO.getName());
        user.setEmail(userCreateDTO.getEmail());
        user.setPhone(userCreateDTO.getPhone());
        user.setActive(true);

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        log.info("Finding user by ID: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com ID: " + id));
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        log.info("Finding user by email: {}", email);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com email: " + email));
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        log.info("Finding all users");
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        log.info("Finding all users with pagination");
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<User> findActiveUsers() {
        log.info("Finding active users");
        return userRepository.findByActiveTrue();
    }

    @Transactional(readOnly = true)
    public List<User> findByNameContaining(String name) {
        log.info("Finding users by name containing: {}", name);
        return userRepository.findByNameContainingIgnoreCase(name);
    }

    public User updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        log.info("Updating user with ID: {}", id);
        
        User user = findById(id);

        if (userUpdateDTO.getName() != null) {
            user.setName(userUpdateDTO.getName());
        }
        if (userUpdateDTO.getEmail() != null && !userUpdateDTO.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(userUpdateDTO.getEmail())) {
                throw new IllegalArgumentException("Email já está em uso: " + userUpdateDTO.getEmail());
            }
            user.setEmail(userUpdateDTO.getEmail());
        }
        if (userUpdateDTO.getPhone() != null) {
            user.setPhone(userUpdateDTO.getPhone());
        }
        if (userUpdateDTO.getActive() != null) {
            user.setActive(userUpdateDTO.getActive());
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id);
        User user = findById(id);
        userRepository.delete(user);
    }

    public void deactivateUser(Long id) {
        log.info("Deactivating user with ID: {}", id);
        User user = findById(id);
        user.setActive(false);
        userRepository.save(user);
    }
}