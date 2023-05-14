package ru.practicum.shareit.item;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.request.ItemRequest;

@Component
@RequiredArgsConstructor
public class ItemMapper {


    private final ItemRepository itemRepository;

    public Item mapDtoToItem(ItemDto itemDto) {
        return new Item(
                itemDto.getId(),
                itemDto.getName() != null ? itemDto.getName() : itemRepository.getItem(itemDto.getId()).getName(),
                itemDto.getDescription() != null ? itemDto.getDescription() : itemRepository.getItem(itemDto.getId()).getDescription(),
                itemDto.getAvailable() != null ? itemDto.getAvailable() : itemRepository.getItem(itemDto.getId()).getAvailable(),
                itemDto.getRequestId() != 0 ? new ItemRequest(itemDto.getRequestId()) : null
        );
    }

    public ItemDto mapItemToDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getItemRequest() != null ? item.getItemRequest().getId() : 0
        );
    }

}
