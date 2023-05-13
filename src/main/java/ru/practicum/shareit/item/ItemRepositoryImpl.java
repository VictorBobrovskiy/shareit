package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepositoryImpl implements ItemRepository {

    Map<Integer, List<Item>> items = new HashMap();

    @Override
    public List<Item> findByUserId(int userId) {
        return items.get(userId);
    }

    @Override
    public Item save(Item item) {
        List<Item> itemList = items.get(item.getOwner().getId());
        itemList.add(item);
        items.put(item.getId(), itemList);
        return item;
    }

    @Override
    public void deleteByUserIdAndItemId(int userId, int itemId) {
        List<Item> itemList = items.get(userId);
        itemList.removeIf(item -> item.getId() == itemId);
    }

    @Override
    public Item updateItem(int userId, Item item) {
        List<Item> itemList = items.get(userId);
        itemList.remove(item);
        itemList.add(item);
        items.put(userId, itemList);
        return itemList.get(itemList.size() - 1);
    }

    @Override
    public Item getItem(int itemId) {
        return items.values()
                .stream()
                .flatMap(Collection::stream)
                .filter(item -> item.getId() == itemId)
                .findFirst()
                .orElse(null);
    }
}
