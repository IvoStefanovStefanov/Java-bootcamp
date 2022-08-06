package api.user.javabootcamp.service.Impl;

import com.academy.javabootcamp.exception.ResourceNotFound;
import com.academy.javabootcamp.model.CarCategory;
import com.academy.javabootcamp.repository.CarCategoryRepository;
import com.academy.javabootcamp.service.impl.CarCategoryServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarCategoryServiceImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private CarCategoryRepository carCategoryRepository;

    @InjectMocks
    private CarCategoryServiceImpl carCategoryServiceImpl;

    @Test
    public void verifyFindByIdResourceNotFound() {
        long id = 1;
        expectedException.expect(ResourceNotFound.class);
        expectedException.expectMessage(String.format("CarCategory with ID: %s not found in our database", id));
        carCategoryServiceImpl.findById(id);
    }

    @Test
    public void verifyFindById() {
        long id = 1;
        when(carCategoryRepository.findById(id)).thenReturn(Optional.of(CarCategory.builder().build()));
        carCategoryServiceImpl.findById(id);
        verify(carCategoryRepository, times(1)).findById(id);
    }
}
