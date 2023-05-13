package ru.practicum.shareit.item;

import java.util.List;

public interface ItemService {

    List<ItemDto> getItems(int userId);

    ItemDto addNewItem(int userId, ItemDto itemDto);

    void deleteItem(int userId, int itemId);

    ItemDto updateItem(int userId, ItemDto itemDto);

    ItemDto getItem(int itemId);
}
