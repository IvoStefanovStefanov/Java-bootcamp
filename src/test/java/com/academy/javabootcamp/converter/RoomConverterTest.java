package com.academy.javabootcamp.converter;

import com.academy.javabootcamp.dto.*;
import com.academy.javabootcamp.model.Image;
import com.academy.javabootcamp.model.Room;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class RoomConverterTest {


    @Test
    public void assertEqualsConverterRoomSaveRequestWithRoom() {
        RoomSaveConverter roomSaveConverter = new RoomSaveConverter();
        List<String> imageList = new ArrayList<>();
        RoomSaveRequest roomSaveRequest = RoomSaveRequest.builder()
                .title("Двустаен апартамент")
                .image("https://images.niceview/newImage1.jpg")
                .images(imageList)
                .description("с изглед към морето")
                .facilities("ВИП зона,конферентна стая,тераса,кръгла спалня,джакузи,3 Д")
                .area(26.0)
                .people(2)
                .price(100.0)
                .count(2)
                .build();

        Room room = roomSaveConverter.toRoom(roomSaveRequest);

        Assertions.assertEquals(roomSaveRequest.getTitle(), room.getTitle());
        Assertions.assertEquals(roomSaveRequest.getImage(), room.getImage());
        Assertions.assertNotNull(roomSaveRequest.getImages());
        Assertions.assertEquals(roomSaveRequest.getDescription(), room.getDescription());
        Assertions.assertEquals(roomSaveRequest.getFacilities(), room.getFacilities());
        Assertions.assertEquals(roomSaveRequest.getArea(), room.getArea());
        Assertions.assertEquals(roomSaveRequest.getPeople(), room.getPeople());
        Assertions.assertEquals(roomSaveRequest.getPrice(), room.getPrice());
    }

    @Test
    public void assertEqualsConverterRoomResponseWithRoom() {
        RoomSaveConverter roomSaveConverter = new RoomSaveConverter();
        List<Image> images = new ArrayList<>();
        Room room = Room.builder()
                .id(30L)
                .title("Двустаен апартамент")
                .image("https://images.niceview/newImage1.jpg")
                .images(images)
                .description("с изглед към морето")
                .facilities("ВИП зона,конферентна стая,тераса,кръгла спалня,джакузи,3 Д")
                .area(26.0)
                .people(56)
                .price(100.0)
                .build();


        RoomSaveResponse roomResponse = roomSaveConverter.toRoomResponse(room);

        Assertions.assertEquals(roomResponse.getTitle(), room.getTitle());
        Assertions.assertEquals(roomResponse.getImage(), room.getImage());
        Assertions.assertNotNull(roomResponse.getImages());
        Assertions.assertEquals(roomResponse.getDescription(), room.getDescription());
        Assertions.assertEquals(roomResponse.getFacilities(), room.getFacilities());
        Assertions.assertEquals(roomResponse.getArea(), room.getArea());
        Assertions.assertEquals(roomResponse.getPeople(), room.getPeople());
        Assertions.assertEquals(roomResponse.getPrice(), room.getPrice());

    }

    @Test
    public void assertThatIsConvertedCorrectly() {
        RoomSaveConverter roomSaveConverter = new RoomSaveConverter();
        Room room = roomSaveConverter.toRoom(RoomSaveRequest.builder()
                .title("Двустаен апартамент")
                .image("https://images.niceview/newImage1.jpg")
                .images(List.of("https://images.nice.view.sea/newImage.jpg"))
                .description("с изглед към морето")
                .facilities("ВИП зона,конферентна стая,тераса,кръгла спалня,джакузи,3 Д")
                .area(26.0)
                .people(2)
                .price(100.0)
                .count(2)
                .build());

        RoomSaveResponse roomResponse = roomSaveConverter.toRoomResponse(room);

        Assertions.assertEquals(room.getTitle(), roomResponse.getTitle());
        Assertions.assertEquals(room.getImage(), roomResponse.getImage());
        Assertions.assertEquals(room.getDescription(), roomResponse.getDescription());
        Assertions.assertEquals(room.getFacilities(), roomResponse.getFacilities());
        Assertions.assertEquals(room.getArea(), roomResponse.getArea());
        Assertions.assertEquals(room.getPeople(), roomResponse.getPeople());
        Assertions.assertEquals(room.getPrice(), roomResponse.getPrice());
    }

    @Test
    public void assertRoomUpdateConverterIsConvertingProperly() {
        RoomUpdateConverter roomUpdateConverter = new RoomUpdateConverter();
        Room room = roomUpdateConverter.toRoom(RoomUpdateRequest.builder()
                .id(186L)
                .title("comfortable room")
                .image("https://images.normal.view/newImage44.jpg")
                .images(List.of("https://images.big.normal.view/newImages40.jpg"))
                .description("изглед към more")
                .facilities("тераса,спалня,трапезария,tv")
                .area(27.0)
                .people(3)
                .price(270.0)
                .count(6)
                .build());

        RoomUpdateResponse roomResponse = roomUpdateConverter.toRoomResponse(room);

        Assertions.assertEquals(room.getTitle(), roomResponse.getTitle());
        Assertions.assertEquals(room.getImage(), roomResponse.getImage());
        Assertions.assertEquals(room.getDescription(), roomResponse.getDescription());
        Assertions.assertEquals(room.getFacilities(), roomResponse.getFacilities());
        Assertions.assertEquals(room.getArea(), roomResponse.getArea());
        Assertions.assertEquals(room.getPeople(), roomResponse.getPeople());
        Assertions.assertEquals(room.getPrice(), roomResponse.getPrice());
        Assertions.assertNotNull(room.getCount());
    }

    @Test
    public void assertRoomUpdateConverterConvertCorrectly() {
        RoomUpdateConverter roomUpdateConverter = new RoomUpdateConverter();
        List<Image> roomImages = new ArrayList<>();
        Room room = Room.builder()
                .id(186L)
                .title("comfortable room")
                .image("https://images.normal.view/newImage44.jpg")
                .facilities("тераса,спалня,трапезария,tv")
                .description("изглед към more")
                .area(27.0)
                .people(3)
                .price(270.0)
                .images(roomImages)
                .build();


        RoomUpdateResponse roomResponse = roomUpdateConverter.toRoomResponse(room);

        Assertions.assertEquals(room.getTitle(), roomResponse.getTitle());
        Assertions.assertEquals(room.getImage(), roomResponse.getImage());
        Assertions.assertNotNull(room.getImages());
        Assertions.assertEquals(room.getFacilities(), roomResponse.getFacilities());
        Assertions.assertEquals(room.getDescription(), roomResponse.getDescription());
        Assertions.assertEquals(room.getArea(), roomResponse.getArea());
        Assertions.assertEquals(room.getPeople(), roomResponse.getPeople());

    }

    @Test
    public void assertRoomDetailsConverterConvertRoomDetailsProperly() {
        RoomDetailsConverter roomDetailsConverter = new RoomDetailsConverter();
        List<Image> roomImages = new ArrayList<>();
        Room room = Room.builder()
                .id(107L)
                .title("двойна стая")
                .description("две стаи и баня")
                .area(31.0)
                .count(9)
                .price(160.0)
                .people(4)
                .image("https://images.niceview4/newImage35.jpg")
                .images(roomImages)
                .facilities("телевизор,вана")
                .build();


        RoomDetailsDto roomDetailsDto = roomDetailsConverter.toRoomDetailsDto(room);

        Assertions.assertNotNull(roomDetailsDto.getTitle());
        Assertions.assertNotNull(roomDetailsDto.getDescription());
        Assertions.assertNotNull(roomDetailsDto.getArea());
        Assertions.assertNotNull(roomDetailsDto.getCount());
        Assertions.assertNotNull(roomDetailsDto.getPrice());
        Assertions.assertNotNull(roomDetailsDto.getPeople());
        Assertions.assertNotNull(roomDetailsDto.getImage());
        Assertions.assertNotNull(roomDetailsDto.getImages());
        Assertions.assertNotNull(roomDetailsDto.getFacilities());

    }

}











