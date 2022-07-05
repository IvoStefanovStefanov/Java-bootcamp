package com.academy.javabootcamp.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ReservationUpdateResponse {

    private Long id;
    private String room;
    private Long  userId;
    private String user;
    private String startDate;
    private String endDate;
    private Long days;
    private Integer adults;
    private Integer kids;
    private String typeBed;
    private String typeView;
    private Double price;
    private Date created;
    private String status;
}
