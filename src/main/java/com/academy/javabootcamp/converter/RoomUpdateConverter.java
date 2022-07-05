package com.academy.javabootcamp.converter;

import com.academy.javabootcamp.dto.RoomUpdateRequest;
import com.academy.javabootcamp.dto.RoomUpdateResponse;
import com.academy.javabootcamp.model.Image;
import com.academy.javabootcamp.model.Room;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RoomUpdateConverter {

    public Room toRoom(RoomUpdateRequest roomUpdateRequest) {

        return Room.builder()
                .id(roomUpdateRequest.getId())
                .title(roomUpdateRequest.getTitle())
                .image(roomUpdateRequest.getImage())
                .images(roomUpdateRequest.getImages().stream()
                        .map(image -> Image.builder().roomImage(image).build())
                        .collect(Collectors.toList()))
                .description(roomUpdateRequest.getDescription())
                .facilities(roomUpdateRequest.getFacilities())
                .area(roomUpdateRequest.getArea())
                .people(roomUpdateRequest.getPeople())
                .price(roomUpdateRequest.getPrice())
                .count(roomUpdateRequest.getCount())
                .build();
    }

    public RoomUpdateResponse toRoomResponse(Room room) {

        return RoomUpdateResponse.builder()
                .id(room.getId())
                .title(room.getTitle())
                .image(room.getImage())
                .facilities(room.getFacilities())
                .description(room.getDescription())
                .area(room.getArea())
                .people(room.getPeople())
                .price(room.getPrice())
                .images(room.getImages()
                        .stream()
                        .map(Image::getRoomImage)
                        .collect(Collectors.toList()))
                .build();
    }
}




