package com.academy.javabootcamp.dto;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoomUpdateRequest {

    private Long id;

    @Size(min = 2, max = 255, message = "Title should be between 2 and 255 characters")
    @NotEmpty
    private String title;

    @URL(message = "Please insert valid URL")
    @Pattern(regexp = "[^\\s]+(.*?)\\.(jpg|jpeg|png|gif|JPG|JPEG|PNG|GIF)$",
            message = "The image must be one of the follow types jpg, jpeg, png, gif, JPG, JPEG, PNG, GIF")
    private String image;

    private List<
            @URL(message = "Please insert valid URL")
            @Pattern(regexp = "[^\\s]+(.*?)\\.(jpg|jpegó|png|gif|JPG|JPEG|PNG|GIF)$",
                    message = "The image must be one of the follow types: jpg, jpeg, png, gif, JPG, JPEG, PNG, GIF") String> images;

    @Size(min = 2, max = 255, message = "Description should be between 2 and 255 characters")
    @NotEmpty
    private String description;

    @Size(min = 2, max = 255, message = "Facilities should be between 2 and 255 characters")
    @NotEmpty
    private String facilities;

    @Min(value = 1, message = "Area should be more than 0")
    private Double area;

    @Min(value = 1, message = "People should be more than 0")
    private Integer people;

    @Min(value = 1, message = "Price should be more than 0")
    private Double price;

    @Min(value = 1, message = "Count should be more than 0")
    private Integer count;
}
