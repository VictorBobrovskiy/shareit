package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingNotFoundExceptionTest {

    @Test
    public void testItemNotFoundException() {
        String errorMessage = "Item not found";
        BookingNotFoundException exception = new BookingNotFoundException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}
