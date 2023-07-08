package ru.practicum.shareit.ItemRequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.ItemDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ItemRequestDtoTest {

    @Test
    public void testItemRequestDtoConstructorAndGetters() {
        // Prepare test data
        Long requestId = 1L;
        String description = "Request Description";
        LocalDateTime created = LocalDateTime.now();
        List<ItemDto> items = new ArrayList<>();

        // Create an ItemRequestDto using the constructor
        ItemRequestDto itemRequestDto = new ItemRequestDto(requestId, description, created, items);

        // Assertions
        Assertions.assertEquals(requestId, itemRequestDto.getId());
        Assertions.assertEquals(description, itemRequestDto.getDescription());
        Assertions.assertEquals(created, itemRequestDto.getCreated());
        Assertions.assertEquals(items, itemRequestDto.getItems());
    }

    @Test
    public void testItemRequestDtoSetterAndGetters() {
        // Prepare test data
        ItemRequestDto itemRequestDto = new ItemRequestDto();

        Long requestId = 1L;
        String description = "Request Description";
        LocalDateTime created = LocalDateTime.now();
        List<ItemDto> items = new ArrayList<>();

        // Set values using setters
        itemRequestDto.setId(requestId);
        itemRequestDto.setDescription(description);
        itemRequestDto.setCreated(created);
        itemRequestDto.setItems(items);

        // Assertions
        Assertions.assertEquals(requestId, itemRequestDto.getId());
        Assertions.assertEquals(description, itemRequestDto.getDescription());
        Assertions.assertEquals(created, itemRequestDto.getCreated());
        Assertions.assertEquals(items, itemRequestDto.getItems());
    }
}
