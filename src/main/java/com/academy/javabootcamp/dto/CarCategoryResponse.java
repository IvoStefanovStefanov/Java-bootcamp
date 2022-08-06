package com.academy.javabootcamp.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CarCategoryResponse {

    private Long id;

    private String title;

    private int seats;

    private double price;
}
