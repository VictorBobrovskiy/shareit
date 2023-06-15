package ru.practicum.shareit.ItemRequest;

import org.springframework.data.domain.Page;

import java.util.List;

public interface ItemRequestService {
    ItemRequest createItemRequest(Long userId, ItemRequestDto itemRequestDto);
    List<ItemRequest> getOwnItemRequests(Long userId);
    List<ItemRequestDto>  getAllItemRequests(int from, int size);
    ItemRequest getItemRequest(long itemRequestId);
}
