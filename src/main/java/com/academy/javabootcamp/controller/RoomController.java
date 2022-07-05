package com.academy.javabootcamp.controller;

import com.academy.javabootcamp.converter.*;
import com.academy.javabootcamp.dto.*;
import com.academy.javabootcamp.filters.FreeRoomCriteria;
import com.academy.javabootcamp.filters.FreeRoomFilter;
import com.academy.javabootcamp.model.Reservation;
import com.academy.javabootcamp.model.Room;
import com.academy.javabootcamp.model.User;
import com.academy.javabootcamp.service.ReservationService;
import com.academy.javabootcamp.service.RoomService;
import com.academy.javabootcamp.service.UserService;
import com.academy.javabootcamp.validator.SaveReservationValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/rooms")
public class RoomController {

    private final RoomSaveConverter roomSaveConverter;
    private final RoomService roomService;
    private final RoomDetailsConverter roomDetailsConverter;
    private final RoomUpdateConverter roomUpdateConverter;
    private final ReservationService reservationService;
    private final ReservationSaveConverter reservationSaveConverter;
    private final UserService userService;
    private final RoomReservationBuilder roomReservationBuilder;
    private final SaveReservationValidator saveReservationValidator;
    private final ReservationByRoomConverter reservationByRoomConverter;
    private final ReservationDetailsConverter reservationDetailsConverter;
    private final ReservationUpdateConverter reservationUpdateConverter;
    private final FreeRoomFilter freeRoomFilter;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomSaveResponse> saveRoom(@RequestBody @Valid RoomSaveRequest roomSaveRequest) {
        Room room = roomSaveConverter.toRoom(roomSaveRequest);
        Room savedRoom = roomService.save(room);

        return ResponseEntity.status(HttpStatus.CREATED).body(roomSaveConverter.toRoomResponse(savedRoom));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RoomDetailsDto> findById(@PathVariable Long id) {
        Room foundRoom = roomService.findById(id);
        RoomDetailsDto roomDetailsDto = roomDetailsConverter.toRoomDetailsDto(foundRoom);
        return ResponseEntity.ok(roomDetailsDto);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomUpdateResponse> updateRoom(@RequestBody @Valid RoomUpdateRequest roomUpdateRequest,
                                                         @PathVariable Long id) {
        Room room = roomUpdateConverter.toRoom(roomUpdateRequest);
        Room updateRoom = roomService.update(room, id);
        return ResponseEntity.ok(roomUpdateConverter.toRoomResponse(updateRoom));
    }

    @PostMapping(value = "/{id}/reservations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservationSaveResponse> saveReservation(@RequestBody @Valid ReservationSaveRequest reservationSaveRequest,
                                                                   @PathVariable Long id) {

        saveReservationValidator.validateSaveData(reservationSaveRequest, id);
        Reservation newReservation = roomReservationBuilder.buildReservation(reservationSaveRequest, id);
        Reservation saveReservation = reservationService.save(newReservation);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationSaveConverter.toReservationResponse(saveReservation));
    }

    @PostMapping(value = "/{id}/summarize")
    public ResponseEntity<ReservationSaveResponse> summarizeReservation(@RequestBody @Valid ReservationSaveRequest reservationSaveRequest,
                                                                        @PathVariable Long id) {

        saveReservationValidator.validateSaveData(reservationSaveRequest, id);
        Reservation newReservation = roomReservationBuilder.buildSummarize(reservationSaveRequest, id);
        return ResponseEntity.ok(reservationSaveConverter.toReservationResponse(newReservation));
    }

    @PutMapping(value = "/{id}/reservations/{rid}")
    public ResponseEntity<ReservationUpdateResponse> updateReservation

            (@RequestBody @Valid ReservationUpdateRequest reservationUpdateRequest, @PathVariable Long id,
             @PathVariable Long rid) {



        saveReservationValidator.validateUpdateData(id, rid);
        Reservation newReservation = roomReservationBuilder.buildUpdate(reservationUpdateRequest, id);


        Reservation updateReservation = reservationService.update(newReservation, rid);
        return ResponseEntity.ok(reservationUpdateConverter.toReservationResponse(updateReservation));


    }

    @GetMapping(value = "/{id}/reservations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservationResponse>> findAllReservation(@PathVariable Long id) {

        Room room = roomService.findById(id);
        List<Reservation> roomReservations = room.getReservations();
        List<ReservationResponse> reservationsResponse = roomReservations
                .stream()
                .map(reservationByRoomConverter::toReservationResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservationsResponse);
    }

    @GetMapping(value = "/{id}/reservations/{rid}")
    public ResponseEntity<ReservationResponse> findById(@PathVariable Long id, @PathVariable Long rid) {
        Room foundRoom = roomService.findById(id);
        Reservation foundReservation = reservationService.findById(rid);
        User foundUser = userService.findById(foundReservation.getUser().getId());
        ReservationResponse reservationResponse = reservationDetailsConverter.toReservationResponse(foundReservation,
                foundRoom, foundUser);

        return ResponseEntity.ok(reservationResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<RoomDetailsDto>> findAll(FreeRoomCriteria freeRoomCriteria) {
        return freeRoomFilter.getListResponseEntity(freeRoomCriteria.getStartDate(),
                freeRoomCriteria.getEndDate(),
                freeRoomCriteria.getAdults(),
                freeRoomCriteria.getKids());
    }

    @DeleteMapping(value = "/{id}/reservations/{rid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Long id, @PathVariable Long rid) {
        Reservation reservation = reservationService.findReservationByRoomIdAndId(id, rid);
        reservationService.deleteById(reservation.getId());

        return ResponseEntity.noContent().build();
    }

}
