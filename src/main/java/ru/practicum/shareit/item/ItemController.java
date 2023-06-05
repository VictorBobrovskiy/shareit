package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.comment.CommentDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;

    private final String user = "X-Sharer-User-Id";

    @GetMapping
    public List<ItemDto> getAll(@RequestHeader(user) Long userId) {
        log.debug("User items requested");
        return itemService.getItems(userId);
    }

    @GetMapping({"/{itemId}"})
    public ItemDto getItem(@RequestHeader(user) Long userId, @PathVariable("itemId") Long itemId) {
        log.debug("Item with ID requested");
        return itemService.getItem(userId, itemId);
    }

    @PostMapping
    public ItemDto addNewItem(@RequestHeader(user) Long userId, @RequestBody @Valid ItemDto itemDto) {
        log.debug("Create item requested");
        return itemService.addNewItem(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader(user) Long userId,
                          @PathVariable("itemId") Long itemId,
                          @RequestBody ItemDto itemDto) {
        log.debug("Update item requested");
        return itemService.updateItem(userId, itemId, itemDto);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@RequestHeader(user) Long userId, @PathVariable Long itemId) {
        log.debug("Delete item requested");
        itemService.deleteItem(userId, itemId);
    }

    @GetMapping("/search")
    public List<ItemDto> searchForItems(@RequestParam String text) {
        log.debug("Search for item requested");
        return itemService.searchForItems(text);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addNewComment(
            @RequestHeader(user) Long userId,
            @PathVariable Long itemId,
            @RequestBody @Valid CommentDto commentDto) {
        log.debug("Create item requested");
        return itemService.addNewComment(userId, itemId, commentDto);
    }

}