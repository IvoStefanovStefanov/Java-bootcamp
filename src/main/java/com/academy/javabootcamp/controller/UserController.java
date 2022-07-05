package com.academy.javabootcamp.controller;


import com.academy.javabootcamp.converter.*;
import com.academy.javabootcamp.dto.*;
import com.academy.javabootcamp.model.Reservation;
import com.academy.javabootcamp.model.User;
import com.academy.javabootcamp.service.ReservationService;
import com.academy.javabootcamp.service.UserService;
import com.academy.javabootcamp.validator.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/users")
@CrossOrigin
public class UserController {

    private final UserUpdateConverter userUpdateConverter;
    private final UserSave userSave;
    private final UserService userService;
    private final UserConverter userConverter;
    private final UserValidator userValidator;
    private final ReservationService reservationService;
    private final AllReservationsByAllUsersConverter allReservationsByAllUsersConverter;
    private final ReservationDetailsConverter reservationDetailsConverter;

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        User foundUser = userService.findById(id);
        UserDto userDto = userConverter.toUserDto((foundUser));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    public ResponseEntity<UserSaveResponse> saveUser(@RequestBody @Valid UserSaveRequest userSaveRequest) {
        String email = userSaveRequest.getEmail();
        userValidator.validateSaveIfEmailIsDuplicated(email);
        User user = userSave.toUser(userSaveRequest);
        User savedUser = userService.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(userSave.toUserResponse(savedUser));
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserUpdateResponse> updateUser(@RequestBody @Valid UserUpdateRequest
                                                                 userUpdateRequest, @PathVariable Long id) {
        String email = userUpdateRequest.getEmail();
        userValidator.validateUpdateIfEmailIsDuplicated(email, id);
        User user = userUpdateConverter.toUser(userUpdateRequest);
        User updateUser = userService.update(user, id);
        return ResponseEntity.ok(userUpdateConverter.toUserResponse(updateUser));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> findAll() {
        SortedSet<User> findUsers = new TreeSet<>(Comparator.comparing(User::getId));
        findUsers.addAll(userService.findAll());
        return ResponseEntity.ok(findUsers
                .stream()
                .map(userConverter::toUserDto)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/reservations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AllUsersReservationsResponse>> findAllReservations() {
        List<Reservation> userReservations = reservationService.findAll();
        List<AllUsersReservationsResponse> reservationResponses = userReservations
                .stream()
                .map(allReservationsByAllUsersConverter::toAllUserReservationResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservationResponses);
    }

    @GetMapping(value = "/{uid}/reservations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AllUsersReservationsResponse>> findAllReservation(@PathVariable Long uid) {

        User user = userService.findById(uid);
        List<Reservation> userReservation = user.getReservations();
        List<AllUsersReservationsResponse> reservationResponses = userReservation
                .stream()
                .map(allReservationsByAllUsersConverter::toAllUserReservationResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservationResponses);
    }

    @GetMapping("/{uid}/reservations/{rid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservationResponse> listAllUserReservation(@PathVariable Long uid, @PathVariable Long rid) {
        Reservation reservation = reservationService.findByUserIdAndId(uid, rid);
        ReservationResponse reservationResponse = reservationDetailsConverter.toReservationResponse(reservation, reservation.getRoom(), reservation.getUser());
        return ResponseEntity.ok(reservationResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


