package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> getAll(@RequestHeader("X-Later-User-Id") int userId) {
        return itemService.getItems(userId);
    }

    @GetMapping({"/itemId"})
    public ItemDto getItem(@PathVariable("itemId") int itemId) {
        return itemService.getItem(itemId);
    }

    @PostMapping
    public ItemDto add(@RequestHeader("X-Later-User-Id") int userId,
                    @RequestBody ItemDto itemDto) {
        return itemService.addNewItem(userId, itemDto);
    }

    @PatchMapping
    public ItemDto update(@RequestHeader("X-Later-User-Id") int userId,
                    @RequestBody ItemDto itemDto) {
        return itemService.updateItem(userId, itemDto);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@RequestHeader("X-Later-User-Id") int userId,
                           @PathVariable int itemId) {
        itemService.deleteItem(userId, itemId);
    }
}