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
public class ItemDto {

    private String name;

    private String description;

    private boolean available;

    private int requestId;
}
