package ru.practicum.shareit.item;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.ItemRequest.ItemRequest;
import ru.practicum.shareit.user.User;

public class ItemTest {

    @Test
    public void testConstructorWithId() {
        Long id = 1L;
        Item item = new Item(id);
        Assertions.assertEquals(id, item.getId());
    }

    @Test
    public void testEqualsAndHashCode() {
        User owner = new User("John", "john@example.com");
        ItemRequest request = new ItemRequest("Test description");

        Item item1 = new Item("Test Item", "Item Description", true, request);
        item1.setId(1L);
        item1.setOwner(owner);

        Item item2 = new Item("Test Item", "Item Description", true, request);
        item2.setId(1L);
        item2.setOwner(owner);

        Assertions.assertEquals(item1, item2);
        Assertions.assertEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    public void testToString() {
        Long id = 1L;
        String name = "Test Item";
        String description = "Item Description";
        Boolean available = true;

        Item item = new Item(id);
        item.setName(name);
        item.setDescription(description);
        item.setAvailable(available);

        String expectedToString = "Item{id=1, name='Test Item', description='Item Description', available=true}";
        Assertions.assertEquals(expectedToString, item.toString());
    }
}
