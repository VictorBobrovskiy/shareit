package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.ItemRequest.*;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserNotFoundException;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ItemRequestServiceImplTest {

    @Mock
    private ItemRequestRepository itemRequestRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRepository itemRepository;
    @InjectMocks
    private ItemRequestServiceImpl itemRequestService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void createItemRequestValidData() {
        Long userId = 1L;

        User user = new User(userId);

        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setDescription("Item Request");
        itemRequestDto.setCreated(LocalDateTime.now());

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        when(itemRequestRepository.save(any(ItemRequest.class))).thenReturn(ItemRequestMapper.toEntity(itemRequestDto));
        when(userRepository.existsById(userId)).thenReturn(true);

        ItemRequestDto result = itemRequestService.createItemRequest(userId, itemRequestDto);

        assertNotNull(result);
        assertNotNull(result.getCreated());
        assertEquals(itemRequestDto.getDescription(), result.getDescription());

    }

    @Test
    void createItemRequestInvalidUser() {
        Long userId = 1L;
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setDescription("Test description");

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            itemRequestService.createItemRequest(userId, itemRequestDto);
        });

        verify(itemRequestRepository, never()).save(any(ItemRequest.class));
    }

    @Test
    void getOwnItemRequestsValidUser() {
        Long userId = 1L;

        User user = new User(userId);

        ItemRequest itemRequest1 = new ItemRequest();
        itemRequest1.setId(1L);
        itemRequest1.setDescription("Item Request 1");
        itemRequest1.setRequester(user);

        ItemRequest itemRequest2 = new ItemRequest();
        itemRequest2.setId(2L);
        itemRequest2.setDescription("Item Request 2");
        itemRequest2.setRequester(user);

        List<ItemRequest> itemRequestList = new ArrayList<>();
        itemRequestList.add(itemRequest1);
        itemRequestList.add(itemRequest2);

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        when(itemRequestRepository.findAllByRequesterId(userId)).thenReturn(itemRequestList);
        when(itemRepository.getAllByRequestId(anyLong())).thenReturn(new ArrayList<>());
        when(userRepository.existsById(userId)).thenReturn(true);

        List<ItemRequestDto> result = itemRequestService.getOwnItemRequests(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(itemRequest1.getDescription(), result.get(0).getDescription());
        assertEquals(itemRequest2.getDescription(), result.get(1).getDescription());
    }

    @Test
    void getOwnItemRequestsInvalidUser() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            itemRequestService.getOwnItemRequests(userId);
        });

        verify(itemRequestRepository, never()).findAllByRequesterId(anyLong());
    }

    @Test
    void getAllItemRequestsValidPagination() {
        int from = 0;
        int size = 1;

        Long userId = 1L;
        User user = new User(userId);
        Long testUserId = 2L;

        ItemRequest itemRequest1 = new ItemRequest();
        itemRequest1.setId(1L);
        itemRequest1.setDescription("Item Request 1");
        itemRequest1.setCreated(LocalDateTime.now());
        itemRequest1.setRequester(user);

        ItemRequest itemRequest2 = new ItemRequest();
        itemRequest2.setId(2L);
        itemRequest2.setDescription("Item Request 2");
        itemRequest2.setCreated(LocalDateTime.now());
        itemRequest2.setRequester(user);

        List<ItemRequest> itemRequestList = new ArrayList<>();
        itemRequestList.add(itemRequest1);
        itemRequestList.add(itemRequest2);


        Pageable pageable = PageRequest.of(from, size);
        Page<ItemRequest> itemRequestPage = new PageImpl<>(itemRequestList, pageable, 2);

        when(itemRequestRepository.findAllOrderByCreated(pageable)).thenReturn(itemRequestPage);
        when(itemRepository.getAllByRequestId(anyLong())).thenReturn(new ArrayList<>());
        when(userRepository.existsById(testUserId)).thenReturn(true);

        List<ItemRequestDto> result = itemRequestService.getAllItemRequests(testUserId, from, size);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(itemRequest1.getDescription(), result.get(0).getDescription());
        assertEquals(itemRequest2.getDescription(), result.get(1).getDescription());
    }

    @Test
    void getItemRequestValidUserAndItemRequest() {
        Long userId = 1L;
        Long itemRequestId = 1L;

        User user = new User();
        user.setId(userId);

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setId(itemRequestId);
        itemRequest.setDescription("Item Request");

        Item item1 = new Item();
        item1.setId(1L);
        item1.setName("Item 1");

        Item item2 = new Item();
        item2.setId(2L);
        item2.setName("Item 2");

        List<Item> itemList = new ArrayList<>();
        itemList.add(item1);
        itemList.add(item2);

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        when(itemRequestRepository.findById(itemRequestId)).thenReturn(java.util.Optional.of(itemRequest));
        when(itemRepository.getAllByRequestId(itemRequestId)).thenReturn(itemList);
        when(userRepository.existsById(userId)).thenReturn(true);

        ItemRequestDto result = itemRequestService.getItemRequest(userId, itemRequestId);

        assertNotNull(result);
        assertEquals(itemRequest.getDescription(), result.getDescription());
        assertEquals(2, result.getItems().size());
        assertEquals(item1.getName(), result.getItems().get(0).getName());
        assertEquals(item2.getName(), result.getItems().get(1).getName());
    }

    @Test
    void getItemRequestInvalidUser() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            itemRequestService.getItemRequest(userId, 1L);
        });

        verify(itemRequestRepository, never()).findById(anyLong());
    }
}
