package com.academy.javabootcamp.converter;

import com.academy.javabootcamp.dto.TransferSummarizeResponse;
import com.academy.javabootcamp.model.CarImage;
import com.academy.javabootcamp.model.CarTransfer;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TransferSummarizeConverter {

    public TransferSummarizeResponse toTransferSummarizeResponse(CarTransfer carTransfer) {
        return TransferSummarizeResponse.builder()
                .id(carTransfer.getId())
                .category(carTransfer.getCar().getCarCategory().getName())
                .brand(carTransfer.getCar().getBrand())
                .model(carTransfer.getCar().getModel())
                .image(carTransfer.getCar().getImage())
                .images(carTransfer.getCar().getImages()
                        .stream()
                        .map(CarImage::getCarImage)
                        .collect(Collectors.toList()))
                .seats(carTransfer.getCar().getCarCategory().getSeats())
                .price(carTransfer.getCar().getCarCategory().getPrice())
                .date(carTransfer.getTransferDate())
                .build();
    }
}
