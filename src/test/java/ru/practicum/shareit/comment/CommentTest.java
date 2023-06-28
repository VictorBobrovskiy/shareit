package ru.practicum.shareit.comment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

public class CommentTest {

    @Test
    public void testCommentConstructor() {
        // Prepare test data
        Long commentId = 1L;
        String text = "This is a comment";
        Item item = new Item(1L);
        User author = new User(1L);
        LocalDateTime created = LocalDateTime.now();

        // Create a comment using the constructor
        Comment comment = new Comment(commentId, text, item, author);

        // Assertions
        Assertions.assertEquals(commentId, comment.getId());
        Assertions.assertEquals(text, comment.getText());
        Assertions.assertEquals(item, comment.getItem());
        Assertions.assertEquals(author, comment.getAuthor());
        Assertions.assertEquals(created.getYear(), comment.getCreated().getYear());
        Assertions.assertEquals(created.getMonth(), comment.getCreated().getMonth());
        Assertions.assertEquals(created.getDayOfMonth(), comment.getCreated().getDayOfMonth());
        Assertions.assertEquals(created.getHour(), comment.getCreated().getHour());
        Assertions.assertEquals(created.getMinute(), comment.getCreated().getMinute());
        Assertions.assertEquals(created.getSecond(), comment.getCreated().getSecond());
    }

    @Test
    public void testCommentSetterGetter() {
        // Prepare test data
        Comment comment = new Comment();

        Long commentId = 1L;
        String text = "This is a comment";
        Item item = new Item(1L);
        User author = new User(1L);
        LocalDateTime created = LocalDateTime.now();

        // Set values using setters
        comment.setId(commentId);
        comment.setText(text);
        comment.setItem(item);
        comment.setAuthor(author);
        comment.setCreated(created);

        // Assertions
        Assertions.assertEquals(commentId, comment.getId());
        Assertions.assertEquals(text, comment.getText());
        Assertions.assertEquals(item, comment.getItem());
        Assertions.assertEquals(author, comment.getAuthor());
        Assertions.assertEquals(created, comment.getCreated());
    }
}
