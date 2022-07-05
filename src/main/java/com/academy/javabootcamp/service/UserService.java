package com.academy.javabootcamp.service;

import com.academy.javabootcamp.model.User;

import java.util.Set;

public interface UserService {

    Set<User> findAll();

    User findById(Long id);

    User findByEmail(String email);

    User save(User user);

    User update(User user, Long id);

    void delete(Long id);
}
