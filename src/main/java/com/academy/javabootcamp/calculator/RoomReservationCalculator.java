package com.academy.javabootcamp.calculator;


import com.academy.javabootcamp.model.Reservation;
import com.academy.javabootcamp.model.Room;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class RoomReservationCalculator {

    public Long calculateDaysReservation(Reservation reservation) {

        LocalDate startReservationDate = LocalDate.parse(reservation.getStartDate());
        LocalDate endReservationDate = LocalDate.parse(reservation.getEndDate());

        Long period = ChronoUnit.DAYS.between(startReservationDate, endReservationDate);

        return period;
    }

    public Double calculatePriceReservation(Reservation reservation, Room room) {
        Double reservationPrice = calculateDaysReservation(reservation) * room.getPrice();
        return reservationPrice;
    }
}
