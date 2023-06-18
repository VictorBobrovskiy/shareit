package ru.practicum.shareit.ItemRequest;

import java.util.List;

public interface ItemRequestService {
    ItemRequestDto createItemRequest(Long userId, ItemRequestDto itemRequestDto);

    List<ItemRequestDto> getOwnItemRequests(Long userId);

    List<ItemRequestDto> getAllItemRequests(int from, int size);

    ItemRequestDto getItemRequest(Long userId, Long itemRequestId);
}
