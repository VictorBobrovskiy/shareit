package ru.practicum.shareit.comment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentNotFoundExceptionTest {

    @Test
    public void testItemNotFoundException() {
        String errorMessage = "Item not found";
        CommentNotFoundException exception = new CommentNotFoundException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}
