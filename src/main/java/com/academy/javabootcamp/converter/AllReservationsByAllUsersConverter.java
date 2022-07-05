package com.academy.javabootcamp.converter;


import com.academy.javabootcamp.dto.AllUsersReservationsResponse;
import com.academy.javabootcamp.model.Reservation;
import org.springframework.stereotype.Component;

@Component
public class AllReservationsByAllUsersConverter {

    public AllUsersReservationsResponse toAllUserReservationResponse(Reservation reservation) {

        return AllUsersReservationsResponse.builder()
                .id(reservation.getId())
                .room(reservation.getRoom().getTitle())
                .user(reservation.getUser().fullName())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .days(reservation.getDays())
                .adults(reservation.getAdults())
                .kids(reservation.getKids())
                .typeBed(reservation.getTypeBed())
                .typeView(reservation.getTypeView())
                .price(reservation.getPrice())
                .date(reservation.getCreated().toString())
                .status(reservation.getStatus())
                .build();
    }
}
