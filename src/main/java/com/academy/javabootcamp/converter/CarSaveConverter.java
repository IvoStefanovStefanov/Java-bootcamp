package com.academy.javabootcamp.converter;

import com.academy.javabootcamp.dto.CarSaveRequest;
import com.academy.javabootcamp.dto.CarSaveResponse;
import com.academy.javabootcamp.model.Car;
import com.academy.javabootcamp.model.CarCategory;
import com.academy.javabootcamp.model.CarImage;
import com.academy.javabootcamp.repository.CarCategoryRepository;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CarSaveConverter {

    private final CarCategoryRepository carCategoryRepository;

    public CarSaveConverter(CarCategoryRepository carCategoryRepository) {
        this.carCategoryRepository = carCategoryRepository;
    }

    public Car toCar(CarSaveRequest carSaveRequest) {

        CarCategory foundCarCategory = carCategoryRepository.getById(carSaveRequest.getCategory());

        return Car.builder()
                .carCategory(foundCarCategory)
                .brand(carSaveRequest.getBrand())
                .model(carSaveRequest.getModel())
                .image(carSaveRequest.getImage())
                .images(carSaveRequest.getImages()
                        .stream()
                        .map(image -> CarImage.builder().carImage(image).build())
                        .collect(Collectors.toList()))
                .year(carSaveRequest.getYear())
                .build();
    }

    public CarSaveResponse toCarResponse(Car car) {

        return CarSaveResponse.builder()
                .id(car.getId())
                .category(car.getCarCategory().getName())
                .brand(car.getBrand())
                .model(car.getModel())
                .image(car.getImage())
                .images(car.getImages().stream().map(CarImage::getCarImage).collect(Collectors.toList()))
                .seats(car.getCarCategory().getSeats())
                .price(car.getCarCategory().getPrice())
                .build();
    }
}
