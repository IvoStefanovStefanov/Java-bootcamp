package com.academy.javabootcamp.service;

import com.academy.javabootcamp.model.CarCategory;

import java.util.List;

public interface CarCategoryService {

    List<CarCategory> findAll();

    CarCategory findById(Long id);

    CarCategory save(CarCategory carCategory);

    CarCategory update(CarCategory carCategory, Long id);

    void delete(Long id);
}
