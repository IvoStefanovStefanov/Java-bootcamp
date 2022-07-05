package com.academy.javabootcamp.converter;

import com.academy.javabootcamp.dto.RoomDetailsDto;
import com.academy.javabootcamp.model.Image;
import com.academy.javabootcamp.model.Room;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RoomDetailsConverter {

    public RoomDetailsDto toRoomDetailsDto(Room room) {
        return RoomDetailsDto.builder()
                .id(room.getId())
                .title(room.getTitle())
                .description(room.getDescription())
                .area(room.getArea())
                .count(room.getCount())
                .price(room.getPrice())
                .people(room.getPeople())
                .image(room.getImage())
                .images(room.getImages()
                        .stream()
                        .map(Image::getRoomImage)
                        .collect(Collectors.toList()))
                .facilities(room.getFacilities())
                .build();
    }
}
