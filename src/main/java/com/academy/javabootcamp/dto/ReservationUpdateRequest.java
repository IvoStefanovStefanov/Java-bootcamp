package com.academy.javabootcamp.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ReservationUpdateRequest {

      @Min(value = 0, message = "User id should not be less than 0")
      private Long user;

      @NotEmpty
      private String startDate;

      @NotEmpty
      private String endDate;

      @Min(value = 1, message = "Adults number should not be less than 0")
      private Integer adults;

      @Min(value = 0, message = "Kids number should not be less than 0")
      private Integer kids;

      @Size(min = 2, max = 255, message = "Type of bed should be between 2 and 255 characters")
      @NotEmpty
      private String typeBed;

      @Size(min = 2, max = 255, message = "Type of view should be between 2 and 255 characters")
      @NotEmpty
      private String typeView;

      private String status;
}
