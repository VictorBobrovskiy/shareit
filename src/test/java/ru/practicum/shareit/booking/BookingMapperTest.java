package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

public class BookingMapperTest {

    @Test
    public void testToBooking() {
        // Prepare test data
        Long bookingId = 1L;
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusHours(2);
        Long itemId = 2L;
        Long bookerId = 3L;
        Status status = Status.APPROVED;

        BookingDto bookingDto = new BookingDto(bookingId, start, end, itemId, bookerId, "Item Name", status);

        // Perform mapping
        Booking booking = BookingMapper.toBooking(bookingDto);

        // Assertions
        Assertions.assertEquals(bookingId, booking.getId());
        Assertions.assertEquals(start, booking.getStart());
        Assertions.assertEquals(end, booking.getEnd());
        Assertions.assertEquals(itemId, booking.getItem().getId());
        Assertions.assertEquals(bookerId, booking.getBooker().getId());
        Assertions.assertEquals(status, booking.getStatus());
    }

    @Test
    public void testToDto() {
        // Prepare test data
        Long bookingId = 1L;
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusHours(2);
        Long itemId = 2L;
        Long bookerId = 3L;
        String itemName = "Item Name";
        Status status = Status.APPROVED;

        Booking booking = new Booking(bookingId, start, end, new Item(itemId), new User(bookerId), status);

        // Perform mapping
        BookingDto bookingDto = BookingMapper.toDto(booking);

        // Assertions
        Assertions.assertEquals(bookingId, bookingDto.getId());
        Assertions.assertEquals(start, bookingDto.getStart());
        Assertions.assertEquals(end, bookingDto.getEnd());
        Assertions.assertEquals(itemId, bookingDto.getItemId());
        Assertions.assertEquals(bookerId, bookingDto.getBookerId());
        Assertions.assertEquals(status, bookingDto.getStatus());
    }
}
