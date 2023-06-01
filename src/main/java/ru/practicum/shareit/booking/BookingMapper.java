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
        return new BookingDto(
        booking.getId(),
        booking.getStart(),
        booking.getEnd(),
        booking.getItem().getId(),
        booking.getBooker().getId(),
        booking.getItem().getName(),
        booking.getStatus()
        );
    }
}
