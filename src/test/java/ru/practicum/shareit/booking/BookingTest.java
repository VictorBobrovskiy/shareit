package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

public class BookingTest {

    @Test
    public void testBookingConstructor() {
        // Prepare test data
        Long bookingId = 1L;
        LocalDateTime start = LocalDateTime.of(2023, 6, 30, 10, 0);
        LocalDateTime end = LocalDateTime.of(2023, 6, 30, 12, 0);
        Item item = new Item(2L);
        User booker = new User(3L);
        Status status = Status.APPROVED;

        // Create a Booking using the constructor
        Booking booking = new Booking(bookingId, start, end, item, booker, status);

        // Assertions
        Assertions.assertEquals(bookingId, booking.getId());
        Assertions.assertEquals(start, booking.getStart());
        Assertions.assertEquals(end, booking.getEnd());
        Assertions.assertEquals(item, booking.getItem());
        Assertions.assertEquals(booker, booking.getBooker());
        Assertions.assertEquals(status, booking.getStatus());
    }

    @Test
    public void testBookingSetterGetter() {
        // Prepare test data
        Booking booking = new Booking();

        Long bookingId = 1L;
        LocalDateTime start = LocalDateTime.of(2023, 6, 30, 10, 0);
        LocalDateTime end = LocalDateTime.of(2023, 6, 30, 12, 0);
        Item item = new Item(2L);
        User booker = new User(3L);
        Status status = Status.APPROVED;

        // Set values using setters
        booking.setId(bookingId);
        booking.setStart(start);
        booking.setEnd(end);
        booking.setItem(item);
        booking.setBooker(booker);
        booking.setStatus(status);

        // Assertions
        Assertions.assertEquals(bookingId, booking.getId());
        Assertions.assertEquals(start, booking.getStart());
        Assertions.assertEquals(end, booking.getEnd());
        Assertions.assertEquals(item, booking.getItem());
        Assertions.assertEquals(booker, booking.getBooker());
        Assertions.assertEquals(status, booking.getStatus());
    }
}
