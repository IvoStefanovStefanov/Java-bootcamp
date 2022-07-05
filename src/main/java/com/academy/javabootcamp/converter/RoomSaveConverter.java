package com.academy.javabootcamp.converter;

import com.academy.javabootcamp.dto.RoomSaveRequest;
import com.academy.javabootcamp.dto.RoomSaveResponse;
import com.academy.javabootcamp.model.Image;
import com.academy.javabootcamp.model.Room;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RoomSaveConverter {

    public Room toRoom(RoomSaveRequest roomSaveRequest) {

        return Room.builder()
                .title(roomSaveRequest.getTitle())
                .image(roomSaveRequest.getImage())
                .images(roomSaveRequest.getImages()
                        .stream()
                        .map(image -> Image.builder().roomImage(image).build())
                        .collect(Collectors.toList()))
                .description(roomSaveRequest.getDescription())
                .facilities(roomSaveRequest.getFacilities())
                .area(roomSaveRequest.getArea())
                .people(roomSaveRequest.getPeople())
                .price(roomSaveRequest.getPrice())
                .count(roomSaveRequest.getCount())
                .build();
    }

    public RoomSaveResponse toRoomResponse(Room room) {

        return RoomSaveResponse.builder()
                .id(room.getId())
                .title(room.getTitle())
                .image(room.getImage())
                .images(room.getImages().stream().map(Image::getRoomImage).collect(Collectors.toList()))
                .description(room.getDescription())
                .facilities(room.getFacilities())
                .area(room.getArea())
                .people(room.getPeople())
                .price(room.getPrice())
                .build();
    }
}
