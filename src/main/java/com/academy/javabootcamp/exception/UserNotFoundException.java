package com.academy.javabootcamp.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    private String field;

    public UserNotFoundException(String message, String field) {
        super(message);
        this.field = field;
    }
}
