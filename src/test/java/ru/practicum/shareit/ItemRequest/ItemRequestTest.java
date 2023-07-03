package ru.practicum.shareit.ItemRequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

public class ItemRequestTest {

    @Test
    public void testConstructorWithId() {
        Long id = 1L;
        ItemRequest itemRequest = new ItemRequest(id);
        Assertions.assertEquals(id, itemRequest.getId());
    }

    @Test
    public void testConstructorWithDescription() {
        String description = "Test description";
        ItemRequest itemRequest = new ItemRequest(description);
        Assertions.assertEquals(description, itemRequest.getDescription());
    }

    @Test
    public void testEqualsAndHashCode() {
        User user = new User("John", "john@example.com");
        LocalDateTime created = LocalDateTime.now();

        ItemRequest itemRequest1 = new ItemRequest("Test description");
        itemRequest1.setId(1L);
        itemRequest1.setRequester(user);
        itemRequest1.setCreated(created);

        ItemRequest itemRequest2 = new ItemRequest("Test description");
        itemRequest2.setId(1L);
        itemRequest2.setRequester(user);
        itemRequest2.setCreated(created);

        Assertions.assertEquals(itemRequest1, itemRequest2);
        Assertions.assertEquals(itemRequest1.hashCode(), itemRequest2.hashCode());
    }

    @Test
    public void testToString() {
        Long id = 1L;
        String description = "Test description";
        LocalDateTime created = LocalDateTime.now();

        ItemRequest itemRequest = new ItemRequest(id);
        itemRequest.setDescription(description);
        itemRequest.setCreated(created);

        String expectedToString = "ItemRequest{id=1, description='Test description', created=" + created.toString() + "}";
        Assertions.assertEquals(expectedToString, itemRequest.toString());
    }
}
