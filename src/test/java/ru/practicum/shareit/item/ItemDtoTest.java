package ru.practicum.shareit.item;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.comment.CommentDto;

import java.util.ArrayList;
import java.util.List;

public class ItemDtoTest {

    @Test
    public void testConstructorWithId() {
        Long id = 1L;
        ItemDto itemDto = new ItemDto(id, "Test Item");
        Assertions.assertEquals(id, itemDto.getId());
    }

    @Test
    public void testEqualsAndHashCode() {
        ItemDto itemDto1 = new ItemDto("Test Item", "Item Description", true);
        itemDto1.setId(1L);

        ItemDto itemDto2 = new ItemDto("Test Item", "Item Description", true);
        itemDto2.setId(1L);

        Assertions.assertEquals(itemDto1, itemDto2);
        Assertions.assertEquals(itemDto1.hashCode(), itemDto2.hashCode());
    }

    @Test
    public void testToString() {
        Long id = 1L;
        String name = "Test Item";
        String description = "Item Description";
        Boolean available = true;

        ItemDto itemDto = new ItemDto(id, name, description, available);

        String expectedToString = "ItemDto(id=1, name=Test Item, description=Item Description, available=true, requestId=null, lastBooking=null, nextBooking=null, comments=null)";
        Assertions.assertEquals(expectedToString, itemDto.toString());
    }

    @Test
    public void testCompareTo() {
        ItemDto itemDto1 = new ItemDto(1L, "Item 1");
        ItemDto itemDto2 = new ItemDto(2L, "Item 2");

        Assertions.assertTrue(itemDto1.compareTo(itemDto2) < 0);
        Assertions.assertTrue(itemDto2.compareTo(itemDto1) > 0);
        Assertions.assertEquals(0, itemDto1.compareTo(itemDto1));
    }

    @Test
    public void testSetAndGetComments() {
        ItemDto itemDto = new ItemDto();
        List<CommentDto> comments = new ArrayList<>();
        comments.add(new CommentDto("Comment 1"));
        comments.add(new CommentDto("Comment 2"));

        itemDto.setComments(comments);
        Assertions.assertEquals(comments, itemDto.getComments());
    }

    // Add more test cases for other methods and constructors as needed

}
