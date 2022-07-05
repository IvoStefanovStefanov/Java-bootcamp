package com.academy.javabootcamp.service;

import com.academy.javabootcamp.model.Reservation;

import java.util.List;

public interface ReservationService {

    Reservation save(Reservation reservation);

    Reservation findById(Long id);

    List<Reservation> findAll();

    Reservation update(Reservation reservation, Long id);

    Reservation findReservationByRoomIdAndId(Long id, Long rid);

    Reservation findByUserIdAndId(Long uid, Long rid);

    void deleteById(Long id);
}
