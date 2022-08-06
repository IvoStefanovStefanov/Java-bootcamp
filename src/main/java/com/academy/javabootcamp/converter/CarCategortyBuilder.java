package com.academy.javabootcamp.converter;

import com.academy.javabootcamp.dto.CarCategoryRequest;
import com.academy.javabootcamp.dto.CarCategoryResponse;
import com.academy.javabootcamp.model.CarCategory;
import com.academy.javabootcamp.repository.CarCategoryRepository;
import com.academy.javabootcamp.repository.CarRepository;
import org.springframework.stereotype.Component;

@Component
public class CarCategortyBuilder {

//    private final CarRepository carRepository;
//
//    public CarCategortyBuilder( CarRepository carRepository) {
//        this.carRepository = carRepository;
//    }

    public CarCategory toCarCategory(CarCategoryRequest carCategoryRequest) {
        CarCategory carCategory =  CarCategory.builder()
                .name(carCategoryRequest.getTitle())
                .seats(carCategoryRequest.getSeats())
                .price(carCategoryRequest.getPrice())
                .carNumber(carCategoryRequest.getNumber())
                .build();

        return carCategory;
    }

    public CarCategoryResponse toCarCategoryResponse(CarCategory carCategory) {
        CarCategoryResponse carCategoryResponse =  CarCategoryResponse.builder()
                .id(carCategory.getId())
                .title(carCategory.getName())
                .seats(carCategory.getSeats())
                .price(carCategory.getPrice())
                .build();

        return carCategoryResponse;
    }
}
