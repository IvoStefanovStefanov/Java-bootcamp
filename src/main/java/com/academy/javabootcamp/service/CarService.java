package com.academy.javabootcamp.service;

import com.academy.javabootcamp.model.Car;

import java.util.List;

public interface CarService {

    Car save(Car car);

    Car updateCar(Car car, Long id);

    List<Car> findAll();

    Car findById(Long id);

    void delete(Long id);
}
