package com.academy.javabootcamp.dto;

import lombok.*;

import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BadRequestResponse {
    private String message;
    private Map<String, Set<String>> errors;
}
