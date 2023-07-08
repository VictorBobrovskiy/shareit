package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ItemRepositoryTest {

    @Mock
    private ItemRepository itemRepository;

    @Test
    public void findAllItemsByOwnerIdOrderById() {
        List<Item> items = itemRepository.findAllItemsByOwnerIdOrderById(1L);
        Mockito.verify(itemRepository).findAllItemsByOwnerIdOrderById(1L);
    }

    @Test
    public void findAllItemsByDescriptionContainingIgnoreCaseAndAvailableTrue() {
        List<Item> items = itemRepository.findAllItemsByDescriptionContainingIgnoreCaseAndAvailableTrue("New Item");
        Mockito.verify(itemRepository).findAllItemsByDescriptionContainingIgnoreCaseAndAvailableTrue("New Item");
    }

    @Test
    public void getAllByRequestId() {
        List<Item> items = itemRepository.getAllByRequestId(1L);
        Mockito.verify(itemRepository).getAllByRequestId(1L);
    }

    @Test
    public void testFindById() {
        Long userId = 1L;
        Item user = new Item(userId, "John Doe", "john.doe@mail.com", true, null);
        when(itemRepository.findById(userId)).thenReturn(Optional.of(user));
        Optional<Item> foundUser = itemRepository.findById(userId);
        assertEquals(user, foundUser.orElse(null));
    }

    @Test
    public void testSave() {

        Item user = new Item("John Doe", "john.doe@mail.com", true, null);
        when(itemRepository.save(user)).thenReturn(user);
        Item savedUser = itemRepository.save(user);
        assertEquals(user, savedUser);
    }


    @Test
    void saveUserWhenNameNull() {
        Long userId = 1L;
        Item user = new Item(userId, "John Doe", "john.doe@mail.com", true, null);
        Item user2 = new Item(userId, "user@mail.com", "user item", true, null);
        when(itemRepository.save(user2)).thenThrow(DataIntegrityViolationException.class);
        assertThatThrownBy(() -> itemRepository.save(user2))
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test
    void saveUserWhenEmailNull() {

        Item user2 = new Item("ItemName", "Description", null, null);
        when(itemRepository.save(user2)).thenThrow(DataIntegrityViolationException.class);
        assertThatThrownBy(() -> itemRepository.save(user2))
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test
    void saveUserWhenEmailExists() {
        Long userId = 1L;
        Item user = new Item(userId, "John Doe", "john.doe@mail.com", true, null);
        Item user2 = new Item(userId, "user@mail.com", "user item", true, null);
        itemRepository.save(user);
        when(itemRepository.save(user2)).thenThrow(DataIntegrityViolationException.class);
        assertThatThrownBy(() -> itemRepository.save(user2))
                .isInstanceOf(DataIntegrityViolationException.class);

    }

    @Test
    void findByIdValidId() {
        Long userId = 1L;
        Item user = new Item(userId, "John Doe", "john.doe@mail.com", true, null);
        itemRepository.save(user);
        when(itemRepository.findById(user.getId())).thenReturn(Optional.of(user));
        assertThat(itemRepository.findById(user.getId()))
                .isPresent()
                .hasValueSatisfying(u -> {
                    assertThat(u).isEqualTo(user);
                });

    }

    @Test
    void itShouldNotFindByIdWhenIdDoesNotExist() {
        Long userId = 1L;
        Item user = new Item(userId, "John Doe", "john.doe@mail.com", true, null);
        itemRepository.save(user);
        assertThat(itemRepository.findById(2L)).isNotPresent();

    }

    @Test
    void itShouldReturnTwoUsers() {
        Long userId = 1L;
        Item user = new Item(userId, "John Doe", "john.doe@mail.com", true, null);
        Item user2 = new Item(userId, "user@mail.com", "user item", true, null);
        List<Item> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user2);
        itemRepository.save(user);
        itemRepository.save(user2);

        when(itemRepository.findAll()).thenReturn(userList);

        assertThat(itemRepository.findAll()).hasSize(2)
                .contains(user, user2);

    }

    @Test
    void itShouldDeleteById() {
        Long userId = 1L;
        Item user = new Item(userId, "John Doe", "john.doe@mail.com", true, null);
        when(itemRepository.save(user)).thenReturn(user);
        itemRepository.save(user);
        itemRepository.deleteById(user.getId());
        assertThat(itemRepository.findById(user.getId())).isNotPresent();
    }

    @Test
    void itShouldDeleteAll() {
        Long userId = 1L;
        Item user = new Item(userId, "John Doe", "john.doe@mail.com", true, null);
        when(itemRepository.save(user)).thenReturn(user);
        itemRepository.save(user);
        Item user2 = new Item("Username", "user@mail.com", true, null);
        when(itemRepository.save(user2)).thenReturn(user2);
        itemRepository.save(user2);

        itemRepository.deleteAll();
        assertThat(itemRepository.findAll())
                .isEmpty();
    }
}
