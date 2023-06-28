package ru.practicum.shareit.ItemRequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
public class ItemRequestRepositoryTest {

    @Autowired
    private ItemRequestRepository itemRequestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testFindAllByRequesterId() {
        // Create a test user
        User user = new User(3L, "Mike", "mike@go.com");
        userRepository.save(user);

        // Create test item requests
        ItemRequest itemRequest1 = new ItemRequest();
        itemRequest1.setDescription("Item request 1");
        itemRequest1.setRequester(user);
        itemRequest1.setCreated(LocalDateTime.now());
        itemRequestRepository.save(itemRequest1);

        ItemRequest itemRequest2 = new ItemRequest();
        itemRequest2.setDescription("Item request 2");
        itemRequest2.setRequester(user);
        itemRequest2.setCreated(LocalDateTime.now());
        itemRequestRepository.save(itemRequest2);

        // Retrieve item requests by user ID
        List<ItemRequest> result = itemRequestRepository.findAllByRequesterId(user.getId());

        // Assertions
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void testFindAllOrderByCreated() {

        User user = new User(4L, "Nike", "nike@go.com");
        userRepository.save(user);
        // Create test item requests
        ItemRequest itemRequest1 = new ItemRequest();
        itemRequest1.setDescription("Item request 1");
        itemRequest1.setCreated(LocalDateTime.now().minusDays(2));
        itemRequest1.setRequester(user);
        itemRequestRepository.save(itemRequest1);

        ItemRequest itemRequest2 = new ItemRequest();
        itemRequest2.setDescription("Item request 2");
        itemRequest2.setCreated(LocalDateTime.now().minusDays(1));
        itemRequest2.setRequester(user);
        itemRequestRepository.save(itemRequest2);

        // Set up pagination
        Pageable pageable = PageRequest.of(0, 10);

        // Retrieve item requests ordered by created date
        Page<ItemRequest> result = itemRequestRepository.findAllOrderByCreated(pageable);

        // Assertions
        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.getTotalElements());
    }
}
