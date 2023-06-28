package ru.practicum.shareit.item;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.ItemRequest.ItemRequest;
import ru.practicum.shareit.user.User;

public class ItemTest {

    @Test
    public void testItemConstructor() {
        // Prepare test data
        Long itemId = 1L;
        User owner = new User(2L);
        String name = "Item Name";
        String description = "Item Description";
        Boolean available = true;
        ItemRequest request = new ItemRequest(3L);

        // Create an Item using the constructor
        Item item = new Item(itemId, owner, name, description, available, request);

        // Assertions
        Assertions.assertEquals(itemId, item.getId());
        Assertions.assertEquals(owner, item.getOwner());
        Assertions.assertEquals(name, item.getName());
        Assertions.assertEquals(description, item.getDescription());
        Assertions.assertEquals(available, item.getAvailable());
        Assertions.assertEquals(request, item.getRequest());
    }

    @Test
    public void testItemSetterGetter() {
        // Prepare test data
        Item item = new Item();

        Long itemId = 1L;
        User owner = new User(2L);
        String name = "Item Name";
        String description = "Item Description";
        Boolean available = true;
        ItemRequest request = new ItemRequest(3L);

        // Set values using setters
        item.setId(itemId);
        item.setOwner(owner);
        item.setName(name);
        item.setDescription(description);
        item.setAvailable(available);
        item.setRequest(request);

        // Assertions
        Assertions.assertEquals(itemId, item.getId());
        Assertions.assertEquals(owner, item.getOwner());
        Assertions.assertEquals(name, item.getName());
        Assertions.assertEquals(description, item.getDescription());
        Assertions.assertEquals(available, item.getAvailable());
        Assertions.assertEquals(request, item.getRequest());
    }
}
