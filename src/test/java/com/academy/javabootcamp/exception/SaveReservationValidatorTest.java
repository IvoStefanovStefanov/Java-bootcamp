package com.academy.javabootcamp.exception;

import com.academy.javabootcamp.model.Reservation;
import com.academy.javabootcamp.model.Room;
import com.academy.javabootcamp.validator.SaveReservationValidator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class SaveReservationValidatorTest {

    private static final String NOT_AVAILABLE_ROOM_MESSAGE = "This type of room %s is not available for this period of time";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    private SaveReservationValidator saveReservationValidator;

    @Test
    public void verifyNumberOfGuestsBiggerThanRoomCapacity() {
        int capacity = 2;
        int numberOfGusts = 4;
        expectedException.expect(ReservationDataNotAppropriateException.class);
        expectedException.expectMessage("This room is not big enough for this number of guests");
        saveReservationValidator.validateNumberOfGuests(capacity, numberOfGusts);

    }

    @Test
    public void verifyReservationStartDateAfterExistingStartDateAndBeforeExistingEndDate() {
        List<Reservation> reservations = new ArrayList<>();

        Room room = buildTestRoom(reservations);

        LocalDate existingStartDate = LocalDate.now();
        LocalDate startDate = existingStartDate.plusDays(1);
        LocalDate endDate = existingStartDate.plusDays(6);
        LocalDate existingEndDate = existingStartDate.plusDays(5);

        Collections.addAll(reservations, buildTestReservation(reservations, existingStartDate, existingEndDate),
                buildTestReservation(reservations, existingStartDate, existingEndDate));

        Long numberDays = ChronoUnit.DAYS.between(startDate, endDate);

        expectedException.expect(ReservationDataNotAppropriateException.class);
        expectedException.expectMessage(String.format(NOT_AVAILABLE_ROOM_MESSAGE, room.getTitle()));
        saveReservationValidator.validateTimePeriodIsOverlapOtherReservations(startDate, endDate, numberDays, room);
    }

    @Test
    public void verifyReservationStartDateEqualExistingStartDateAndBeforeExistingEndDate() {
        List<Reservation> reservations = new ArrayList<>();

        Room room = buildTestRoom(reservations);

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(6);
        LocalDate existingEndDate = startDate.plusDays(5);

        Collections.addAll(reservations, buildTestReservation(reservations, startDate, existingEndDate),
                buildTestReservation(reservations, startDate, existingEndDate));

        Long numberDays = ChronoUnit.DAYS.between(startDate, endDate);

        expectedException.expect(ReservationDataNotAppropriateException.class);
        expectedException.expectMessage(String.format(NOT_AVAILABLE_ROOM_MESSAGE, room.getTitle()));
        saveReservationValidator.validateTimePeriodIsOverlapOtherReservations(startDate, endDate, numberDays, room);
    }

    @Test
    public void verifyReservationStartDateAfterExistingStartDateAndEqualExistingEndDate() {
        List<Reservation> reservations = new ArrayList<>();

        Room room = buildTestRoom(reservations);

        LocalDate existingStartDate = LocalDate.now();
        LocalDate startDate = existingStartDate.plusDays(1);
        LocalDate endDate = existingStartDate.plusDays(6);

        Collections.addAll(reservations, buildTestReservation(reservations, existingStartDate, endDate),
                buildTestReservation(reservations, existingStartDate, endDate));

        Long numberDays = ChronoUnit.DAYS.between(startDate, endDate);

        expectedException.expect(ReservationDataNotAppropriateException.class);
        expectedException.expectMessage(String.format(NOT_AVAILABLE_ROOM_MESSAGE, room.getTitle()));
        saveReservationValidator.validateTimePeriodIsOverlapOtherReservations(startDate, endDate, numberDays, room);
    }

    @Test
    public void verifyReservationStartDateEqualExistingStartDateAndEqualExistingEndDate() {
        List<Reservation> reservations = new ArrayList<>();

        Room room = buildTestRoom(reservations);

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(6);

        Collections.addAll(reservations, buildTestReservation(reservations, startDate, endDate),
                buildTestReservation(reservations, startDate, endDate));

        Long numberDays = ChronoUnit.DAYS.between(startDate, endDate);

        expectedException.expect(ReservationDataNotAppropriateException.class);
        expectedException.expectMessage(String.format(NOT_AVAILABLE_ROOM_MESSAGE, room.getTitle()));
        saveReservationValidator.validateTimePeriodIsOverlapOtherReservations(startDate, endDate, numberDays, room);
    }

    @Test
    public void verifyReservationEndDateBeforeExistingEndDate() {
        List<Reservation> reservations = new ArrayList<>();

        Room room = buildTestRoom(reservations);

        LocalDate existingStartDate = LocalDate.now();
        LocalDate startDate = existingStartDate.minusDays(2);
        LocalDate endDate = existingStartDate.plusDays(6);
        LocalDate existingEndDate = endDate.plusDays(1);

        Collections.addAll(reservations, buildTestReservation(reservations, existingStartDate, existingEndDate),
                buildTestReservation(reservations, existingStartDate, existingEndDate));

        Long numberDays = ChronoUnit.DAYS.between(startDate, endDate);

        expectedException.expect(ReservationDataNotAppropriateException.class);
        expectedException.expectMessage(String.format(NOT_AVAILABLE_ROOM_MESSAGE, room.getTitle()));
        saveReservationValidator.validateTimePeriodIsOverlapOtherReservations(startDate, endDate, numberDays, room);
    }

    @Test
    public void verifyReservationEndDateEqualExistingEndDate() {
        List<Reservation> reservations = new ArrayList<>();

        Room room = buildTestRoom(reservations);

        LocalDate existingStartDate = LocalDate.now();
        LocalDate startDate = existingStartDate.minusDays(2);
        LocalDate endDate = existingStartDate.plusDays(6);

        Collections.addAll(reservations, buildTestReservation(reservations, existingStartDate, endDate),
                buildTestReservation(reservations, existingStartDate, endDate));

        Long numberDays = ChronoUnit.DAYS.between(startDate, endDate);

        expectedException.expect(ReservationDataNotAppropriateException.class);
        expectedException.expectMessage(String.format(NOT_AVAILABLE_ROOM_MESSAGE, room.getTitle()));
        saveReservationValidator.validateTimePeriodIsOverlapOtherReservations(startDate, endDate, numberDays, room);
    }

    @Test
    public void verifyIfExistingReservationIsInNewReservation() {
        List<Reservation> reservations = new ArrayList<>();

        Room room = buildTestRoom(reservations);

        LocalDate existingStartDate = LocalDate.now();
        LocalDate startDate = existingStartDate.minusDays(2);
        LocalDate existingEndDate = existingStartDate.plusDays(5);
        LocalDate endDate = existingEndDate.plusDays(2);

        Collections.addAll(reservations, buildTestReservation(reservations, existingStartDate, existingEndDate),
                buildTestReservation(reservations, existingStartDate, existingEndDate));

        Long numberDays = ChronoUnit.DAYS.between(startDate, endDate);

        expectedException.expect(ReservationDataNotAppropriateException.class);
        expectedException.expectMessage(String.format(NOT_AVAILABLE_ROOM_MESSAGE, room.getTitle()));
        saveReservationValidator.validateTimePeriodIsOverlapOtherReservations(startDate, endDate, numberDays, room);
    }

    private Room buildTestRoom(List<Reservation> reservations) {

        return Room.builder()
                .title("Apartment")
                .count(1)
                .reservations(reservations)
                .build();
    }

    private Reservation buildTestReservation(List<Reservation> reservations, LocalDate existingStartDate, LocalDate existingEndDate) {

        return Reservation.builder()
                .room(buildTestRoom(reservations))
                .startDate(existingStartDate.toString())
                .endDate(existingEndDate.toString())
                .build();
    }
}
