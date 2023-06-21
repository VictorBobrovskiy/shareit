package ru.practicum.shareit.ItemRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.ItemDto;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.UserNotFoundException;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {

    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public ItemRequestDto createItemRequest(Long userId, ItemRequestDto itemRequestDto) {
        ItemRequest request = ItemRequestMapper.toEntity(itemRequestDto);
        request.setRequester(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found")));
        request.setCreated(LocalDateTime.now());
        return ItemRequestMapper.toDto(itemRequestRepository.save(request));
    }

    @Override
    public List<ItemRequestDto> getOwnItemRequests(Long userId) {
        checkUserExists(userId);
        return itemRequestRepository.findAllByRequesterId(userId).stream()
                .map(this::mapItemRequestDtoWithItems)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemRequestDto> getAllItemRequests(Long userId, int from, int size) {
        if (from < 0 || size < 1) {
            throw new IllegalArgumentException("Wrong page number");
        }
        int pageNum = from / size;
        return itemRequestRepository.findAllOrderByCreated(PageRequest.of(pageNum, size))
                .stream()
                .filter(itemRequest -> !itemRequest.getRequester().getId().equals(userId))
                .map(this::mapItemRequestDtoWithItems)
                .collect(Collectors.toList());
    }

    @Override
    public ItemRequestDto getItemRequest(Long userId, Long itemRequestId) {
        checkUserExists(userId);
        ItemRequest itemRequest = itemRequestRepository.findById(itemRequestId)
                .orElseThrow(() -> new ItemRequestNotFoundException("ItemRequest not found"));
        return mapItemRequestDtoWithItems(itemRequest);
    }

    private ItemRequestDto mapItemRequestDtoWithItems(ItemRequest itemRequest) {
        ItemRequestDto itemRequestDto = ItemRequestMapper.toDto(itemRequest);
        List<ItemDto> itemDtoList = itemRepository.getAllByRequestId(itemRequest.getId())
                .stream()
                .map(ItemMapper::mapItemToDto)
                .collect(Collectors.toList());
        itemRequestDto.setItems(itemDtoList);
        return itemRequestDto;
    }

    private void checkUserExists(Long ownerId) {
        if (!userRepository.existsById(ownerId)) {
            throw new UserNotFoundException("User Not Found");
        }
    }
}
