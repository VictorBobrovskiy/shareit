package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

public class BookingTest {

    @Test
    public void testConstructorAndGetters() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusHours(2);
        Item item = new Item();
        User booker = new User();
        Status status = Status.APPROVED;

        Booking booking = new Booking(1L, start, end, item, booker, status);

        Assertions.assertEquals(1L, booking.getId());
        Assertions.assertEquals(start, booking.getStart());
        Assertions.assertEquals(end, booking.getEnd());
        Assertions.assertEquals(item, booking.getItem());
        Assertions.assertEquals(booker, booking.getBooker());
        Assertions.assertEquals(status, booking.getStatus());
    }

    @Test
    public void testSetterAndGetters() {
        Booking booking = new Booking();

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusHours(2);
        Item item = new Item();
        User booker = new User();
        Status status = Status.APPROVED;

        booking.setId(1L);
        booking.setStart(start);
        booking.setEnd(end);
        booking.setItem(item);
        booking.setBooker(booker);
        booking.setStatus(status);

        Assertions.assertEquals(1L, booking.getId());
        Assertions.assertEquals(start, booking.getStart());
        Assertions.assertEquals(end, booking.getEnd());
        Assertions.assertEquals(item, booking.getItem());
        Assertions.assertEquals(booker, booking.getBooker());
        Assertions.assertEquals(status, booking.getStatus());
    }

    @Test
    public void testEqualsAndHashCode() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusHours(2);
        Item item1 = new Item(1L, "Item 1", "Item 1 Description", true, null);
        Item item2 = new Item(2L, "Item 2", "Item 2 Description", true, null);
        User booker1 = new User(1L, "User 1", "user1@mail.ru");
        User booker2 = new User(2L, "User 2", "user2@mail.ru");
        Status status = Status.APPROVED;

        Booking booking1 = new Booking(1L, start, end, item1, booker1, status);
        Booking booking2 = new Booking(1L, start, end, item1, booker1, status);
        Booking booking3 = new Booking(2L, start, end, item1, booker1, status);
        Booking booking4 = new Booking(1L, start, end, item2, booker1, status);
        Booking booking5 = new Booking(1L, start, end, item1, booker2, status);
        Booking booking6 = new Booking(1L, start, end, item1, booker1, Status.WAITING);

        // Test equality
        Assertions.assertEquals(booking1, booking2);
        Assertions.assertNotEquals(booking1, booking3);
        Assertions.assertNotEquals(booking1, booking4);
        Assertions.assertNotEquals(booking1, booking5);
        Assertions.assertNotEquals(booking1, booking6);

        // Test hash code
        Assertions.assertEquals(booking1.hashCode(), booking2.hashCode());
        Assertions.assertNotEquals(booking1.hashCode(), booking3.hashCode());
        Assertions.assertNotEquals(booking1.hashCode(), booking4.hashCode());
        Assertions.assertNotEquals(booking1.hashCode(), booking5.hashCode());
        Assertions.assertNotEquals(booking1.hashCode(), booking6.hashCode());
    }

    @Test
    public void testToString() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusHours(2);
        Booking booking = new Booking(start, end);
        Item item = new Item(1L, "Item 1", "Item 1 Description", true, null);
        User booker = new User(1L, "User 1", "user1@mail.ru");
        Status status = Status.APPROVED;
        booking.setItem(item);
        booking.setBooker(booker);
        booking.setStatus(status);
        booking.setId(1L);

        String expectedString = "Booking{id=1, start=" + start + ", end=" + end + ", status=" + status + "}";
        Assertions.assertEquals(expectedString, booking.toString());
    }
}
