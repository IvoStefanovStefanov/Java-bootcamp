package api.user.javabootcamp.service;

import com.academy.javabootcamp.exception.ResourceNotFound;
import com.academy.javabootcamp.repository.UserRepository;
import com.academy.javabootcamp.service.impl.UserServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    public void verifyFindById(){
        long id = 1;
        expectedException.expect(ResourceNotFound.class);
        expectedException.expectMessage(String.format("User with %d id does not exists.", id));
        userServiceImpl.findById(id);
    }

    @Test
    public void verifyFindByEmail(){
        String email = "test@abv.bg";
        expectedException.expect(ResourceNotFound.class);
        expectedException.expectMessage(String.format("User with email %s is not found", email));
        userServiceImpl.findByEmail(email);
    }
}
