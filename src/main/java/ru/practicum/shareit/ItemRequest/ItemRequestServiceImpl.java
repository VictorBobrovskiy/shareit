package ru.practicum.shareit.ItemRequest;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
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
    private final ModelMapper modelMapper;

    @Override
    public ItemRequestDto createItemRequest(Long userId, ItemRequestDto itemRequestDto) {
        ItemRequest request = modelMapper.map(itemRequestDto, ItemRequest.class);
        request.setRequester(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found")));
        request.setCreated(LocalDateTime.now());
        return modelMapper.map(itemRequestRepository.save(request), ItemRequestDto.class);
    }

    @Override
    public List<ItemRequestDto> getOwnItemRequests(Long userId) {
        checkUserExists(userId);
        return itemRequestRepository.findAllByRequesterId(userId).stream()
                .map(this::mapItemRequestDtoWithItems)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemRequestDto> getAllItemRequests(int from, int size) {
        if (from < 0 || size < 1) {
            throw new IllegalArgumentException("Wrong page number");
        }
        int pageNum = from/size;
        return itemRequestRepository.findAllOrderByCreated(PageRequest.of((int) pageNum, size))
                .stream()
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
        ItemRequestDto itemRequestDto = modelMapper.map(itemRequest, ItemRequestDto.class);
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
