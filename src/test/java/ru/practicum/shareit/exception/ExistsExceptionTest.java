package ru.practicum.shareit.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExistsExceptionTest {

    @Test
    public void testExistsException() {
        String errorMessage = "Item already exists";
        ExistsException exception = new ExistsException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}
