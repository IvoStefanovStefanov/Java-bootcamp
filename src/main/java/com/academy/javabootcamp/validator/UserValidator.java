package com.academy.javabootcamp.validator;

import com.academy.javabootcamp.exception.DuplicateRecordException;
import com.academy.javabootcamp.exception.ResourceNotFound;
import com.academy.javabootcamp.model.User;
import com.academy.javabootcamp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserValidator {

    private final UserService userService;

    public void validateSaveIfEmailIsDuplicated(String email) {

        try {
            User user = userService.findByEmail(email);
            if (user != null) {
                throw new DuplicateRecordException(String.format("This email: %s already exist", email));
            }
        } catch (ResourceNotFound e) {
        }
    }

    public void validateUpdateIfEmailIsDuplicated(String email, Long id) {

        try {
            User user = userService.findByEmail(email);
            if (!user.getId().equals(id)) {
                throw new DuplicateRecordException(String.format("This email: %s already exist", email));
            }
        } catch (ResourceNotFound e) {
        }
    }
}
