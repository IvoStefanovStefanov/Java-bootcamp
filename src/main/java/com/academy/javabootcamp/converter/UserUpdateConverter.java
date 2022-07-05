package com.academy.javabootcamp.converter;

import com.academy.javabootcamp.dto.UserUpdateRequest;
import com.academy.javabootcamp.dto.UserUpdateResponse;
import com.academy.javabootcamp.model.Role;
import com.academy.javabootcamp.model.User;
import com.academy.javabootcamp.service.RoleService;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserUpdateConverter {
    private final RoleService roleService;

    public UserUpdateConverter(RoleService roleService) {
        this.roleService = roleService;
    }
    public User toUser(UserUpdateRequest userUpdateRequest) {

        User user = User.builder()
                .id(userUpdateRequest.getId())
                .name(userUpdateRequest.getName())
                .surname(userUpdateRequest.getSurname())
                .email(userUpdateRequest.getEmail())
                .phone(userUpdateRequest.getPhone())
                .password(userUpdateRequest.getPassword())
                .created(Date.from(Instant.now()))
                .build();

        Set<Role> roleSet = new HashSet<>();
        for (String roleStr : userUpdateRequest.getRoles()) {
            Role role = roleService.findByResponseName(roleStr);
            roleSet.add(role);
        }
        user.setRoles(roleSet);
        return user;
    }

    public UserUpdateResponse toUserResponse(User user) {

        return UserUpdateResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .roles(user.getRoles().stream().map(Role::getResponseName).collect(Collectors.toSet()))
                .created(Date.from(Instant.now()))
                .build();
    }
}
