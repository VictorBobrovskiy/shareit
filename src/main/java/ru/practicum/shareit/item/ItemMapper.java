package ru.practicum.shareit.item;


public class ItemMapper {


    public static Item mapDtoToItem(ItemDto itemDto) {
        return new Item(
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.isAvailable());
    }

    public static ItemDto mapItemToDto(Item item) {
        return new ItemDto(
                item.getName(),
                item.getDescription(),
                item.isAvailable(),
                item.getItemRequest() != null ? item.getItemRequest().getId() : null
        );
    }

}
