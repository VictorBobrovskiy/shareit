package ru.practicum.shareit.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidationExceptionTest {

    @Test
    public void testValidationException() {
        String errorMessage = "Validation failed";
        ValidationException exception = new ValidationException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}
