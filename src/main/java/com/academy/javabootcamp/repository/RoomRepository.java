package com.academy.javabootcamp.repository;

import com.academy.javabootcamp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Room findByTitle(String title);

    @Query(value = """
            SELECT * FROM rooms r LEFT JOIN\040
            (SELECT COUNT(rr.room_id) AS reservation_count, rr.room_id FROM reservations rr
            WHERE (rr.start_date < ?1 AND rr.end_date = 'end_date' > ?2)
            OR (rr.start_date BETWEEN ?1 AND ?2)
            OR (rr.end_date BETWEEN ?1 AND ?2)
            GROUP BY rr.room_id) AS booking
            ON r.id = booking.room_id WHERE r.people = 'people' > ?3
            AND booking.reservation_count IS NULL
            OR r.count > booking.reservation_count
            """,
            nativeQuery = true)
    Optional<List<Room>> findAllAvailableRooms(String startDate, String endDate, Integer Adults, Integer Kids);

}
