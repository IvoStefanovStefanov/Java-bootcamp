package com.academy.javabootcamp.converter.userConverter;

import com.academy.javabootcamp.converter.UserSave;
import com.academy.javabootcamp.dto.UserSaveRequest;
import com.academy.javabootcamp.dto.UserSaveResponse;
import com.academy.javabootcamp.model.Role;
import com.academy.javabootcamp.model.User;
import com.academy.javabootcamp.repository.RoleRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class UserSaveConverterTests {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void assertThatUserSaveConverterIsConvertingToUserCorrectly() {
        UserSave userSave = new UserSave(roleRepository, passwordEncoder);
        Set<String> userRoles = new HashSet<>();

        UserSaveRequest userSaveRequest = UserSaveRequest.builder()
                .email("Georgi_Georgiev@abv.bg")
                .password(passwordEncoder.encode("11111111"))
                .name("Georgi")
                .surname("Georgiev")
                .phone("0875646353")
                .roles(userRoles)
                .build();

        User user = userSave.toUser(userSaveRequest);

        Assertions.assertEquals(user.getEmail(), userSaveRequest.getEmail());
        Assertions.assertEquals(user.getPassword(), userSaveRequest.getPassword());
        Assertions.assertEquals(user.getName(), userSaveRequest.getName());
        Assertions.assertEquals(user.getSurname(), userSaveRequest.getSurname());
        Assertions.assertEquals(user.getPhone(), userSaveRequest.getPhone());
        Assertions.assertNotNull(user.getRoles());
    }

    @Test
    public void assertThatUserSaveConverterIsConvertingToResponseCorrectly() {
        UserSave userSave = new UserSave(roleRepository, passwordEncoder);
        Set<Role> userRoles = new HashSet<>();
        User user = User.builder()
                .email("Georgi_Georgiev@abv.bg")
                .name("Georgi")
                .surname("Georgiev")
                .phone("0875646353")
                .roles(userRoles)
                .build();

        UserSaveResponse userSaveResponse = userSave.toUserResponse(user);

        Assertions.assertEquals(user.getEmail(), userSaveResponse.getEmail());
        Assertions.assertEquals(user.getName(), userSaveResponse.getName());
        Assertions.assertEquals(user.getSurname(), userSaveResponse.getSurname());
        Assertions.assertEquals(user.getPhone(), userSaveResponse.getPhone());
        Assertions.assertNotNull(userSaveResponse.getRoles());
    }
}
