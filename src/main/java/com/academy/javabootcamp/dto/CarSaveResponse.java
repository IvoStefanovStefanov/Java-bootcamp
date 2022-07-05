package com.academy.javabootcamp.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CarSaveResponse {

    private Long id;
    private String category;
    private String brand;
    private String model;
    private String image;
    private List<String> images;
    private Integer seats;
    private Double price;
}
