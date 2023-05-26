package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Slf4j
public class ItemRepositoryImpl implements ItemRepository {

    Map<Integer, Set<Item>> items = new HashMap<>();

    @Override
    public List<Item> findByUserId(int userId) {
        log.debug("Update item requested");
        return new ArrayList<>(items.get(userId));
    }

    @Override
    public Item save(Item item) {
        Set<Item> itemSet = items.get(item.getOwner().getId());
        if (itemSet != null) {
            itemSet.add(item);
            items.put(item.getId(), itemSet);
        } else {
            items.put(item.getOwner().getId(), new HashSet<>(Collections.singletonList(item)));
        }
        log.debug("Item saved");
        return item;
    }

    @Override
    public void deleteByUserIdAndItemId(int userId, int itemId) {
        Set<Item> itemSet = items.get(userId);
        itemSet.removeIf(item -> item.getId() == itemId);
        log.debug("Item deleted");
    }

    @Override
    public Item updateItem(int userId, Item item) {
        Set<Item> itemSet = items.get(userId);
        itemSet.removeIf(item1 -> item.getId() == item1.getId());
        itemSet.add(item);
        items.put(userId, itemSet);
        log.debug("Updated item sent");
        return item;
    }

    @Override
    public Item getItem(int id) {
        return items.values().stream()
                .flatMap(Set::stream)
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException("No item found with id " + id));
    }

    @Override
    public List<Item> searchForItems(String text) {
        List<Item> itemList = new ArrayList<>();
        for (Set<Item> itemSet : items.values()) {
            for (Item item : itemSet) {
                if ((item.getName().toLowerCase().contains(text.toLowerCase()) ||
                        item.getDescription().toLowerCase().contains(text.toLowerCase())) && item.getAvailable() == true) {
                    itemList.add(item);
                }
            }
        }
        return itemList;
    }
}
