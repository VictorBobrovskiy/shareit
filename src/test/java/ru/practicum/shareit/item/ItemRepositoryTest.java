package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
}
