package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import ru.practicum.shareit.ItemRequest.*;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserNotFoundException;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ItemRequestServiceImplTest {

    private ItemRequestService itemRequestService;

    @Mock
    private ItemRequestRepository itemRequestRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ItemRepository itemRepository;

    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        itemRequestService = new ItemRequestServiceImpl(itemRequestRepository, userRepository, itemRepository, modelMapper);
    }

    @Test
    void createItemRequest_validData_itemRequestCreated() {
        Long userId = 1L;
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setDescription("Test item request");

        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(itemRequestRepository.save(any(ItemRequest.class))).thenAnswer(invocation -> {
            ItemRequest savedRequest = invocation.getArgument(0);
            savedRequest.setId(1L);
            savedRequest.setCreated(LocalDateTime.now());
            return savedRequest;
        });

        ItemRequestDto createdRequest = itemRequestService.createItemRequest(userId, itemRequestDto);

        assertNotNull(createdRequest);
        assertEquals(itemRequestDto.getDescription(), createdRequest.getDescription());
        assertNotNull(createdRequest.getCreated());
    }

    @Test
    void createItemRequest_invalidUser_userNotFoundExceptionThrown() {
        Long userId = 1L;
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setDescription("Test item request");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> itemRequestService.createItemRequest(userId, itemRequestDto));

        verify(itemRequestRepository, never()).save(any(ItemRequest.class));
    }

    @Test
    void getOwnItemRequests_validUser_itemRequestsRetrieved() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        ItemRequest itemRequest1 = new ItemRequest();
        itemRequest1.setId(1L);
        itemRequest1.setDescription("Item Request 1");

        ItemRequest itemRequest2 = new ItemRequest();
        itemRequest2.setId(2L);
        itemRequest2.setDescription("Item Request 2");

        List<ItemRequest> itemRequestList = new ArrayList<>();
        itemRequestList.add(itemRequest1);
        itemRequestList.add(itemRequest2);

        when(userRepository.existsById(userId)).thenReturn(true);
        when(itemRequestRepository.findAllByRequesterId(userId)).thenReturn(itemRequestList);
        when(itemRepository.getAllByRequestId(anyLong())).thenReturn(new ArrayList<>());

        List<ItemRequestDto> result = itemRequestService.getOwnItemRequests(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(itemRequest1.getDescription(), result.get(0).getDescription());
        assertEquals(itemRequest2.getDescription(), result.get(1).getDescription());
    }

    @Test
    void getOwnItemRequests_invalidUser_userNotFoundExceptionThrown() {
        Long userId = 1L;

        when(userRepository.existsById(userId)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> {
            itemRequestService.getOwnItemRequests(userId);
        });

        verify(itemRequestRepository, never()).findAllByRequesterId(userId);
    }

    @Test
    void getItemRequest_validUserAndItemRequest_itemRequestRetrieved() {
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
//        when(itemRepository.getAllByRequestId(itemRequestId)).thenReturn(itemList);

        ItemRequestDto result = itemRequestService.getItemRequest(userId, itemRequestId);

        assertNotNull(result);
        assertEquals(itemRequest.getDescription(), result.getDescription());
        assertEquals(2, result.getItems().size());
        assertEquals(item1.getName(), result.getItems().get(0).getName());
        assertEquals(item2.getName(), result.getItems().get(1).getName());
    }


    @Test
    void getItemRequest_validData_itemRequestRetrieved() {
        long userId = 1L;
        long itemRequestId = 1L;

        User user = new User();
        user.setId(userId);

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setId(itemRequestId);
        itemRequest.setDescription("Item Request");

        when(userRepository.existsById(userId)).thenReturn(true);
        when(itemRequestRepository.findById(itemRequestId)).thenReturn(Optional.of(itemRequest));
        when(itemRepository.getAllByRequestId(itemRequestId)).thenReturn(new ArrayList<>());

        ItemRequestDto result = itemRequestService.getItemRequest(userId, itemRequestId);

        assertNotNull(result);
        assertEquals(itemRequest.getDescription(), result.getDescription());
        assertEquals(itemRequest.getId(), result.getId());
    }

    @Test
    void getItemRequest_invalidUser_userNotFoundExceptionThrown() {
        Long userId = 1L;
        Long itemRequestId = 1L;

        when(userRepository.existsById(userId)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> {
            itemRequestService.getItemRequest(userId, itemRequestId);
        });

        verify(itemRequestRepository, never()).findById(itemRequestId);
    }
}
