package ru.practicum.shareit.comment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

public class CommentTest {

    @Test
    public void testConstructorWithId() {
        Long id = 1L;
        String text = "Test Comment";
        Item item = new Item();
        User author = new User();

        Comment comment = new Comment(id, text, item, author);
        Assertions.assertEquals(id, comment.getId());
        Assertions.assertEquals(text, comment.getText());
        Assertions.assertEquals(item, comment.getItem());
        Assertions.assertEquals(author, comment.getAuthor());
        Assertions.assertNotNull(comment.getCreated());
    }

    @Test
    public void testEqualsAndHashCode() {
        Item item = new Item(1L);
        User user = new User("Test User");
        Comment comment1 = new Comment("Test Comment", item, user);
        comment1.setId(1L);

        Comment comment2 = new Comment("Test Comment", item, user);
        comment2.setId(1L);

        Assertions.assertEquals(comment1, comment2);
        Assertions.assertEquals(comment1.hashCode(), comment2.hashCode());
    }

    @Test
    public void testToString() {
        Long id = 1L;
        String text = "Test Comment";
        LocalDateTime created = LocalDateTime.now();

        Comment comment = new Comment(id, text, new Item(), new User("Test User"));
        comment.setCreated(created);

        String expectedToString = "Comment{id=1, text='Test Comment', created=" + created + "}";
        Assertions.assertEquals(expectedToString, comment.toString());
    }

    // Add more test cases for other methods and constructors as needed

}
