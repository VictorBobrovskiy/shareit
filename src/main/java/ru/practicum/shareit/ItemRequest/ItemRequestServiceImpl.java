package ru.practicum.shareit.ItemRequest;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
        request.setRequester(userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found")));
        request.setCreated(LocalDateTime.now());
        return modelMapper.map(itemRequestRepository.save(request), ItemRequestDto.class);
    }

    @Override
    public List<ItemRequestDto> getOwnItemRequests(Long userId) {
        checkUserExists(userId);
        List<ItemRequestDto> itemRequests = itemRequestRepository.findAllByRequesterId(userId)
                .stream().map(itemRequest -> modelMapper.map(itemRequest, ItemRequestDto.class))
                .collect(Collectors.toList());

        itemRequests.forEach(itemRequestDto -> {
            List<ItemDto> itemDtoList = itemRepository.getAllByRequestId(itemRequestDto.getId())
                    .stream()
                    .map(item -> ItemMapper.mapItemToDto(item))
                    .collect(Collectors.toList());

            itemRequestDto.setItems(itemDtoList);
        });


        return itemRequests;
    }

    @Override
    public List<ItemRequestDto> getAllItemRequests(int from, int size) {

        List<ItemRequestDto> itemRequestPage = itemRequestRepository.findAllOrderByCreated(PageRequest.of(from, size))
                .stream().map(itemRequest -> modelMapper.map(itemRequest, ItemRequestDto.class))
                .collect(Collectors.toList());

        itemRequestPage.forEach(itemRequestDto -> {
            List<ItemDto> itemDtoList = itemRepository.getAllByRequestId(itemRequestDto.getId())
                    .stream()
                    .map(item -> ItemMapper.mapItemToDto(item))
                    .collect(Collectors.toList());

            itemRequestDto.setItems(itemDtoList);
        });

        return itemRequestPage;
    }

    @Override
    public ItemRequestDto getItemRequest(Long userId, Long itemRequestId) {
        checkUserExists(userId);
        ItemRequest itemRequest = itemRequestRepository.findById(itemRequestId)
                .orElseThrow(() -> new ItemRequestNotFoundException("ItemRequest not found"));
        ItemRequestDto itemRequestDto = modelMapper.map(itemRequest, ItemRequestDto.class);
        List<ItemDto> itemDtoList = itemRepository.getAllByRequestId(itemRequestDto.getId())
                .stream()
                .map(item -> ItemMapper.mapItemToDto(item))
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
