package ru.practicum.shareit.ItemRequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

public class ItemRequestTest {

    @Test
    public void testItemRequestConstructorAndGetters() {
        // Prepare test data
        Long requestId = 1L;
        String description = "Request Description";
        User requester = new User(2L);
        LocalDateTime created = LocalDateTime.now();

        // Create an ItemRequest using the constructor
        ItemRequest itemRequest = new ItemRequest(requestId, description, requester, created);

        // Assertions
        Assertions.assertEquals(requestId, itemRequest.getId());
        Assertions.assertEquals(description, itemRequest.getDescription());
        Assertions.assertEquals(requester, itemRequest.getRequester());
        Assertions.assertEquals(created, itemRequest.getCreated());
    }

    @Test
    public void testItemRequestSetterAndGetters() {
        // Prepare test data
        ItemRequest itemRequest = new ItemRequest();

        Long requestId = 1L;
        String description = "Request Description";
        User requester = new User(2L);
        LocalDateTime created = LocalDateTime.now();

        // Set values using setters
        itemRequest.setId(requestId);
        itemRequest.setDescription(description);
        itemRequest.setRequester(requester);
        itemRequest.setCreated(created);

        // Assertions
        Assertions.assertEquals(requestId, itemRequest.getId());
        Assertions.assertEquals(description, itemRequest.getDescription());
        Assertions.assertEquals(requester, itemRequest.getRequester());
        Assertions.assertEquals(created, itemRequest.getCreated());
    }
}
