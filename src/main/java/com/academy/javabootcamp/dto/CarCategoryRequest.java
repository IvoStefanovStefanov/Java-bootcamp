package com.academy.javabootcamp.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CarCategoryRequest {

    @Size(min = 2, max = 255, message = "Facilities should be between 2 and 255 characters")
    @NotEmpty
    private String title;

    @Min(value = 1, message = "Seats should not be less than 1")
    private int seats;

    @Min(value = 1, message = "Price should not be less than 1")
    private Double price;

    @Min(value = 1, message = "Number should not be less than 1")
    private int number;
}
