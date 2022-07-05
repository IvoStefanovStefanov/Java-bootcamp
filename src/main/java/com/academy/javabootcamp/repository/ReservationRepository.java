package com.academy.javabootcamp.repository;

import com.academy.javabootcamp.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation findByUserIdAndId(Long uid, Long rid);


    Optional<Reservation> findReservationByRoomIdAndId(Long rid, Long id);

    void deleteById(Long id);
}
