package com.academy.javabootcamp.service;

import com.academy.javabootcamp.model.Role;

public interface RoleService {

    Role findByResponseName(String name);
}
