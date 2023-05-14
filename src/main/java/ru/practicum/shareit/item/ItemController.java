package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> getAll(@RequestHeader("X-Sharer-User-Id") int userId) {
        log.debug("User items requested");
        return itemService.getItems(userId);
    }

    @GetMapping({"/{itemId}"})
    public ItemDto getItem(@PathVariable("itemId") int itemId) {
        log.debug("Item with ID requested");
        return itemService.getItem(itemId);
    }

    @PostMapping
    public ItemDto addNewItem(@RequestHeader("X-Sharer-User-Id") int userId, @RequestBody ItemDto itemDto) {
        log.debug("Create item requested");
        return itemService.addNewItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") int userId, @PathVariable("itemId") int itemId, @RequestBody ItemDto itemDto) {
        log.debug("Update item requested");
        return itemService.updateItem(userId, itemId, itemDto);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@RequestHeader("X-Sharer-User-Id") int userId, @PathVariable int itemId) {
        log.debug("Delete item requested");
        itemService.deleteItem(userId, itemId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchForItems(@RequestParam String text) {
        log.debug("Search for item requested");
        return itemService.searchForItems(text);
    }
}