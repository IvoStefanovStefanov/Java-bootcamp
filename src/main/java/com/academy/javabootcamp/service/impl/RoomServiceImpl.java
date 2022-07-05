package com.academy.javabootcamp.service.impl;

import com.academy.javabootcamp.exception.DuplicateRecordException;
import com.academy.javabootcamp.exception.ResourceNotFound;
import com.academy.javabootcamp.model.Room;
import com.academy.javabootcamp.repository.RoomRepository;
import com.academy.javabootcamp.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.IllegalFormatConversionException;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Room save(Room room) {
        try {
            Room foundRoom = roomRepository.findByTitle(room.getTitle());
            if (foundRoom != null) {
                throw new DuplicateRecordException(String.format("Room with title %s already exists", room.getTitle()));
            }
        } catch (IllegalFormatConversionException e) {
            throw new DuplicateRecordException("Room with the same title already exists");
        }
        return roomRepository.save(room);
    }

    @Override
    public Room findById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound(String.format("Room with id %d does not exists", id)));
    }

    @Override
    public Room update(Room room, Long id) {
        Room foundRoom = this.findById(id);
        Room updatedRoom = Room.builder()
                .id(foundRoom.getId())
                .title(room.getTitle())
                .image(room.getImage())
                .description(room.getDescription())
                .facilities(room.getFacilities())
                .area(room.getArea())
                .people(room.getPeople())
                .price(room.getPrice())
                .count(room.getCount())
                .images(room.getImages())
                .build();

        return roomRepository.save(updatedRoom);
    }


    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound(String.format("Room with id %d is not found", id)));
        roomRepository.deleteById(room.getId());

    }

    @Override
    public List<Room> findAllAvailableRooms(String startDate, String endDate, Integer adults, Integer kids) {

        return roomRepository.findAllAvailableRooms(startDate, endDate, adults, kids)
                .orElseThrow(() ->
                        new ResourceNotFound("There are no available rooms for the selected period and number of guests"));
    }

}