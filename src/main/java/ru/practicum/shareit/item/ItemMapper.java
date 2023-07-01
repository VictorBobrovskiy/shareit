package ru.practicum.shareit.item;


import lombok.experimental.UtilityClass;
import ru.practicum.shareit.ItemRequest.ItemRequest;

@UtilityClass
public class ItemMapper {


    public static Item mapDtoToItem(ItemDto itemDto) {
        return new Item(
                itemDto.getId(),
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable(),
                itemDto.getRequestId() != null ? new ItemRequest(itemDto.getRequestId()) : null
        );
    }

    public static ItemDto mapItemToDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getRequest() != null ? item.getRequest().getId() : null
        );
    }

}
