package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDate;

/**
 * TODO Sprint add-item-requests.
 */

@Data
@AllArgsConstructor
public class ItemRequest {

    private int id;

    private LocalDate from;

    private LocalDate to;

    private Item item;

    private User user;

    public ItemRequest(int id) {
        this.id = id;
    }
}
