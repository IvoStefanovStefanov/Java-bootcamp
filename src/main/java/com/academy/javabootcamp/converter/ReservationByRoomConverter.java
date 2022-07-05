package com.academy.javabootcamp.converter;


import com.academy.javabootcamp.dto.ReservationResponse;
import com.academy.javabootcamp.model.Reservation;
import org.springframework.stereotype.Component;


@Component
public class ReservationByRoomConverter {


    public ReservationResponse toReservationResponse(Reservation reservation) {

        return ReservationResponse.builder()
                .id(reservation.getId())
                .room(reservation.getRoom().getTitle())
                .adults(reservation.getAdults())
                .created(reservation.getCreated().toString())
                .days(reservation.getDays())
                .kids(reservation.getKids())
                .price(reservation.getPrice())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .user(reservation.getUser().getName())
                .status(reservation.getStatus())
                .typeBed(reservation.getTypeBed())
                .typeView(reservation.getTypeView())
                .userId(reservation.getUser().getId())
                .build();
    }

}
