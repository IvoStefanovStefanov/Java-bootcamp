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
public class RoomSaveRequest {
    @Size(min = 2, max = 255, message = "Title should be between 2 and 255 characters")
    @NotEmpty
    private String title;

    @URL(message = "Please insert valid URL")
    @NotBlank(message = "Image may not be blank")
    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))$)",message = "The image should be: .jpg, .png, .gif, .bmp")
    private String  image;

    private List<
            @URL(message = "Please insert valid URL")
    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))$)", message = "The image should be: .jpg, .png, .gif, .bmp")
            String> images;

    @Size(min = 2, max = 255, message = "Description should be between 2 and 255 characters")
    @NotEmpty
    private String description;

    @Size(min = 2, max = 255, message = "Facilities should be between 2 and 255 characters")
    @NotEmpty
    private String facilities;

    @Min(value = 0, message = "Area should not be less than 0" )
    private Double area;

    @Min(value = 0, message = "People should not be less than 0")
    private Integer people;

    @Min(value = 0, message = "Price should not be less than 0")
    private Double price;

    @Min(value = 0, message = "Count count should not be less than 0")
    private Integer count;
}
