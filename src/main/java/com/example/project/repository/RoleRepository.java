package com.example.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.model.AppRole;
import com.example.project.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

    Optional<Role> findByRoleName(AppRole roleUser);

}
