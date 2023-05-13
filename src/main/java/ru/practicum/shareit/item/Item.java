package ru.practicum.shareit.item;

import lombok.Data;
import lombok.AllArgsConstructor;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

/**
 * TODO Sprint add-controllers.
 */

@Data
@AllArgsConstructor
public class Item {

    private int id;

    private User owner;

    private String name;

    private String description;

    private boolean available;

    private ItemRequest itemRequest;

    public Item() {

    }

    public Item(String name, String description, boolean available) {
        this.name = name;
        this.description = description;
        this.available = available;
    }
}
