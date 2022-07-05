package com.academy.javabootcamp.dto;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CarSaveRequest {

    private Long category;

    @Size(min = 2, max = 255, message = "Brand should be between 2 and 255 characters")
    @NotEmpty
    private String brand;

    @Size(min = 2, max = 255, message = "Model should be between 2 and 255 characters")
    @NotEmpty
    private String model;

    @URL(message = "Please insert valid URL")
    @NotBlank(message = "Image may not be blank")
    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))$)", message = "The image should be: .jpg, .png, .gif, .bmp")
    private String image;

    private List<
            @URL(message = "Please insert valid URL")
            @Pattern(regexp = "([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))$)", message = "The image should be: .jpg, .png, .gif, .bmp")
                    String> images;

    @Min(value = 0, message = "Year should not be less than 0")
    private Integer year;

}
