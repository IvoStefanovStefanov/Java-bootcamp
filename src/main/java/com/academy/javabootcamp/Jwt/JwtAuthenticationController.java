package com.academy.javabootcamp.Jwt;

import com.academy.javabootcamp.dto.UserDto;
import com.academy.javabootcamp.exception.ErrorModelAuthentication;
import com.academy.javabootcamp.exception.UserNotFoundException;
import com.academy.javabootcamp.model.Role;
import com.academy.javabootcamp.model.User;
import com.academy.javabootcamp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class JwtAuthenticationController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JWTUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    private ErrorModelAuthentication bedRequest;

    @RequestMapping(value = "/users/token", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {


        try {
            authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        } catch (InternalAuthenticationServiceException e) {
            throw new UserNotFoundException("Incorrect email", "email");
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);

        final String userEmail = userDetails.getUsername();

        final User findUser = userService.findByEmail(userEmail);

        return ResponseEntity.ok(new JwtResponse(token, UserDto.builder()
                .id(findUser.getId())
                .name(findUser.getName())
                .surname(findUser.getSurname())
                .email(findUser.getEmail())
                .phone(findUser.getPhone())
                .roles(findUser.getRoles()
                        .stream()
                        .map(Role::getResponseName)
                        .collect(Collectors.toSet()))
                .created(findUser.getCreated())
                .build()));
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new UserNotFoundException("Incorrect password ", "password");
        }
    }
}
