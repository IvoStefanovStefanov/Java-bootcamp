package com.academy.javabootcamp.validator;


import com.academy.javabootcamp.dto.ReservationSaveRequest;
import com.academy.javabootcamp.exception.ReservationDataNotAppropriateException;
import com.academy.javabootcamp.exception.ResourceNotFound;
import com.academy.javabootcamp.model.Reservation;
import com.academy.javabootcamp.model.Room;
import com.academy.javabootcamp.service.ReservationService;
import com.academy.javabootcamp.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class SaveReservationValidator {

    private final RoomService roomService;
    private final ReservationService reservationService;

    public void validateSaveData(ReservationSaveRequest reservationSaveRequest, Long id) {
        Room foundRoom = roomService.findById(id);

        LocalDate startDate = LocalDate.parse(reservationSaveRequest.getStartDate());
        LocalDate endDate = LocalDate.parse(reservationSaveRequest.getEndDate());
        Long numberDays = ChronoUnit.DAYS.between(startDate, endDate);

        Integer count = foundRoom.getPeople();
        Integer numberGuests = reservationSaveRequest.getAdults() + reservationSaveRequest.getKids();

        validateNumberOfGuests(count, numberGuests);
        validateTimePeriodIsOverlapOtherReservations(startDate, endDate, numberDays, foundRoom);

    }

    public void validateNumberOfGuests(int count, int numberGuests) {
        if (count < numberGuests) {
            throw new ReservationDataNotAppropriateException("This room is not big enough for this number of guests");
        }
    }

    public void validateTimePeriodIsOverlapOtherReservations(LocalDate startDate, LocalDate endDate, Long numberDays,
                                                             Room foundRoom) {
        List<Reservation> reservations = new ArrayList<>();

        for (Reservation existingReservation : foundRoom.getReservations()) {
            LocalDate existStartDate = LocalDate.parse(existingReservation.getStartDate());
            LocalDate existEndDate = LocalDate.parse(existingReservation.getEndDate());
            if (((startDate.isAfter(existStartDate) || startDate.isEqual(existStartDate))
                    && (startDate.isBefore(existEndDate)))
                    || ((endDate.isBefore(existEndDate) || endDate.isEqual(existEndDate)) &&
                    endDate.isAfter(existStartDate))) {
                reservations.add(existingReservation);
                if (foundRoom.getCount() <= reservations.size()) {
                    throw new ReservationDataNotAppropriateException(String.format(
                            "This type of room %s is not available for this period of time", foundRoom.getTitle()));
                }
            } else {
                Long existingReservationDays = ChronoUnit.DAYS.between(existStartDate, existEndDate);
                if (numberDays > existingReservationDays
                        && (startDate.isBefore(existStartDate) && endDate.isAfter(existEndDate))) {
                    reservations.add(existingReservation);
                    if (foundRoom.getCount() <= reservations.size()) {
                        throw new ReservationDataNotAppropriateException(String.format(
                                "This type of room %s is not available for this period of time", foundRoom.getTitle()));
                    }
                }
            }
        }
    }

    public void validateUpdateData(Long id, Long rid) {
        Reservation foundReservation = reservationService.findById(rid);
        Room foundRoom = roomService.findById(id);

        if (foundRoom.equals(foundReservation.getRoom())){
               return;
            }
        throw new ResourceNotFound(String.format("For room with id = %d, there is no reservation with id = %d.", id, rid));
    }
}
