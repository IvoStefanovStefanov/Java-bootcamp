package com.academy.javabootcamp.converter.carConverter;

import com.academy.javabootcamp.converter.TransferSummarizeConverter;
import com.academy.javabootcamp.dto.TransferSummarizeResponse;
import com.academy.javabootcamp.model.Car;
import com.academy.javabootcamp.model.CarCategory;
import com.academy.javabootcamp.model.CarImage;
import com.academy.javabootcamp.model.CarTransfer;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TransferSummarizeConverterTest {

    @InjectMocks
    TransferSummarizeConverter transferSummarizeConverter;

    @Test
    public void assertThatTransferSummarizeConverterIsConvertingToTransferSummarizeResponse() {
        List<CarImage> images = new ArrayList<>();
        CarCategory carCategory = new CarCategory();
        Car car = Car.builder()
                .carCategory(carCategory)
                .brand("BWV")
                .model("X5")
                .image("https://images.app.goo.gl/newImage.jpg")
                .images(images)
                .build();

        CarTransfer carTransfer = CarTransfer.builder()
                .car(car)
                .transferDate("2022-04-13")
                .created(new Date())
                .build();

        TransferSummarizeResponse transferSummarizeResponse = transferSummarizeConverter.toTransferSummarizeResponse(carTransfer);

        Assertions.assertEquals(carTransfer.getCar().getCarCategory().getName(), transferSummarizeResponse.getCategory());
        Assertions.assertEquals(carTransfer.getCar().getBrand(), transferSummarizeResponse.getBrand());
        Assertions.assertEquals(carTransfer.getCar().getModel(), transferSummarizeResponse.getModel());
        Assertions.assertEquals(carTransfer.getCar().getImage(), transferSummarizeResponse.getImage());
        Assertions.assertEquals(carTransfer.getCar().getCarCategory().getPrice(), transferSummarizeResponse.getPrice());
        Assertions.assertEquals(carTransfer.getCar().getCarCategory().getSeats(), transferSummarizeResponse.getSeats());
        Assertions.assertEquals(carTransfer.getTransferDate(), transferSummarizeResponse.getDate());
        Assertions.assertNotNull(transferSummarizeResponse.getImages());
    }
}
