package com.academy.javabootcamp.converter;

import com.academy.javabootcamp.calculator.RoomReservationCalculator;
import com.academy.javabootcamp.dto.ReservationUpdateRequest;
import com.academy.javabootcamp.dto.ReservationUpdateResponse;
import com.academy.javabootcamp.model.Reservation;
import com.academy.javabootcamp.model.User;
import com.academy.javabootcamp.repository.RoomRepository;
import com.academy.javabootcamp.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class ReservationUpdateConverter {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final RoomReservationCalculator roomReservationCalculator;

    public ReservationUpdateConverter(UserRepository userRepository, RoomRepository roomRepository, RoomReservationCalculator roomReservationCalculator) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.roomReservationCalculator = roomReservationCalculator;
    }

    public Reservation toReservation(ReservationUpdateRequest reservationUpdateRequest) {
        User foundUser = userRepository.getById(reservationUpdateRequest.getUser());
        // Room foundRoom = roomRepository.
        return Reservation.builder()
                .user(foundUser)
                .startDate(reservationUpdateRequest.getStartDate())
                .endDate(reservationUpdateRequest.getEndDate())
                .adults(reservationUpdateRequest.getAdults())
                .kids(reservationUpdateRequest.getKids())
                .typeBed(reservationUpdateRequest.getTypeBed())
                .typeView(reservationUpdateRequest.getTypeView())
                .status(reservationUpdateRequest.getStatus())
                .build();
    }

    public ReservationUpdateResponse toReservationResponse(Reservation reservation) {

        return ReservationUpdateResponse.builder()
                .id(reservation.getId())
                .room(reservation.getRoom().getTitle())
                .created(reservation.getCreated())
                .days(roomReservationCalculator.calculateDaysReservation(reservation))
                .price(roomReservationCalculator.calculatePriceReservation(reservation, reservation.getRoom()))
                .adults(reservation.getAdults())
                .kids(reservation.getKids())
                .userId(reservation.getUser().getId())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .status(reservation.getStatus())
                .typeView(reservation.getTypeView())
                .typeBed(reservation.getTypeBed())
                .user(reservation.getUser().getName())
                .build();
    }
}
