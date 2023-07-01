package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserNotFoundExceptionTest {

    @Test
    public void testUserNotFoundException() {
        String errorMessage = "User not found";
        UserNotFoundException exception = new UserNotFoundException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}
