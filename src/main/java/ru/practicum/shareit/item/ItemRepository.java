package ru.practicum.shareit.item;

import java.util.List;

interface ItemRepository {

    List<Item> findByUserId(int userId);

    Item save(Item item);

    void deleteByUserIdAndItemId(int userId, int itemId);

    Item updateItem(int userId, Item item);

    Item getItem(int itemId);

    List<Item> searchForItems(String text);
}