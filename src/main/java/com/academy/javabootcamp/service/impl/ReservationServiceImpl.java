package com.academy.javabootcamp.service.impl;

import com.academy.javabootcamp.exception.ResourceNotFound;
import com.academy.javabootcamp.model.Reservation;
import com.academy.javabootcamp.repository.ReservationRepository;
import com.academy.javabootcamp.service.ReservationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound(String.format("Reservation with id %d does not exists", id)));
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation update(Reservation reservation, Long id) {
        Reservation foundReservation = this.findById(id);
        Reservation updateReservation = Reservation.builder()
                .id(foundReservation.getId())
                .price(reservation.getPrice())
                .typeView(reservation.getTypeView())
                .typeBed(reservation.getTypeBed())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .kids(reservation.getKids())
                .adults(reservation.getAdults())
                .room(reservation.getRoom())
                .user(reservation.getUser())
                .created(reservation.getCreated())
                .status(reservation.getStatus())
                .build();


        return reservationRepository.save(updateReservation);
    }


    public Reservation findReservationByRoomIdAndId(Long id, Long rid) {
        return reservationRepository.findReservationByRoomIdAndId(id, rid)
                .orElseThrow(() -> new ResourceNotFound(String.format("Reservation with id %d does not exists", rid)));
    }

    @Override
    public Reservation findByUserIdAndId(Long uid, Long rid) {
        Reservation reservation = reservationRepository.findByUserIdAndId(uid, rid);
        if (reservation == null) throw new ResourceNotFound(String.format("Reservation with id %d does not exists", rid));
        return reservation;
    }

    @Override
    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }
}
