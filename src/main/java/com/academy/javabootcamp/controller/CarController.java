package com.academy.javabootcamp.controller;


import com.academy.javabootcamp.converter.CarSaveConverter;
import com.academy.javabootcamp.converter.CarTransferBuilder;
import com.academy.javabootcamp.converter.TransferSummarizeConverter;
import com.academy.javabootcamp.converter.TransfersByCarResponseConverter;
import com.academy.javabootcamp.dto.*;
import com.academy.javabootcamp.model.Car;
import com.academy.javabootcamp.model.CarTransfer;
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
}
