package com.academy.javabootcamp.dto;

import lombok.*;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserSaveResponse {
    private Long id;
    private String email;
    private String name;
    private String surname;
    private String phone;
    private Set<String> roles;
    private Date created;
}
