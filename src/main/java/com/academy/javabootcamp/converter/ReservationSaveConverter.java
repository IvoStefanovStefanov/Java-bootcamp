package com.academy.javabootcamp.converter;


import com.academy.javabootcamp.calculator.RoomReservationCalculator;
import com.academy.javabootcamp.dto.ReservationSaveRequest;
import com.academy.javabootcamp.dto.ReservationSaveResponse;
import com.academy.javabootcamp.model.Reservation;
import com.academy.javabootcamp.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
@AllArgsConstructor
public class ReservationSaveConverter {

    private final RoomReservationCalculator roomReservationCalculator;

    public Reservation toReservation(ReservationSaveRequest reservationSaveRequest) {

        return Reservation.builder()
                .user(User.builder().id(reservationSaveRequest.getUser()).build())
                .startDate(reservationSaveRequest.getStartDate())
                .endDate(reservationSaveRequest.getEndDate())
                .adults(reservationSaveRequest.getAdults())
                .kids(reservationSaveRequest.getKids())
                .typeBed(reservationSaveRequest.getTypeBed())
                .typeView(reservationSaveRequest.getTypeView())
                .created(Date.from(Instant.now()))
                .build();
    }

    public ReservationSaveResponse toReservationResponse(Reservation reservation) {

        RoomReservationCalculator reservationCalculator = new RoomReservationCalculator();

        ReservationSaveResponse reservationSaveResponse = ReservationSaveResponse.builder()
                .id(reservation.getId())
                .room(reservation.getRoom().getTitle())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .days(reservationCalculator.calculateDaysReservation(reservation))
                .price(reservation.getPrice())
                .adults(reservation.getAdults())
                .kids(reservation.getKids())
                .build();

        return reservationSaveResponse;
    }
}
