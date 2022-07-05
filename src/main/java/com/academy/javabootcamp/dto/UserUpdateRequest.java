package com.academy.javabootcamp.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Valid
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserUpdateRequest {

    private Long id;

    @NotEmpty
    @Size(min = 2, max = 255, message = "Size should be between 2 and 255")
    private String name;

    @NotEmpty
    @Size(min = 2, max = 255, message = "Size should be between 2 and 255")
    private String surname;

    @NotEmpty
    @Size(min = 6, max = 255, message = "Size should be between 2 and 255")
    private String email;

    @NotEmpty
    @Size(min = 1, max = 15, message = "Size should be between 1 and 15")
    private String phone;

    @NotEmpty
    @Size(min = 6, message = "Password size should be min 6 symbols")
    private String password;

    private Set<String> roles;

    private Date created;
}
