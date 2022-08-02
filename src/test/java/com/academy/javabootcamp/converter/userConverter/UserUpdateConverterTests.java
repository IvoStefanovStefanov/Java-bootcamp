package com.academy.javabootcamp.converter.userConverter;

import com.academy.javabootcamp.converter.UserUpdateConverter;
import com.academy.javabootcamp.dto.UserUpdateRequest;
import com.academy.javabootcamp.dto.UserUpdateResponse;
import com.academy.javabootcamp.model.Role;
import com.academy.javabootcamp.model.User;
import com.academy.javabootcamp.service.RoleService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class UserUpdateConverterTests {

    @Mock
    private RoleService roleService;

    @Test
    public void assertThatUserUpdateConverterIsConvertingToUserCorrectly() {
        UserUpdateConverter userUpdateConverter = new UserUpdateConverter(roleService);
        Set<String> userRoles = new HashSet<>();

        UserUpdateRequest userUpdateRequest = UserUpdateRequest.builder()
                .email("Georgi_Georgiev@abv.bg")
                .password("11111111")
                .name("Georgi")
                .surname("Georgiev")
                .phone("0875646353")
                .roles(userRoles)
                .build();

        User user = userUpdateConverter.toUser(userUpdateRequest);

        Assertions.assertEquals(user.getEmail(), userUpdateRequest.getEmail());
        Assertions.assertEquals(user.getPassword(), userUpdateRequest.getPassword());
        Assertions.assertEquals(user.getName(), userUpdateRequest.getName());
        Assertions.assertEquals(user.getSurname(), userUpdateRequest.getSurname());
        Assertions.assertEquals(user.getPhone(), userUpdateRequest.getPhone());
        Assertions.assertNotNull(user.getRoles());
        Assertions.assertNotNull(user.getCreated());
    }

    @Test
    public void assertThatUserUpdateConverterIsConvertingToUserResponseCorrectly() {
        UserUpdateConverter userUpdateConverter = new UserUpdateConverter(roleService);
        Set<Role> userRoles = new HashSet<>();
        User user = User.builder()
                .email("Georgi_Georgiev@abv.bg")
                .name("Georgi")
                .surname("Georgiev")
                .phone("0875646353")
                .roles(userRoles)
                .build();

        UserUpdateResponse userUpdateResponse = userUpdateConverter.toUserResponse(user);

        Assertions.assertEquals(user.getEmail(), userUpdateResponse.getEmail());
        Assertions.assertEquals(user.getName(), userUpdateResponse.getName());
        Assertions.assertEquals(user.getSurname(), userUpdateResponse.getSurname());
        Assertions.assertEquals(user.getPhone(), userUpdateResponse.getPhone());
        Assertions.assertNotNull(userUpdateResponse.getRoles());
        Assertions.assertNotNull(userUpdateResponse.getCreated());
    }
}
