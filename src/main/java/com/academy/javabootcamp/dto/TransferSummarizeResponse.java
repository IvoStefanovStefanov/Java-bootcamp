package com.academy.javabootcamp.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransferSummarizeResponse {

    private Long id;
    private String category;
    private String brand;
    private String model;
    private String image;
    private List<String> images;
    private Integer seats;
    private Double price;
    private String date;
}
