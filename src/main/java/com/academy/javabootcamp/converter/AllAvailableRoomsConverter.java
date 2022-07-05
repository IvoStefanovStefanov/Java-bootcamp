package com.academy.javabootcamp.converter;


import com.academy.javabootcamp.dto.AllAvailableRoomsResponse;
import com.academy.javabootcamp.model.Image;
import com.academy.javabootcamp.model.Room;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AllAvailableRoomsConverter {

    public static AllAvailableRoomsResponse toAllAvailableRoomsResponse(Room room) {
        return AllAvailableRoomsResponse.builder()
                .id(room.getId())
                .title(room.getTitle())
                .image(room.getImage())
                .images(room.getImages()
                        .stream()
                        .map(Image::getRoomImage)
                        .collect(Collectors.toList()))
                .description(room.getDescription())
                .facilities(room.getFacilities())
                .area(room.getArea())
                .people(room.getPeople())
                .price(room.getPrice())
                .build();
    }
}
