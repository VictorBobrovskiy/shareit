package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserNotFoundException;
import ru.practicum.shareit.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final UserRepository userRepository;

    @Override
    public List<ItemDto> getItems(Long userId) {
        return null;
//        return itemRepository.findAllByUserId(userId).stream().map(ItemMapper::mapItemToDto).collect(Collectors.toList());
    }

    @Override
    public ItemDto addNewItem(Long userId, ItemDto itemDto) {
        Item item = ItemMapper.mapDtoToItem(validateItem(itemDto));
        item.setOwner(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found")));
        return ItemMapper.mapItemToDto(itemRepository.save(item));
    }

    @Override
    public void deleteItem(Long userId, Long itemId) {
//        itemRepository.deleteByUserIdAndItemId(userId, itemId);
    }

    @Override
    public ItemDto updateItem(Long userId, Long itemId, ItemDto itemDto) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException("Item not found"));
        User owner = item.getOwner();
        if (owner.getId() != userId) {
            throw new UserNotFoundException("Wrong user");
        } else {
            itemDto.setId(itemId);
            item = ItemMapper.mapDtoToItem(itemDto);
            item.setOwner(owner);
            return ItemMapper.mapItemToDto(itemRepository.save(item));
        }
    }

    @Override
    public ItemDto getItem(Long itemId) {
        return ItemMapper.mapItemToDto(itemRepository.findById(itemId).orElseThrow(
                () -> new ItemNotFoundException("Item not found")));
    }

    @Override
    public List<ItemDto> searchForItems(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return null;
//        return itemRepository.findItemByName(text).stream().map(ItemMapper::mapItemToDto).collect(Collectors.toList());
    }

    private ItemDto validateItem(ItemDto itemDto) {
//        if (itemDto.getName() == null || itemDto.getName().isBlank()) {
//            throw new ValidationException("Items needs a name");
//        } else if (itemDto.getDescription() == null || itemDto.getDescription().isBlank()) {
//            throw new ValidationException("Items needs a description");
//        } else if (itemDto.getAvailable() == null) {
//            throw new ValidationException("Items needs an availability status");
//        } else {
            return itemDto;

    }

}
