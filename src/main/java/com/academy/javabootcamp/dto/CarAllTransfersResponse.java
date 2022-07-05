package com.academy.javabootcamp.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CarAllTransfersResponse {

    private Long id;
    private String category;
    private String brand;
    private String model;
    private String image;
    private Double price;
    private String date;
    private Long userId;
    private String user;
    private String created;
}
