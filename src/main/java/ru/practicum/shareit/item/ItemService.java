package ru.practicum.shareit.item;

import ru.practicum.shareit.comment.CommentDto;

import java.util.List;

public interface ItemService {

    List<ItemDto> getItems(Long userId, int from, int size);

    ItemDto addNewItem(Long userId, ItemDto itemDto);

    void deleteItem(Long userId, Long itemId);

    ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto);

    ItemDto getItem(Long userId, Long itemId);

    List<ItemDto> searchForItems(String text, int from, int size);

    CommentDto addNewComment(Long userId, Long itemId, CommentDto commentDto);
}
