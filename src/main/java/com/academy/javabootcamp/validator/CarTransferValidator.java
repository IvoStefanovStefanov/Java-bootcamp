package com.academy.javabootcamp.validator;

import com.academy.javabootcamp.dto.TransferSummarizeRequest;
import com.academy.javabootcamp.exception.DuplicateRecordException;
import com.academy.javabootcamp.exception.ResourceNotFound;
import com.academy.javabootcamp.model.Car;
import com.academy.javabootcamp.model.CarTransfer;
import com.academy.javabootcamp.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class CarTransferValidator {

    private final CarService carService;

    public void validateSummarizeData(TransferSummarizeRequest transferSummarizeRequest, Long id) {
        Car foundCar = carService.findById(id);
        Integer seats = transferSummarizeRequest.getSeats();
        String model = transferSummarizeRequest.getModel();
        if (!Objects.equals(foundCar.getModel(), model) || !Objects.equals(foundCar.getCarCategory().getSeats(), seats)) {
            throw new ResourceNotFound(String.format("The car with id %d doesn't have that many seats or is not this model", id));
        }
    }

    public void validateTransferDate(TransferSummarizeRequest transferSummarizeRequest, Long id) {
        Car foundCar = carService.findById(id);
        List<Car> allCars = carService.findAll();
        Long carCategory = foundCar.getCarCategory().getId();
        String transferDate = transferSummarizeRequest.getDate();
        int carNumbers = 0;
        for (Car car : allCars) {
            if (carCategory == car.getCarCategory().getId()) {
                for (CarTransfer carTransfer : car.getCarTransfers()) {
                    if (carTransfer.getTransferDate().equals(transferDate)) {
                        carNumbers += 1;
                        if (carNumbers == foundCar.getCarCategory().getCarNumber()) {
                            throw new DuplicateRecordException(String.format("This date %s is already taken", transferDate));
                        }
                    }
                }
            }
        }
    }
}
