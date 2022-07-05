package com.academy.javabootcamp.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ReservationSaveResponse {
        private Long id;
        private String room;
        private String startDate;
        private String endDate;
        private Long days;
        private Integer adults;
        private Integer kids;
        private Double price;
}
