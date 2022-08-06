package api.user.javabootcamp.service.Impl;

import com.academy.javabootcamp.exception.ResourceNotFound;
import com.academy.javabootcamp.model.Car;
import com.academy.javabootcamp.repository.CarRepository;
import com.academy.javabootcamp.service.impl.CarServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carServiceImpl;

    @Test
    public void verifySave() {
        Car carSave = Car.builder().build();
        when(carRepository.save(any(Car.class)))
                .thenReturn(carSave);
        carServiceImpl.save(carSave);
        verify(carRepository, times(1)).save(carSave);
    }

    @Test
    public void verifyDeleteCarById() {
        Long id = 100L;
        when(carRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(Car.builder().id(id).build()));
        carServiceImpl.delete(id);
        verify(carRepository, times(1)).deleteById(id);

    }

    @Test
    public void verifyDeleteThrownResourceNotFound() {
        Long id = 8L;
        expectedException.expect(ResourceNotFound.class);
        expectedException.expectMessage(String.format("Car with ID: %s not found in our database", id));
        when(carRepository.findById(id))
                .thenReturn(Optional.empty());
        carServiceImpl.delete(id);
    }

    @Test
    public void verifyFindAll() {
        when(carRepository.findAll()).thenReturn(Collections.emptyList());
        carServiceImpl.findAll();
        verify(carRepository, times(1)).findAll();
    }

    @Test
    public void verifyFindByIdResourceNotFound() {
        long id = 1;
        expectedException.expect(ResourceNotFound.class);
        expectedException.expectMessage(String.format("Car with ID: %s not found in our database", id));
        carServiceImpl.findById(id);
    }

    @Test
    public void verifyFindById() {
        long id = 1;
        when(carRepository.findById(id)).thenReturn(Optional.of(Car.builder().build()));
        carServiceImpl.findById(id);
        verify(carRepository, times(1)).findById(id);
    }
}
