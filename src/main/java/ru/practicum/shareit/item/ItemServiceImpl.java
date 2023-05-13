package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.UserRepository;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final UserRepository userRepository;

    private int itemId = 0;

    @Override
    public List<ItemDto> getItems(int userId) {
        return itemRepository.findByUserId(userId).stream().map(ItemMapper::mapItemToDto).collect(Collectors.toList());
    }

    @Override
    public ItemDto addNewItem(int userId, ItemDto itemDto) {
        Item item = ItemMapper.mapDtoToItem(itemDto);
        item.setId(++itemId);
        item.setOwner(userRepository.getUser(userId));
        return ItemMapper.mapItemToDto(itemRepository.save(item));
    }

    @Override
    public void deleteItem(int userId, int itemId) {
        itemRepository.deleteByUserIdAndItemId(userId, itemId);
    }

    @Override
    public ItemDto updateItem(int userId, ItemDto itemDto) {
        Item item = ItemMapper.mapDtoToItem(itemDto);
        item.setId(++itemId);
        item.setOwner(userRepository.getUser(userId));
        return ItemMapper.mapItemToDto(itemRepository.updateItem(userId, item));
    }

    @Override
    public ItemDto getItem(int itemId) {
        return ItemMapper.mapItemToDto(itemRepository.getItem(itemId));
    }
}
