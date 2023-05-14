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

    private final ItemMapper itemMapper;

    private int itemId = 0;

    @Override
    public List<ItemDto> getItems(int userId) {
        return itemRepository.findByUserId(userId).stream().map(itemMapper::mapItemToDto).collect(Collectors.toList());
    }

    @Override
    public ItemDto addNewItem(int userId, ItemDto itemDto) {
        validateItem(itemDto);
        Item item = itemMapper.mapDtoToItem(itemDto);
        item.setOwner(userRepository.getUser(userId));
        item.setId(++itemId);
        return itemMapper.mapItemToDto(itemRepository.save(item));
    }

    @Override
    public void deleteItem(int userId, int itemId) {
        itemRepository.deleteByUserIdAndItemId(userId, itemId);
    }

    @Override
    public ItemDto updateItem(int userId, int itemId, ItemDto itemDto) {
        Item item = itemRepository.getItem(itemId);
        User owner = item.getOwner();
        if (owner.getId() != userId) {
            throw new UserNotFoundException("Wrong user");
        } else {
            itemDto.setId(itemId);
            item = itemMapper.mapDtoToItem(itemDto);
            item.setOwner(owner);
            return itemMapper.mapItemToDto(itemRepository.updateItem(userId, item));
        }
    }

    @Override
    public ItemDto getItem(int itemId) {
        return itemMapper.mapItemToDto(itemRepository.getItem(itemId));
    }

    @Override
    public List<ItemDto> searchForItems(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return itemRepository.searchForItems(text).stream().map(itemMapper::mapItemToDto).collect(Collectors.toList());
    }

    private ItemDto validateItem(ItemDto itemDto) {
        if (itemDto.getName() == null || itemDto.getName().isBlank()) {
            throw new ValidationException("Items needs a name");
        } else if (itemDto.getDescription() == null || itemDto.getDescription().isBlank()) {
            throw new ValidationException("Items needs a description");
        } else if (itemDto.getAvailable() == null) {
            throw new ValidationException("Items needs availability status");
        } else {
            return itemDto;
        }
    }

}
