package com.academy.javabootcamp.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoomSaveResponse {
    private Long id;
    private String title;
    private String image;
    private List<String> images;
    private String description;
    private String facilities;
    private Double area;
    private Integer people;
    private Double price;
}
