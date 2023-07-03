package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

public class BookingTest {

    @Test
    public void testConstructorWithId() {
        Long id = 1L;
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 12, 0);
        LocalDateTime end = LocalDateTime.of(2023, 1, 2, 12, 0);
        Item item = new Item();
        User booker = new User("Test User");
        Status status = Status.APPROVED;

        Booking booking = new Booking(id, start, end, item, booker, status);
        Assertions.assertEquals(id, booking.getId());
        Assertions.assertEquals(start, booking.getStart());
        Assertions.assertEquals(end, booking.getEnd());
        Assertions.assertEquals(item, booking.getItem());
        Assertions.assertEquals(booker, booking.getBooker());
        Assertions.assertEquals(status, booking.getStatus());
    }

    @Test
    public void testEqualsAndHashCode() {
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 12, 0);
        LocalDateTime end = LocalDateTime.of(2023, 1, 2, 12, 0);
        Item item = new Item();
        User booker = new User("Test User");
        Status status = Status.APPROVED;

        Booking booking1 = new Booking(1L, start, end, item, booker, status);

        Booking booking2 = new Booking(1L, start, end, item, booker, status);

        Assertions.assertEquals(booking1, booking2);
        Assertions.assertEquals(booking1.hashCode(), booking2.hashCode());
    }

    @Test
    public void testToString() {
        Long id = 1L;
        LocalDateTime start = LocalDateTime.of(2023, 1, 1, 12, 0);
        LocalDateTime end = LocalDateTime.of(2023, 1, 2, 12, 0);
        Status status = Status.WAITING;

        Booking booking = new Booking(id, start, end, new Item(), new User("Test User"), status);

        String expectedToString = "Booking{id=1, start=2023-01-01T12:00, end=2023-01-02T12:00, status=WAITING}";
        Assertions.assertEquals(expectedToString, booking.toString());
    }

    // Add more test cases for other methods and constructors as needed

}
