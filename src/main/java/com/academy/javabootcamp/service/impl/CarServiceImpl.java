package com.academy.javabootcamp.service.impl;

import com.academy.javabootcamp.exception.ResourceNotFound;
import com.academy.javabootcamp.model.Car;
import com.academy.javabootcamp.repository.CarRepository;
import com.academy.javabootcamp.service.CarService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car findById(Long id) {
        return carRepository.findById(id).orElseThrow(() ->
                new ResourceNotFound(String.format("Car with ID: %s not found in our database", id)));
    }

    @Override
    public Car updateCar(Car car, Long id) {

        Car foundCar = this.findById(id);
        Car carToUpdate = Car.builder()
                .id(foundCar.getId())
                .carCategory(car.getCarCategory())
                .brand(car.getBrand())
                .model(car.getModel())
                .image(car.getImage())
                .images(car.getImages())
                .year(car.getYear())
                .build();
        return this.save(carToUpdate);
    }

    public void delete(Long id) {
        Car car = this.findById(id);
        carRepository.deleteById(car.getId());
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

}
