package com.academy.javabootcamp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class RoomDetailsDto {

    private Long id;

    private String title;

    private String image;

    private List<String> images;

    private String description;

    private String facilities;

    private Double area;

    private Integer people;

    private Integer count;

    private Double price;
}



