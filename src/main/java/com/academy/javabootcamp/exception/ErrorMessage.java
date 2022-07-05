package com.academy.javabootcamp.exception;

import lombok.*;

@Data
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

    private String message;
}
