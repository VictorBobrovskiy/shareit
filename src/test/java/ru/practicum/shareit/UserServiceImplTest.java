package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.shareit.user.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "John", "john@example.com"));
        userList.add(new User(2L, "Jane", "jane@example.com"));
        when(userRepository.findAll()).thenReturn(userList);

        List<UserDto> result = userService.findAll();

        assertNotNull(result);
        assertEquals(userList.size(), result.size());
        assertEquals(userList.get(0).getName(), result.get(0).getName());
        assertEquals(userList.get(0).getEmail(), result.get(0).getEmail());
        assertEquals(userList.get(1).getName(), result.get(1).getName());
        assertEquals(userList.get(1).getEmail(), result.get(1).getEmail());
        verify(userRepository).findAll();
    }

    @Test
    void getUserByExistingId() {
        Long userId = 1L;
        User user = new User(userId, "John", "john@example.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDto result = userService.getUser(userId);

        assertNotNull(result);
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        verify(userRepository).findById(userId);
    }

    @Test
    void getUserByNonexistentId() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUser(userId));
        verify(userRepository).findById(userId);
    }

    @Test
    void addUserValid() {
        UserDto userDto = new UserDto(null, "John", "john@example.com");
        User user = new User(1L, "John", "john@example.com");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto result = userService.addUser(userDto);

        assertNotNull(result);
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateExistingUser() {
        Long userId = 1L;
        UserDto userDto = new UserDto(userId, "John Doe", "johndoe@example.com");
        User user = new User(userId, "John", "john@example.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto result = userService.update(userDto);

        assertNotNull(result);
        assertEquals(userDto.getName(), result.getName());
        assertEquals(userDto.getEmail(), result.getEmail());
        verify(userRepository).findById(userId);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateNonexistentUser() {
        Long userId = 1L;
        UserDto userDto = new UserDto(userId, "John Doe", "johndoe@example.com");
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.update(userDto));
        verify(userRepository).findById(userId);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void deleteUser() {
        Long userId = 1L;

        userService.delete(userId);

        verify(userRepository).deleteById(userId);
    }
}
