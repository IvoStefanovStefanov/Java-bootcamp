package com.academy.javabootcamp.dto;

import lombok.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Set;

@Valid
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private String phone;
    private Set<String> roles;
    private Date created;
}
