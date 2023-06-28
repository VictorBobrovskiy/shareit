package ru.practicum.shareit.comment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

public class CommentMapperTest {

    @Test
    public void testToComment() {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setText("Example comment");
        commentDto.setItemId(10L);
        commentDto.setAuthorName("John Doe");

        Comment comment = CommentMapper.toComment(commentDto);

        Assertions.assertEquals(commentDto.getId(), comment.getId());
        Assertions.assertEquals(commentDto.getText(), comment.getText());
        Assertions.assertEquals(commentDto.getItemId(), comment.getItem().getId());
        Assertions.assertEquals(commentDto.getAuthorName(), comment.getAuthor().getName());
    }

    @Test
    public void testToDto() {
        Comment comment = new Comment();
        comment.setId(2L);
        comment.setText("Another comment");
        Item item = new Item(20L);
        comment.setItem(item);
        User author = new User("Jane Smith");
        comment.setAuthor(author);

        CommentDto commentDto = CommentMapper.toDto(comment);

        Assertions.assertEquals(comment.getId(), commentDto.getId());
        Assertions.assertEquals(comment.getText(), commentDto.getText());
        Assertions.assertEquals(comment.getItem().getId(), commentDto.getItemId());
        Assertions.assertEquals(comment.getAuthor().getName(), commentDto.getAuthorName());
    }
}
