package com.academy.javabootcamp.converter.userConverter;

import com.academy.javabootcamp.converter.AllReservationsByAllUsersConverter;
import com.academy.javabootcamp.dto.AllUsersReservationsResponse;
import com.academy.javabootcamp.model.Reservation;
import com.academy.javabootcamp.model.Room;
import com.academy.javabootcamp.model.User;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class AllReservationsByAllUsersConverterTest {

    @Test
    public void assertThatUserReservationConverterIsConvertingToUserReservationDtoCorrectly() {
        AllReservationsByAllUsersConverter allReservationsByAllUsersConverter = new AllReservationsByAllUsersConverter();
        Date create = new Date();
        Room room = new Room();
        room.setTitle("new room");

        Reservation reservation = Reservation.builder()
                .user(User.builder().build())
                .room(room)
                .adults(2)
                .kids(1)
                .startDate("22-12-2022")
                .endDate("24-12-2022")
                .days(2L)
                .typeBed("king size")
                .typeView("sea view")
                .price(25.00)
                .status("new")
                .created(create)
                .build();

        AllUsersReservationsResponse allUsersReservationsResponse = allReservationsByAllUsersConverter.
                toAllUserReservationResponse(reservation);

        Assertions.assertEquals(allUsersReservationsResponse.getAdults(), reservation.getAdults());
        Assertions.assertEquals(allUsersReservationsResponse.getKids(), reservation.getKids());
        Assertions.assertEquals(allUsersReservationsResponse.getStartDate(), reservation.getStartDate());
        Assertions.assertEquals(allUsersReservationsResponse.getEndDate(), reservation.getEndDate());
        Assertions.assertEquals(allUsersReservationsResponse.getDays(), reservation.getDays());
        Assertions.assertEquals(allUsersReservationsResponse.getTypeBed(), reservation.getTypeBed());
        Assertions.assertEquals(allUsersReservationsResponse.getTypeView(), reservation.getTypeView());
        Assertions.assertEquals(allUsersReservationsResponse.getPrice(), reservation.getPrice());
        Assertions.assertEquals(allUsersReservationsResponse.getStatus(), reservation.getStatus());
        Assertions.assertNotNull(allUsersReservationsResponse.getDate());
        Assertions.assertNotNull(allUsersReservationsResponse.getRoom());
        Assertions.assertNotNull(allUsersReservationsResponse.getUser());
    }
}
