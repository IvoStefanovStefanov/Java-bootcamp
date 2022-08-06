package com.academy.javabootcamp.service.impl;

import com.academy.javabootcamp.dto.CarCategoryResponse;
import com.academy.javabootcamp.exception.ResourceNotFound;
import com.academy.javabootcamp.model.CarCategory;
import com.academy.javabootcamp.repository.CarCategoryRepository;
import com.academy.javabootcamp.service.CarCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarCategoryServiceImpl implements CarCategoryService {

    private final CarCategoryRepository carCategoryRepository;

    public CarCategoryServiceImpl(CarCategoryRepository carCategoryRepository) {
        this.carCategoryRepository = carCategoryRepository;
    }

    @Override
    public List<CarCategory> findAll() {
        return carCategoryRepository.findAll();
    }

    @Override
    public CarCategory findById(Long id) {
        return carCategoryRepository.findById(id).orElseThrow(() ->
                new ResourceNotFound(String.format("CarCategory with ID: %s not found in our database", id)));
    }

    @Override
    public CarCategory save(CarCategory carCategory) {
        return carCategoryRepository.save(carCategory);
    }

    @Override
    public CarCategory update(CarCategory carCategory, Long id) {
        CarCategory foundCarCategory = carCategoryRepository.findById(id).orElseThrow(() ->
                new ResourceNotFound(String.format("CarCategory with ID: %s not found in our database", id)));

        CarCategory updatedCarCategory = CarCategory.builder()
                .id(foundCarCategory.getId())
                .carNumber(carCategory.getCarNumber())
                .seats(carCategory.getSeats())
                .price(carCategory.getPrice())
                .name(carCategory.getName())
                .build();

        return carCategoryRepository.save(updatedCarCategory);

    }

    @Override
    public void delete(Long id) {
         carCategoryRepository.findById(id).orElseThrow(() ->
                new ResourceNotFound(String.format("CarCategory with ID: %s not found in our database", id)));

         carCategoryRepository.deleteById(id);
    }
}
