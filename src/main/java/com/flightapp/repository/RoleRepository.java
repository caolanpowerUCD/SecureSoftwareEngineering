package com.flightapp.repository;

import com.flightapp.model.Role;
import com.flightapp.model.ExecutiveUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}