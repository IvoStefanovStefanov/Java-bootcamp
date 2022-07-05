package com.academy.javabootcamp.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class ErrorModelForbidden {
    private String message;

    public ErrorModelForbidden(String message) {
        this.message = message;
    }
}