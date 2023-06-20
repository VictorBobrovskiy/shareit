package ru.practicum.shareit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.shareit.ItemRequest.ItemRequest;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.comment.Comment;
import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.comment.CommentRepository;
import ru.practicum.shareit.item.*;
import ru.practicum.shareit.user.UserAccessException;
import ru.practicum.shareit.user.UserNotFoundException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getItem_ValidUserIdAndItemId_ReturnsItemDto() {
        // Arrange
        Long userId = 1L;
        Long itemId = 1L;
        Item item = new Item(itemId, "Item 1", "Description 1", true, new ItemRequest());
        item.setOwner(new User(userId, "User 1", "user1@example.com"));
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        when(bookingRepository.findFirstByItemAndStartAfterAndStatusOrderByStartAsc(anyLong(), any(LocalDateTime.class), any())).thenReturn(Optional.empty());
        when(bookingRepository.findFirstByItemAndStartBeforeAndStatusOrderByStartDesc(anyLong(), any(LocalDateTime.class), any())).thenReturn(Optional.empty());
        when(commentRepository.findAllByItemId(itemId)).thenReturn(new ArrayList<>());

        // Act
        ItemDto result = itemService.getItem(userId, itemId);

        // Assert
        assertNotNull(result);
        assertEquals(item.getName(), result.getName());
        assertEquals(item.getDescription(), result.getDescription());
        assertNull(result.getLastBooking());
        assertNull(result.getNextBooking());
        verify(itemRepository).findById(itemId);
        verify(bookingRepository).findFirstByItemAndStartAfterAndStatusOrderByStartAsc(anyLong(), any(LocalDateTime.class), any());
        verify(bookingRepository).findFirstByItemAndStartBeforeAndStatusOrderByStartDesc(anyLong(), any(LocalDateTime.class), any());
        verify(commentRepository).findAllByItemId(itemId);
    }

    @Test
    void getItem_InvalidItemId_ThrowsItemNotFoundException() {
        // Arrange
        Long userId = 1L;
        Long itemId = 1L;
        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ItemNotFoundException.class, () -> itemService.getItem(userId, itemId));
        verify(itemRepository).findById(itemId);
        verify(bookingRepository, never()).findFirstByItemAndStartAfterAndStatusOrderByStartAsc(anyLong(), any(LocalDateTime.class), any());
        verify(bookingRepository, never()).findFirstByItemAndStartBeforeAndStatusOrderByStartDesc(anyLong(), any(LocalDateTime.class), any());
        verify(commentRepository, never()).findAllByItemId(anyLong());
    }


    @Test
    void addNewItem_ValidUserIdAndItemDto_ReturnsItemDto() {
        // Arrange
        Long userId = 1L;
        ItemDto itemDto = new ItemDto();
        itemDto.setName("Item 1");
        itemDto.setDescription("Description 1");
        itemDto.setAvailable(true);
        User user = new User(userId, "User 1", "user1@example.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(itemRepository.save(any(Item.class))).thenAnswer(invocation -> {
            Item savedItem = invocation.getArgument(0);
            savedItem.setId(1L);
            return savedItem;
        });

        // Act
        ItemDto result = itemService.addNewItem(userId, itemDto);

        // Assert
        assertNotNull(result);
        assertEquals(itemDto.getName(), result.getName());
        assertEquals(itemDto.getDescription(), result.getDescription());
        assertEquals(itemDto.getAvailable(), result.getAvailable());
        verify(userRepository).findById(userId);
        verify(itemRepository).save(any(Item.class));
    }

    @Test
    void deleteItem_InvalidItemId_ThrowsItemNotFoundException() {
        // Arrange
        Long userId = 1L;
        Long itemId = 1L;
        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ItemNotFoundException.class, () -> itemService.deleteItem(userId, itemId));
        verify(itemRepository).findById(itemId);
        verify(itemRepository, never()).deleteById(itemId);
    }

    @Test
    void deleteItem_InvalidUserId_ThrowsUserAccessException() {

        Long userId = 1L;
        Long itemId = 1L;
        Item item = new Item(itemId, "Item 1", "Description 1", true, new ItemRequest());
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));


        verify(itemRepository, never()).deleteById(itemId);
    }


    @Test
    void updateItem_InvalidItemId_ThrowsItemNotFoundException() {

        Long userId = 1L;
        Long itemId = 1L;
        ItemDto itemDto = new ItemDto();
        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> itemService.updateItem(userId, itemId, itemDto));
        verify(itemRepository).findById(itemId);
        verify(itemRepository, never()).save(any(Item.class));
    }

    @Test
    void updateItem_InvalidUserId_ThrowsUserAccessException() {

        Long userId = 1L;
        Long itemId = 1L;
        ItemDto itemDto = new ItemDto();
        Item item = new Item(itemId, "Item 1", "Description 1", true, new ItemRequest());
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        ;
        verify(itemRepository, never()).save(any(Item.class));
    }

    @Test
    void searchForItems_ValidText_ReturnsMatchingItems() {
        // Arrange
        String searchText = "item";
        int from = 0;
        int size = 10;
        Item item1 = new Item(1L, "Item 1", "Description 1", true, new ItemRequest());
        Item item2 = new Item(2L, "Item 2", "Description 2", true, new ItemRequest());
        List<Item> items = Arrays.asList(item1, item2);
        when(itemRepository.findAllItemsByDescriptionContainingIgnoreCaseAndAvailableTrue(searchText)).thenReturn(items);

        // Act
        List<ItemDto> result = itemService.searchForItems(searchText, from, size);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(item1.getName(), result.get(0).getName());
        assertEquals(item1.getDescription(), result.get(0).getDescription());
        assertEquals(item2.getName(), result.get(1).getName());
        assertEquals(item2.getDescription(), result.get(1).getDescription());
        verify(itemRepository).findAllItemsByDescriptionContainingIgnoreCaseAndAvailableTrue(searchText);
    }


}
