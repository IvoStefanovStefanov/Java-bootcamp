package com.academy.javabootcamp.converter;

import com.academy.javabootcamp.dto.TransferSummarizeRequest;
import com.academy.javabootcamp.model.Car;
import com.academy.javabootcamp.model.CarTransfer;
import com.academy.javabootcamp.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CarTransferBuilder {
    private final CarService carService;

    public CarTransfer buildSummarize(TransferSummarizeRequest transferSummarizeRequest, Long id) {
        Car foundCar = carService.findById(id);

        CarTransfer carTransfer = CarTransfer.builder()
                .id(id)
                .car(foundCar)
                .transferDate(transferSummarizeRequest.getDate())
                .build();

        return carTransfer;
    }
}
