package com.academy.javabootcamp.converter.userConverter;

import com.academy.javabootcamp.converter.UserConverter;
import com.academy.javabootcamp.dto.UserDto;
import com.academy.javabootcamp.model.Role;
import com.academy.javabootcamp.model.User;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class UserConverterTest {

    @Test
    public void assertThatUserConverterIsConvertingToDtoCorrectly() {
        UserConverter userConverter = new UserConverter();
        Set<Role> userRoles = new HashSet<>();

        User user = User.builder()
                .email("Georgi_Georgiev@abv.bg")
                .name("Georgi")
                .surname("Georgiev")
                .phone("0875646353")
                .roles(userRoles)
                .build();

        UserDto userDto = userConverter.toUserDto(user);

        Assertions.assertEquals(user.getEmail(), userDto.getEmail());
        Assertions.assertEquals(user.getName(), userDto.getName());
        Assertions.assertEquals(user.getSurname(), userDto.getSurname());
        Assertions.assertEquals(user.getPhone(), userDto.getPhone());
        Assertions.assertNotNull(user.getRoles());
    }

    @Test
    public void assertThatUserConverterIsConvertingToUserCorrectly() {
        UserConverter userConverter = new UserConverter();
        Set<String> userRoles = new HashSet<>();

        UserDto userDto = UserDto.builder()
                .email("Georgi_Georgiev@abv.bg")
                .name("Georgi")
                .surname("Georgiev")
                .phone("0875646353")
                .roles(userRoles)
                .build();

        User user = userConverter.toUser(userDto);

        Assertions.assertEquals(userDto.getEmail(), user.getEmail());
        Assertions.assertEquals(userDto.getName(), user.getName());
        Assertions.assertEquals(userDto.getSurname(), user.getSurname());
        Assertions.assertEquals(userDto.getPhone(), user.getPhone());
        Assertions.assertNotNull(userDto.getRoles());
    }
}
