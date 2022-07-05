package com.academy.javabootcamp.converter;

import com.academy.javabootcamp.dto.UserDto;
import com.academy.javabootcamp.model.Role;
import com.academy.javabootcamp.model.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserConverter {

    public UserDto toUserDto(User user) {

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .roles(user.getRoles()
                        .stream()
                        .map(Role::getResponseName)
                        .collect(Collectors.toSet()))
                .created(user.getCreated())
                .build();
    }

    public User toUser(UserDto userDto) {

        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .roles(userDto.getRoles()
                        .stream()
                        .map((roleName) ->
                                Role.builder().responseName(roleName).build())
                        .collect(Collectors.toSet()))
                .created(userDto.getCreated())
                .build();
    }
}
