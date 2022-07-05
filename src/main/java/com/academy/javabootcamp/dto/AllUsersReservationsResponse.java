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
public class AllUsersReservationsResponse {

        private    Long id ;
        private    String  user ;
        private    String  room ;
        private    Integer adults;
        private    Integer kids;
        private    String startDate;
        private    String endDate;
        private    Long days;
        private    String typeBed;
        private    String typeView;
        private    Double price;
        private    String date;
        private    String status;

}
