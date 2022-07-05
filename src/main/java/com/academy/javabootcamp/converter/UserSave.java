package com.academy.javabootcamp.converter;

import com.academy.javabootcamp.dto.UserSaveRequest;
import com.academy.javabootcamp.dto.UserSaveResponse;
import com.academy.javabootcamp.model.Role;
import com.academy.javabootcamp.model.User;
import com.academy.javabootcamp.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserSave {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSave(RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User toUser(UserSaveRequest userSaveRequest) {
        User user = User.builder()
                .email(userSaveRequest.getEmail())
                .password(passwordEncoder.encode(userSaveRequest.getPassword()))
                .name(userSaveRequest.getName())
                .surname(userSaveRequest.getSurname())
                .phone(userSaveRequest.getPhone())
                .created(new Date())
                .build();
        Set<Role> roleSet = new HashSet<>();
        for (String roleStr : userSaveRequest.getRoles()) {
            Role role = roleRepository.findByResponseName(roleStr);
            if (role != null) {
                roleSet.add(role);
            }
        }
        user.setRoles(roleSet);

        return user;
    }

    public UserSaveResponse toUserResponse(User user) {

        return UserSaveResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .phone(user.getPhone())
                .roles(user.getRoles().stream().map(Role::getResponseName).collect(Collectors.toSet()))
                .created(user.getCreated())
                .build();
    }
}
