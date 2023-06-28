package ru.practicum.shareit.item;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.BookingDto;
import ru.practicum.shareit.comment.CommentDto;

import java.util.Arrays;
import java.util.List;

public class ItemDtoTest {

    @Test
    public void testItemDtoConstructorAndGetters() {
        // Prepare test data
        Long itemId = 1L;
        String name = "Item Name";
        String description = "Item Description";
        Boolean available = true;
        Long requestId = 2L;
        BookingDto lastBooking = new BookingDto();
        lastBooking.setId(3L);
        BookingDto nextBooking = new BookingDto();
        nextBooking.setId(4L);
        List<CommentDto> comments = Arrays.asList(new CommentDto(5L, "New comment"), new CommentDto(6L, "Newer comment"));

        // Create an ItemDto using the constructor
        ItemDto itemDto = new ItemDto(itemId, name, description, available, requestId);
        itemDto.setLastBooking(lastBooking);
        itemDto.setNextBooking(nextBooking);
        itemDto.setComments(comments);

        // Assertions
        Assertions.assertEquals(itemId, itemDto.getId());
        Assertions.assertEquals(name, itemDto.getName());
        Assertions.assertEquals(description, itemDto.getDescription());
        Assertions.assertEquals(available, itemDto.getAvailable());
        Assertions.assertEquals(requestId, itemDto.getRequestId());
        Assertions.assertEquals(lastBooking, itemDto.getLastBooking());
        Assertions.assertEquals(nextBooking, itemDto.getNextBooking());
        Assertions.assertEquals(comments, itemDto.getComments());
    }

    @Test
    public void testItemDtoSetterAndGetters() {
        // Prepare test data
        ItemDto itemDto = new ItemDto();

        Long itemId = 1L;
        String name = "Item Name";
        String description = "Item Description";
        Boolean available = true;
        Long requestId = 2L;
        BookingDto lastBooking = new BookingDto();
        lastBooking.setId(3L);
        BookingDto nextBooking = new BookingDto();
        nextBooking.setId(4L);
        List<CommentDto> comments = Arrays.asList(new CommentDto(5L, "New comment"), new CommentDto(6L, "Newer comment"));

        // Set values using setters
        itemDto.setId(itemId);
        itemDto.setName(name);
        itemDto.setDescription(description);
        itemDto.setAvailable(available);
        itemDto.setRequestId(requestId);
        itemDto.setLastBooking(lastBooking);
        itemDto.setNextBooking(nextBooking);
        itemDto.setComments(comments);

        // Assertions
        Assertions.assertEquals(itemId, itemDto.getId());
        Assertions.assertEquals(name, itemDto.getName());
        Assertions.assertEquals(description, itemDto.getDescription());
        Assertions.assertEquals(available, itemDto.getAvailable());
        Assertions.assertEquals(requestId, itemDto.getRequestId());
        Assertions.assertEquals(lastBooking, itemDto.getLastBooking());
        Assertions.assertEquals(nextBooking, itemDto.getNextBooking());
        Assertions.assertEquals(comments, itemDto.getComments());
    }
}
