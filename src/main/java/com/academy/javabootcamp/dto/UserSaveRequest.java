package com.academy.javabootcamp.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserSaveRequest {

    @Size(min = 2, max = 255, message = "Email should be between 2 and 255 characters")
    @Email(message = "The email address is invalid.", flags = { Pattern.Flag.CASE_INSENSITIVE })
    @NotEmpty
    private String email;

    @Size(min = 6, message = "The minimum password length must be 6 characters")
    @NotEmpty
    private String password;

    @Size(min = 2, max = 255, message = "Name should be between 2 and 255 characters")
    @NotEmpty
    private String name;

    @Size(min = 2, max = 255, message = "Surname should be between 2 and 255 characters")
    @NotEmpty
    private String surname;

    @Size(min = 1, max = 15, message = "Phone should be between 1 and 15 characters")
    @NotEmpty
    private String phone;

    @NotEmpty
    private Set<String> roles;
}
