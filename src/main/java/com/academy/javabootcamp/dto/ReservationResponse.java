package com.academy.javabootcamp.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ReservationResponse {
  private    Long id ;
  private    String  room ;
  private    Long userId;
  private    String user;
  private    String startDate;
  private    String endDate;
  private    Long days;
  private    Integer adults;
  private    Integer kids;
  private    String typeBed;
  private    String typeView;
  private    Double price;
  private    String created;
  private    String status;
}
