package com.academy.javabootcamp.converter;

import com.academy.javabootcamp.dto.ReservationResponse;
import com.academy.javabootcamp.model.Reservation;
import com.academy.javabootcamp.model.Room;
import com.academy.javabootcamp.model.User;
import org.springframework.stereotype.Component;

@Component
public class ReservationDetailsConverter {

    public ReservationResponse toReservationResponse(Reservation reservation, Room room, User user) {


        return ReservationResponse.builder()
                .id(reservation.getId())
                .room(reservation.getRoom().getTitle())
                .userId(reservation.getUser().getId())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .user(user.getName() + " " + user.getSurname())
                .days(reservation.getDays())
                .adults(reservation.getAdults())
                .kids(reservation.getKids())
                .typeBed(reservation.getTypeBed())
                .typeView(reservation.getTypeView())
                .price(reservation.getPrice())
                .status(reservation.getStatus())
                .created(reservation.getCreated().toString())
                .build();
    }
}
