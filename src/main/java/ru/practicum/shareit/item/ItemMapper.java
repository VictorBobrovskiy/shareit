package ru.practicum.shareit.item;


import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.request.ItemRequest;

import java.awt.print.Book;
import java.util.Map;
import java.util.Set;


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
        Booking lastBooking = new Booking();
        Booking nextBooking = new Booking();
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getRequest() != null ? item.getRequest().getId() : null,
                lastBooking,
                nextBooking
        );
    }

    public static ItemOwnerDto mapItemToOwnerDto(Item item) {
        Booking lastBooking = new Booking();
        Booking nextBooking = new Booking();
        return new ItemOwnerDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getRequest() != null ? item.getRequest().getId() : null,
                lastBooking,
                nextBooking
        );
    }
}
