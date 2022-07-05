package com.academy.javabootcamp.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AllAvailableRoomsRequest {

    @NotEmpty
    private String startDate;

    @NotEmpty
    private String endDate;

    @Min(value = 1,message = "Adults number should not be less than 0")
    private Integer adults;

    @Min(value = 0,message = "Kids number should not be less than 0")
    private Integer kids;

}
