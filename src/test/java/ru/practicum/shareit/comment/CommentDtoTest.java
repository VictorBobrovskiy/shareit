package ru.practicum.shareit.comment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class CommentDtoTest {

    @Test
    public void testCommentDtoConstructor() {
        // Prepare test data
        Long commentId = 1L;
        String text = "This is a comment";
        Long itemId = 2L;
        String authorName = "John Doe";
        LocalDateTime created = LocalDateTime.now();

        // Create a CommentDto using the constructor
        CommentDto commentDto = new CommentDto(commentId, text, itemId, authorName, created);

        // Assertions
        Assertions.assertEquals(commentId, commentDto.getId());
        Assertions.assertEquals(text, commentDto.getText());
        Assertions.assertEquals(itemId, commentDto.getItemId());
        Assertions.assertEquals(authorName, commentDto.getAuthorName());
        Assertions.assertEquals(created, commentDto.getCreated());
    }

    @Test
    public void testCommentDtoSetterGetter() {
        // Prepare test data
        CommentDto commentDto = new CommentDto();

        Long commentId = 1L;
        String text = "This is a comment";
        Long itemId = 2L;
        String authorName = "John Doe";
        LocalDateTime created = LocalDateTime.now();

        // Set values using setters
        commentDto.setId(commentId);
        commentDto.setText(text);
        commentDto.setItemId(itemId);
        commentDto.setAuthorName(authorName);
        commentDto.setCreated(created);

        // Assertions
        Assertions.assertEquals(commentId, commentDto.getId());
        Assertions.assertEquals(text, commentDto.getText());
        Assertions.assertEquals(itemId, commentDto.getItemId());
        Assertions.assertEquals(authorName, commentDto.getAuthorName());
        Assertions.assertEquals(created, commentDto.getCreated());
    }
}
