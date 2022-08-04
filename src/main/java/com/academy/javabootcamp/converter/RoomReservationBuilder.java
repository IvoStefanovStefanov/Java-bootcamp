package com.academy.javabootcamp.converter;

import com.academy.javabootcamp.calculator.RoomReservationCalculator;
import com.academy.javabootcamp.dto.ReservationSaveRequest;
import com.academy.javabootcamp.dto.ReservationUpdateRequest;
import com.academy.javabootcamp.model.Reservation;
import com.academy.javabootcamp.model.Room;
import com.academy.javabootcamp.model.User;
import com.academy.javabootcamp.service.RoomService;
import com.academy.javabootcamp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
@AllArgsConstructor
public class RoomReservationBuilder {

    private final RoomService roomService;
    private final UserService userService;
    private final ReservationSaveConverter reservationSaveConverter;
    private final ReservationUpdateConverter reservationUpdateConverter;
    private final RoomReservationCalculator roomReservationCalculator;

    public Reservation buildReservation(ReservationSaveRequest reservationSaveRequest, Long id) {
        Room foundRoom = roomService.findById(id);
        User foundUser = userService.findById(reservationSaveRequest.getUser());
//        RoomReservationCalculator reservationCalculator = new RoomReservationCalculator();

        Reservation reservation = reservationSaveConverter.toReservation(reservationSaveRequest);
        Reservation newReservation = Reservation.builder()
                .id(reservation.getId())
                .room(foundRoom)
                .user(foundUser)
                .adults(reservation.getAdults())
                .kids(reservation.getKids())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .typeBed(reservation.getTypeBed())
                .typeView(reservation.getTypeView())
                .price(roomReservationCalculator.calculatePriceReservation(reservation, foundRoom))
                .created(Date.from(Instant.now()))
                .status("new")
                .days(roomReservationCalculator.calculateDaysReservation(reservation))
                .build();
        return newReservation;
    }

    public Reservation buildSummarize(ReservationSaveRequest reservationSaveRequest, Long id) {
        Room foundRoom = roomService.findById(id);
        User foundUser = userService.findById(reservationSaveRequest.getUser());

        Reservation reservation = reservationSaveConverter.toReservation(reservationSaveRequest);
        Reservation summarize = Reservation.builder()
                .id(foundUser.getId())
                .room(foundRoom)
                .user(foundUser)
                .adults(reservation.getAdults())
                .kids(reservation.getKids())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .typeBed(reservation.getTypeBed())
                .typeView(reservation.getTypeView())
                .price(roomReservationCalculator.calculatePriceReservation(reservation, foundRoom))
                .created(Date.from(Instant.now()))
                .build();
        return summarize;
    }

    public Reservation buildUpdate(ReservationUpdateRequest reservationUpdateRequest, Long id) {
        Room foundRoom = roomService.findById(id);
        User foundUser = userService.findById(reservationUpdateRequest.getUser());

        Reservation reservation = reservationUpdateConverter.toReservation(reservationUpdateRequest);
        Reservation newReservation = Reservation.builder()
                .id(reservation.getId())
                .room(foundRoom)
                .user(foundUser)
                .adults(reservation.getAdults())
                .kids(reservation.getKids())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .typeBed(reservation.getTypeBed())
                .typeView(reservation.getTypeView())
                .price(roomReservationCalculator.calculatePriceReservation(reservation, foundRoom))
                .days(roomReservationCalculator.calculateDaysReservation(reservation))
                .created(Date.from(Instant.now()))
                .status(reservation.getStatus())
                .build();
        return newReservation;
    }
}
