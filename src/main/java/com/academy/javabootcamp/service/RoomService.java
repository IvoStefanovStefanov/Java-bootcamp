package com.academy.javabootcamp.service;

import com.academy.javabootcamp.model.Room;

import java.util.List;

public interface RoomService {

    Room findById(Long id);

    Room save(Room room);

    Room update(Room room, Long id);

    List<Room> findAll();

    void delete(Long id);

    List<Room> findAllAvailableRooms(String startDate, String endDate, Integer Adults, Integer Kids);

}
