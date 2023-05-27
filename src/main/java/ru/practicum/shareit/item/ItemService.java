package ru.practicum.shareit.item;

import java.util.List;

public interface ItemService {

    List<ItemDto> getItems(Long userId);

    ItemDto addNewItem(Long userId, ItemDto itemDto);

    void deleteItem(Long userId, Long itemId);

    ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto);

    ItemDto getItem(Long itemId);

    List<ItemDto> searchForItems(String text);
}
