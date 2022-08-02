package com.academy.javabootcamp.converter.carConverter;

import com.academy.javabootcamp.converter.TransfersByCarResponseConverter;
import com.academy.javabootcamp.dto.CarAllTransfersResponse;
import com.academy.javabootcamp.model.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class TransfersByCarResponseConverterTest {

    @InjectMocks
    TransfersByCarResponseConverter transfersByCarResponseConverter;

    @Test
    public void assertThatTransferByCarResponseConverterIsConvertingTransferByCarResponseCorrectly() {
        Set<Role> userRoles = new HashSet<>();
        List<CarImage> images = new ArrayList<>();
        CarCategory carCategory = new CarCategory();
        Car car = Car.builder()
                .carCategory(carCategory)
                .brand("BWV")
                .model("X5")
                .image("https://images.app.goo.gl/newImage.jpg")
                .images(images)
                .build();

        User user = User.builder()
                .email("Georgi_Georgiev@abv.bg")
                .name("Georgi")
                .surname("Georgiev")
                .phone("0875646353")
                .roles(userRoles)
                .build();

        CarTransfer carTransfer = CarTransfer.builder()
                .user(user)
                .car(car)
                .transferDate("2022-04-13")
                .created(new Date())
                .build();

        CarAllTransfersResponse carAllTransfersResponse = transfersByCarResponseConverter.toTransferByCarResponse(carTransfer);

        Assertions.assertEquals(carTransfer.getCar().getCarCategory().getName(), carAllTransfersResponse.getCategory());
        Assertions.assertEquals(carTransfer.getCar().getBrand(), carAllTransfersResponse.getBrand());
        Assertions.assertEquals(carTransfer.getCar().getModel(), carAllTransfersResponse.getModel());
        Assertions.assertEquals(carTransfer.getCar().getImage(), carAllTransfersResponse.getImage());
        Assertions.assertEquals(carTransfer.getCar().getCarCategory().getPrice(), carAllTransfersResponse.getPrice());
        Assertions.assertEquals(carTransfer.getUser().getId(), carAllTransfersResponse.getUserId());
        Assertions.assertEquals(carTransfer.getUser().fullName(), carAllTransfersResponse.getUser());
        Assertions.assertEquals(carTransfer.getTransferDate(), carAllTransfersResponse.getDate());
        Assertions.assertNotNull(carAllTransfersResponse.getCreated());
    }
}
