package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserAccessExceptionTest {

    @Test
    public void testUserAccessException() {
        String errorMessage = "Access denied";
        UserAccessException exception = new UserAccessException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}
