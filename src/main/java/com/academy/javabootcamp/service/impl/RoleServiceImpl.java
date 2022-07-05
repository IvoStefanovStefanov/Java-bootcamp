package com.academy.javabootcamp.service.impl;

import com.academy.javabootcamp.model.Role;
import com.academy.javabootcamp.repository.RoleRepository;
import com.academy.javabootcamp.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByResponseName(String name) {
        return roleRepository.findByResponseName(name);
    }
}
