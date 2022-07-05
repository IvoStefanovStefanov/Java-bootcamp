package com.academy.javabootcamp.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import java.io.IOException;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BadRequestResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, Set<String>> fieldErrors = new HashMap<>();
        List<FieldError> fieldErrorList = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrorList) {
            if (fieldErrors.containsKey(fieldError.getField())) {
                fieldErrors.get(fieldError.getField()).add(fieldError.getDefaultMessage());
            } else {
                Set<String> errors = new HashSet<>();
                errors.add(fieldError.getDefaultMessage());
                fieldErrors.put(fieldError.getField(), errors);
            }
        }

        BadRequestResponse badRequestResponse = BadRequestResponse.builder()
                .message("Invalid data supplied")
                .errors(fieldErrors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequestResponse);
    }

    @ExceptionHandler({ResourceNotFound.class})
    public ResponseEntity<ErrorMessage> handleResourceNotFound(ResourceNotFound e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<ErrorModelAuthentication> handleDuplicateRecord(UserNotFoundException ex) {

        Map<String, Set<String>> errors = new HashMap<>();
        Set<String> messages = new HashSet<>();
        messages.add(ex.getMessage());
        errors.put(ex.getField(), messages);

        ErrorModelAuthentication error = ErrorModelAuthentication.builder()
                .message("LOGIN FAILED ")
                .errors(errors)
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    private ResponseEntity<ErrorModelForbidden> handleException(AccessDeniedException ex) {

        Set<String> messages = new HashSet<>();
        messages.add(ex.getMessage());


        ErrorModelForbidden error = ErrorModelForbidden.builder()
                .message("Forbidden access")
                .build();

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ReservationDataNotAppropriateException.class})
    public ResponseEntity<ErrorMessage> handleReservationBadData(ReservationDataNotAppropriateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler({DuplicateRecordException.class})
    public ResponseEntity<ErrorMessage> handleUserEmailDuplicated(DuplicateRecordException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler({InvalidFormatException.class, MismatchedInputException.class})
    public void handlerIllegalArgumentException(JsonProcessingException exception,
                                                ServletWebRequest webRequest) throws IOException {
        if(exception instanceof InvalidFormatException) {
            if (webRequest.getResponse() != null) {
                webRequest.getResponse().sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
            }
        } else if (exception instanceof MismatchedInputException) {
            if (webRequest.getResponse() != null) {
                webRequest.getResponse().sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
            }
        }
    }
}