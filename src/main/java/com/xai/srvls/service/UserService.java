package com.xai.srvls.service;

import com.xai.srvls.model.User;
import com.xai.srvls.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for User-related operations
 */
@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    /**
     * Find a user by ID
     * @param id the user ID
     * @return an Optional containing the user if found
     */
    @Cacheable(value = "users", key = "#id", unless = "#result == null")
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }
    
    /**
     * Find a user by username
     * @param username the username
     * @return an Optional containing the user if found
     */
    @Cacheable(value = "users", key = "#username", unless = "#result == null")
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    /**
     * Find a user by email
     * @param email the email address
     * @return an Optional containing the user if found
     */
    @Cacheable(value = "users", key = "#email", unless = "#result == null")
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    /**
     * Get all users
     * @return a list of all users
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    /**
     * Save a user to the database
     * @param user the user to save
     * @return the saved user
     */
    @Transactional
    @CacheEvict(value = "users", key = "#user.id")
    public User save(User user) {
        return userRepository.save(user);
    }
    
    /**
     * Update a user
     * @param id the user ID
     * @param updatedUser the updated user data
     * @return the updated user
     * @throws UsernameNotFoundException if the user is not found
     */
    @Transactional
    @CacheEvict(value = "users", key = "#id")
    public User update(UUID id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstName(updatedUser.getFirstName());
                    user.setLastName(updatedUser.getLastName());
                    if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    }
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }
    
    /**
     * Delete a user
     * @param id the user ID
     */
    @Transactional
    @CacheEvict(value = "users", key = "#id")
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }
    
    /**
     * Update the last login time for a user
     * @param id the user ID
     */
    @Transactional
    @CacheEvict(value = "users", key = "#id")
    public void updateLastLogin(UUID id) {
        userRepository.findById(id).ifPresent(user -> {
            user.setLastLoginAt(LocalDateTime.now());
            userRepository.save(user);
        });
    }
    
    /**
     * Check if a username already exists
     * @param username the username to check
     * @return true if the username exists
     */
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    /**
     * Check if an email already exists
     * @param email the email to check
     * @return true if the email exists
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
