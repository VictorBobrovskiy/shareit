package ru.practicum.shareit.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ErrorResponseTest {

    @Test
    void getMessage_shouldReturnErrorMessage() {
        // Create ErrorResponse with an error message
        ErrorResponse errorResponse = new ErrorResponse("Error occurred");

        // Verify the error message
        Assertions.assertEquals("Error occurred", errorResponse.getError());
    }
}
