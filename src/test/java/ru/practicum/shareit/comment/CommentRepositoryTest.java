package ru.practicum.shareit.comment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class CommentRepositoryTest {


    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindAllByItemId() {

        Item item = new Item();
        item.setId(1L);
        item.setName("Item 1");
        item.setDescription("Description 1");
        item.setAvailable(false);

        Item item2 = new Item();
        item2.setId(2L);
        item2.setName("Item 2");
        item2.setDescription("Description 2");
        item2.setAvailable(true);


        User owner = new User(1L, "name", "name@email.com");
        userRepository.save(owner);
        item.setOwner(owner);
        item2.setOwner(owner);

        item = itemRepository.save(item);
        item2 = itemRepository.save(item2);

        User author = new User(2L, "name2", "name2@email.com");
        userRepository.save(author);

        Comment comment1 = new Comment();
        comment1.setText("Comment 1");
        comment1.setItem(item);
        comment1.setAuthor(author);
        comment1.setCreated(LocalDateTime.now());
        commentRepository.save(comment1);
        Comment comment2 = new Comment();
        comment2.setText("Comment 2");
        comment2.setItem(item);
        comment2.setAuthor(author);
        comment2.setCreated(LocalDateTime.now());
        commentRepository.save(comment2);
        Comment comment3 = new Comment();
        comment3.setText("Comment 3");
        comment3.setItem(item2);
        comment3.setAuthor(author);
        comment3.setCreated(LocalDateTime.now());
        commentRepository.save(comment3);


        List<Comment> commentsForItem1 = commentRepository.findAllByItemId(item.getId());

        Assertions.assertEquals(2, commentsForItem1.size());
        List<String> commentTextsForItem1 = Arrays.asList(commentsForItem1.get(0).getText(), commentsForItem1.get(1).getText());
        Assertions.assertTrue(commentTextsForItem1.contains(comment1.getText()));
        Assertions.assertTrue(commentTextsForItem1.contains(comment2.getText()));

        List<Comment> commentsForItem2 = commentRepository.findAllByItemId(item2.getId());

        Assertions.assertEquals(1, commentsForItem2.size());
        Assertions.assertEquals(comment3.getText(), commentsForItem2.get(0).getText());
    }
}
