package api.user.javabootcamp.service.Impl;

import com.academy.javabootcamp.exception.DuplicateRecordException;
import com.academy.javabootcamp.exception.ResourceNotFound;
import com.academy.javabootcamp.model.Room;
import com.academy.javabootcamp.repository.RoomRepository;
import com.academy.javabootcamp.service.impl.RoomServiceImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoomServiceImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceImpl roomServiceImpl;


    @Test
    public void verifyFindAll() {
        when(roomRepository.findAll()).thenReturn(Collections.emptyList());
        roomServiceImpl.findAll();
        verify(roomRepository, times(1)).findAll();
    }

    @Test
    public void verifyFindById() {
        Long id = 3L;
        when(roomRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(Room.builder().build()));
        roomServiceImpl.findById(id);
        verify(roomRepository, times(1)).findById(id);

    }

    @Test
    public void verifyRoomFindByIdResourceNotFound() {
        Long id = 101L;
        expectedException.expect(ResourceNotFound.class);
        expectedException.expectMessage(String.format("Room with id %d does not exists", id));
        when(roomRepository.findById(id))
                .thenReturn(Optional.empty());
        roomServiceImpl.findById(id);

    }

    @Test
    public void verifyDeleteRoomById() {
        Long id = 9L;
        when(roomRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(Room.builder().build()));
        roomServiceImpl.findById(id);
        verify(roomRepository, times(1)).findById(id);

    }

    @Test
    public void verifyDeleteThrownResourceNotFound() {
        Long id = 30L;
        expectedException.expect(ResourceNotFound.class);
        expectedException.expectMessage(String.format("Room with id %d is not found", id));
        when(roomRepository.findById(id))
                .thenReturn(Optional.empty());
        roomServiceImpl.delete(id);

    }

    @Test
    public void verifyUpdateRoom() {
        Long id = 112L;
        Room foundRoom = new Room();
        when(roomRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(Room.builder().build()));
        roomServiceImpl.update(foundRoom, id);
        verify(roomRepository, times(1)).findById(id);

    }


    @Test
    public void verifySave() {
        Room roomSave = Room.builder().build();
        when(roomRepository.save(any(Room.class)))
                .thenReturn(roomSave);
        roomServiceImpl.save(roomSave);
        verify(roomRepository, times(1)).save(roomSave);
    }

    @Test
    public void verifyThrownDuplicateRecordExceptionIfRoomTitleExist() {
        Room room = new Room();
        room.setTitle("studio");
        expectedException.expect(DuplicateRecordException.class);
        expectedException.expectMessage(String.format("Room with title %s already exists", room.getTitle()));
        when(roomRepository.findByTitle(anyString()))
                .thenReturn(room);
        roomServiceImpl.save(room);
    }

}

