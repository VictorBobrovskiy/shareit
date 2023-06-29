package ru.practicum.shareit.ItemRequest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemRequestNotFoundExceptionTest {
    @Test
    public void testItemNotFoundException() {
        String errorMessage = "Item not found";
        ItemRequestNotFoundException exception = new ItemRequestNotFoundException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}
