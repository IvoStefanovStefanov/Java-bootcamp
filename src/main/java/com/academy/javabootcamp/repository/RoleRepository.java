package com.academy.javabootcamp.repository;

import com.academy.javabootcamp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByResponseName(String name);

}
