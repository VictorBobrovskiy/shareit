package ru.practicum.shareit.booking;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;


public class BookingMapper {

    public static Booking toBooking(BookingDto bookingDto) {
        return new Booking(
                bookingDto.getId(),
                bookingDto.getStart(),
                bookingDto.getEnd(),
                new Item(bookingDto.getItemId()),
                new User(bookingDto.getBookerId()),
                bookingDto.getStatus()
        );
    }

    public static BookingDto toDto (Booking booking) {
        Long id = booking.getId();
        LocalDateTime start = booking.getStart();
        LocalDateTime end = booking.getEnd();
        Long itemId = booking.getItem().getId();
        Long bookerId = booking.getBooker().getId();
        String itemName = booking.getItem().getName();
        String status  = booking.getStatus();
        return new BookingDto(id, start, end, itemId, bookerId, itemName, status);
    }
}
