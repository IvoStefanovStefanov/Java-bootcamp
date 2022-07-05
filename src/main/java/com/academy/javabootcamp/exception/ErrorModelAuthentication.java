package com.academy.javabootcamp.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;


@Getter
@Setter
@Builder
public class ErrorModelAuthentication {


    private String message;
    private Map<String, Set<String>> errors;

    public ErrorModelAuthentication(String message, Map<String, Set<String>> errors) {
        this.message = message;
        this.errors = errors;
    }
}
