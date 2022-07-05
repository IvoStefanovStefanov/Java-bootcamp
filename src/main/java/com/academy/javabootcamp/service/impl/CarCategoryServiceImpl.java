package com.academy.javabootcamp.service.impl;

import com.academy.javabootcamp.exception.ResourceNotFound;
import com.academy.javabootcamp.model.CarCategory;
import com.academy.javabootcamp.repository.CarCategoryRepository;
import com.academy.javabootcamp.service.CarCategoryService;

public class CarCategoryServiceImpl implements CarCategoryService {

    private final CarCategoryRepository carCategoryRepository;

    public CarCategoryServiceImpl(CarCategoryRepository carCategoryRepository) {
        this.carCategoryRepository = carCategoryRepository;
    }

    @Override
    public CarCategory findById(Long id) {
        return carCategoryRepository.findById(id).orElseThrow(() ->
                new ResourceNotFound(String.format("CarCategory with ID: %s not found in our database", id)));
    }
}
