package com.academy.javabootcamp.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransferSummarizeRequest {

    @Size(min = 2, max = 255, message = "Model should be between 2 and 255 characters")
    @NotEmpty
    private String date;

    @Size(min = 2, max = 255, message = "Model should be between 2 and 255 characters")
    @NotEmpty
    private String model;

    @Min(value = 2, message = "Seats should not be less than 2")
    private Integer seats;
}
