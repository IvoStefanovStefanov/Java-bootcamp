package api.user.javabootcamp.converter.carConverter;

import com.academy.javabootcamp.converter.CarSaveConverter;
import com.academy.javabootcamp.dto.CarSaveRequest;
import com.academy.javabootcamp.dto.CarSaveResponse;
import com.academy.javabootcamp.model.Car;
import com.academy.javabootcamp.model.CarCategory;
import com.academy.javabootcamp.model.CarImage;
import com.academy.javabootcamp.repository.CarCategoryRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CarSaveConverterTests {

    @Mock
    private CarCategoryRepository carCategoryRepository;

    @InjectMocks
    private CarSaveConverter carSaveConverter;

    @Test
    public void assertThatCarSaveConverterIsConvertingToCarCorrectly() {

        List<String> images = new ArrayList<>();

        CarSaveRequest carSaveRequest = CarSaveRequest.builder()
                .category(1L)
                .brand("BWV")
                .model("X5")
                .image("https://images.app.goo.gl/newImage.jpg")
                .images(images)
                .year(1999)
                .build();

        Car car = carSaveConverter.toCar(carSaveRequest);

        Assertions.assertEquals(car.getBrand(), carSaveRequest.getBrand());
        Assertions.assertEquals(car.getModel(), carSaveRequest.getModel());
        Assertions.assertEquals(car.getImage(), carSaveRequest.getImage());
        Assertions.assertNotNull(car.getImages());
        Assertions.assertEquals(car.getYear(), carSaveRequest.getYear());
    }

    @Test
    public void assertThatCarSaveConverterIsConvertingToCarSaveResponseCorrectly() {

        List<CarImage> images = new ArrayList<>();
        CarCategory carCategory = new CarCategory();

        Car car = Car.builder()
                .carCategory(carCategory)
                .brand("BWV")
                .model("X5")
                .image("https://images.app.goo.gl/newImage.jpg")
                .images(images)
                .build();

        CarSaveResponse carSaveResponse = carSaveConverter.toCarResponse(car);

        Assertions.assertEquals(car.getCarCategory().getName(), carSaveResponse.getCategory());
        Assertions.assertEquals(car.getBrand(), carSaveResponse.getBrand());
        Assertions.assertEquals(car.getModel(), carSaveResponse.getModel());
        Assertions.assertEquals(car.getImage(), carSaveResponse.getImage());
        Assertions.assertEquals(car.getCarCategory().getSeats(), carSaveResponse.getSeats());
        Assertions.assertEquals(car.getCarCategory().getPrice(), carSaveResponse.getPrice());
        Assertions.assertNotNull(carSaveResponse.getImages());
    }
}
