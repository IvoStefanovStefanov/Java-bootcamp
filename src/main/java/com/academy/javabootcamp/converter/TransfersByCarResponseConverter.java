package com.academy.javabootcamp.converter;

import com.academy.javabootcamp.model.CarTransfer;
import com.academy.javabootcamp.dto.CarAllTransfersResponse;
import org.springframework.stereotype.Component;

@Component
public class TransfersByCarResponseConverter {

    public CarAllTransfersResponse toTransferByCarResponse(CarTransfer carTransfer) {

        return CarAllTransfersResponse.builder()
                .id(carTransfer.getId())
                .category(carTransfer.getCar().getCarCategory().getName())
                .brand((carTransfer.getCar().getBrand()))
                .model(carTransfer.getCar().getModel())
                .image(carTransfer.getCar().getImage())
                .price(carTransfer.getCar().getCarCategory().getPrice())
                .date(carTransfer.getTransferDate())
                .userId(carTransfer.getUser().getId())
                .user(carTransfer.getUser().fullName())
                .created(carTransfer.getCreated().toString())
                .build();
    }
}
