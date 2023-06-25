package ru.practicum.shareit.user;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@DataJpaTest(properties = "spring.jpa.properties.javax.persistence.validation.mode=none")
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class UserRepositoryTest {

    @MockBean
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testFindById() {
        Long userId = 1L;
        User user = new User(userId, "John Doe", "john.doe@mail.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        Optional<User> foundUser = userRepository.findById(userId);
        assertEquals(user, foundUser.orElse(null));
    }

    @Test
    public void testSave() {
        User user = new User("John Doe", "john.doe@mail.com");
        when(userRepository.save(user)).thenReturn(user);
        User savedUser = userRepository.save(user);
        assertEquals(user, savedUser);
    }


    @Test
    void saveUserWhenNameNull() {
        Long userId = 1L;
        User user = new User(userId, "John Doe", "john.doe@mail.com");
        User user2 = new User("user@mail.com");
        when(userRepository.save(user2)).thenThrow(DataIntegrityViolationException.class);
        assertThatThrownBy(() -> userRepository.save(user2))
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test
    void saveUserWhenEmailNull() {

        User user2 = new User("Username");
        when(userRepository.save(user2)).thenThrow(DataIntegrityViolationException.class);
        assertThatThrownBy(() -> userRepository.save(user2))
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test
    void saveUserWhenEmailExists() {
        Long userId = 1L;
        User user = new User(userId, "John Doe", "john.doe@mail.com");
        User user2 = new User("Username", "john.doe@mail.com");
        userRepository.save(user);
        when(userRepository.save(user2)).thenThrow(DataIntegrityViolationException.class);
        assertThatThrownBy(() -> userRepository.save(user2))
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test
    void findByIdValidId() {
        Long userId = 1L;
        User user = new User(userId, "John Doe", "john.doe@mail.com");
        userRepository.save(user);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        assertThat(userRepository.findById(user.getId()))
                .isPresent()
                .hasValueSatisfying(u -> {
                    assertThat(u).isEqualTo(user);
                });

    }

    @Test
    void itShouldNotFindByIdWhenIdDoesNotExist() {
        Long userId = 1L;
        User user = new User(userId, "John Doe", "john.doe@mail.com");
        userRepository.save(user);
        assertThat(userRepository.findById(2L)).isNotPresent();

    }

    @Test
    void itShouldReturnTwoUsers() {
        Long userId = 1L;
        User user = new User(userId, "John Doe", "john.doe@mail.com");
        List<User> userList = new ArrayList<>();
        userList.add(user);

        User user2 = new User("Username", "user@mail.com");
        userList.add(user2);
        userRepository.save(user);
        userRepository.save(user2);

        when(userRepository.findAll()).thenReturn(userList);

        assertThat(userRepository.findAll()).hasSize(2)
                .contains(user, user2);

    }

    @Test
    void itShouldDeleteById() {
        Long userId = 1L;
        User user = new User(userId, "John Doe", "john.doe@mail.com");
        when(userRepository.save(user)).thenReturn(user);
        userRepository.save(user);
        userRepository.deleteById(user.getId());
        assertThat(userRepository.findById(user.getId())).isNotPresent();
    }

    @Test
    void itShouldDeleteAll() {
        Long userId = 1L;
        User user = new User(userId, "John Doe", "john.doe@mail.com");
        when(userRepository.save(user)).thenReturn(user);
        userRepository.save(user);
        User user2 = new User("Username", "user@mail.com");
        when(userRepository.save(user2)).thenReturn(user2);
        userRepository.save(user2);

        userRepository.deleteAll();
        assertThat(userRepository.findAll())
                .isEmpty();
    }
}