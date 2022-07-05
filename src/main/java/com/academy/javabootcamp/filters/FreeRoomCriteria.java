package com.academy.javabootcamp.filters;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
@Setter
public class FreeRoomCriteria {

    @NotBlank
    private String startDate;

    @NotBlank
    private String endDate;

    @NotBlank
    private Integer adults;

    @NotBlank
    private Integer kids;

}
