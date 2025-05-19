package com.xai.srvls.repository;

import com.xai.srvls.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for User entity
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    
    /**
     * Find a user by username
     * @param username the username
     * @return an Optional containing the user if found
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Find a user by email
     * @param email the email
     * @return an Optional containing the user if found
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Check if a username exists
     * @param username the username
     * @return true if the username exists
     */
    Boolean existsByUsername(String username);
    
    /**
     * Check if an email exists
     * @param email the email
     * @return true if the email exists
     */
    Boolean existsByEmail(String email);
}
