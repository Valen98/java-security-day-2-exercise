package com.booleanuk.api.repository;

import com.booleanuk.api.model.ERole;
import com.booleanuk.api.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
