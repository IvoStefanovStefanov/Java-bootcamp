package com.academy.javabootcamp.filters;

import com.academy.javabootcamp.converter.RoomDetailsConverter;
import com.academy.javabootcamp.dto.RoomDetailsDto;
import com.academy.javabootcamp.model.Room;
import com.academy.javabootcamp.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class FreeRoomFilter {

    @Autowired
    private final RoomService roomService;

    private final RoomDetailsConverter roomDetailsConverter;

    public Boolean isNull(String startDate, String endDate, Integer adults, Integer kids) {
        return startDate == null || endDate == null || adults == null || kids == null;
    }

    public ResponseEntity<List<RoomDetailsDto>> getListResponseEntity(String startDate, String endDate, Integer adults, Integer kids) {
        if (!this.isNull(startDate, endDate, adults, kids)) {
            List<Room> availableRoomsList = roomService.findAllAvailableRooms(startDate, endDate, adults, kids);
            return ResponseEntity.ok(availableRoomsList.stream()
                    .map(roomDetailsConverter::toRoomDetailsDto)
                    .toList());
        } else {
            List<RoomDetailsDto> roomDetailsDtoList = roomService.findAll().stream().map(roomDetailsConverter::toRoomDetailsDto).toList();
            return ResponseEntity.ok(roomDetailsDtoList);
        }
    }
}
