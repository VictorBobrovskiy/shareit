package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookingDtoTest {

    @Test
    public void testBookingDto() {
        Long id = 1L;
        LocalDateTime start = LocalDateTime.of(2023, 6, 30, 9, 0);
        LocalDateTime end = LocalDateTime.of(2023, 6, 30, 17, 0);
        Long itemId = 2L;
        Long bookerId = 3L;
        String itemName = "Test Item";
        Status status = Status.APPROVED;

        BookingDto bookingDto = new BookingDto(id, start, end, itemId, bookerId, itemName, status);

        assertEquals(id, bookingDto.getId());
        assertEquals(start, bookingDto.getStart());
        assertEquals(end, bookingDto.getEnd());
        assertEquals(itemId, bookingDto.getItemId());
        assertEquals(bookerId, bookingDto.getBookerId());
        assertEquals(itemName, bookingDto.getItemName());
        assertEquals(status, bookingDto.getStatus());

        // Test compareTo method
        BookingDto otherBookingDto = new BookingDto();
        otherBookingDto.setStart(LocalDateTime.of(2023, 7, 1, 10, 0));
        assertTrue(bookingDto.compareTo(otherBookingDto) < 0);
    }
}
