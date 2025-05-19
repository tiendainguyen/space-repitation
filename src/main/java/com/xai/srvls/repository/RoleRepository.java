package com.xai.srvls.repository;

import com.xai.srvls.model.ERole;
import com.xai.srvls.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for Role entity
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    /**
     * Find a role by name
     * @param name the role name
     * @return an Optional containing the role if found
     */
    Optional<Role> findByName(ERole name);
}
