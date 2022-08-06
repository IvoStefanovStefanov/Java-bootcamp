package com.academy.javabootcamp.controller;


import com.academy.javabootcamp.converter.*;
import com.academy.javabootcamp.dto.*;
import com.academy.javabootcamp.model.Car;
import com.academy.javabootcamp.model.CarCategory;
import com.academy.javabootcamp.model.CarTransfer;
import com.academy.javabootcamp.service.CarCategoryService;
import com.academy.javabootcamp.service.CarService;
import com.academy.javabootcamp.validator.CarTransferValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/cars")
public class CarController {

    private final CarSaveConverter carSaveConverter;
    private final CarService carService;
    private final TransfersByCarResponseConverter transfersByCarResponseConverter;
    private final CarTransferBuilder carTransferBuilder;
    private final TransferSummarizeConverter transferSummarizeConverter;
    private final CarTransferValidator carTransferValidator;
    private final CarCategortyBuilder carCategortyBuilder;
    private final CarCategoryService carCategoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarSaveResponse> saveCar(@RequestBody @Valid CarSaveRequest carSaveRequest) {
        Car car = carSaveConverter.toCar(carSaveRequest);
        Car savedCar = carService.save(car);

        return ResponseEntity.status(HttpStatus.CREATED).body(carSaveConverter.toCarResponse(savedCar));
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {

        carService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<CarSaveResponse>> findAll() {
        List<Car> findAllCars = carService.findAll();
        return ResponseEntity.ok(findAllCars
                .stream()
                .map(carSaveConverter::toCarResponse)
                .collect(Collectors.toList()));
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarSaveResponse> updateCar(@RequestBody @Valid CarSaveRequest carSaveRequest,
                                                     @PathVariable Long id) {
        Car toCar = carSaveConverter.toCar(carSaveRequest);
        Car update = carService.updateCar(toCar, id);
        return ResponseEntity.ok(carSaveConverter
                .toCarResponse(update));

    }

    @GetMapping(value = "/{id}/transfers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CarAllTransfersResponse>> findAllTransfers(@PathVariable Long id) {
        Car car = carService.findById(id);
        List<CarTransfer> carTransfers = car.getCarTransfers();
        List<CarAllTransfersResponse> carAllTransfersResponses = carTransfers
                .stream()
                .map(transfersByCarResponseConverter::toTransferByCarResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carAllTransfersResponses);
    }

    @PostMapping(value = "/{id}/summarize")
    public ResponseEntity<TransferSummarizeResponse> summarizeTransfer(@RequestBody @Valid TransferSummarizeRequest transferSummarizeRequest,
                                                                       @PathVariable Long id) {

        carTransferValidator.validateSummarizeData(transferSummarizeRequest, id);
        carTransferValidator.validateTransferDate(transferSummarizeRequest, id);
        CarTransfer newCarTransfer = carTransferBuilder.buildSummarize(transferSummarizeRequest, id);
        return ResponseEntity.ok(transferSummarizeConverter.toTransferSummarizeResponse(newCarTransfer));
    }

    @PostMapping(value = "/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarCategoryResponse> saveCarCategories(@RequestBody @Valid CarCategoryRequest carCategoryRequest) {
        CarCategory carCategory = carCategortyBuilder.toCarCategory(carCategoryRequest);
        CarCategory savedCarCategory = carCategoryService.save(carCategory);

        return ResponseEntity.status(HttpStatus.CREATED).body(carCategortyBuilder.toCarCategoryResponse(savedCarCategory));
    }

    @GetMapping(value = "/categories")
    public ResponseEntity<List<CarCategoryResponse>> findAllCategories() {
        List<CarCategory> findAllCarCategories = carCategoryService.findAll();
        return ResponseEntity.ok(findAllCarCategories
                .stream()
                .map(carCategortyBuilder::toCarCategoryResponse)
                .collect(Collectors.toList()));
    }

    @PutMapping(value = "/categories/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarCategoryResponse> updateCarCategories(@RequestBody @Valid CarCategoryRequest carCategoryRequest,
                                                                   @PathVariable Long id) {
        CarCategory carCategory = carCategortyBuilder.toCarCategory(carCategoryRequest);
        CarCategory updatedCarCategory = carCategoryService.update(carCategory, id);

        return ResponseEntity.status(HttpStatus.OK).body(carCategortyBuilder.toCarCategoryResponse(updatedCarCategory));
    }

    @DeleteMapping(value = "/categories/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteCarCategory(@PathVariable Long id) {

        carCategoryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
