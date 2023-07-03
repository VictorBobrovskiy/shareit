package ru.practicum.shareit.item;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.practicum.shareit.ItemRequest.ItemRequest;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.comment.Comment;
import ru.practicum.shareit.comment.CommentDto;
import ru.practicum.shareit.comment.CommentRepository;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserNotFoundException;
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
    public void testGetItems() {
        // Mock data
        Long userId = 1L;
        int from = 0;
        int size = 10;
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item());
        when(itemRepository.findAllItemsByOwnerIdOrderById(userId)).thenReturn(itemList);

        // Invoke the method
        List<ItemDto> result = itemService.getItems(userId, from, size);

        // Verify the behavior and assertions
        verify(itemRepository, times(1)).findAllItemsByOwnerIdOrderById(userId);
        Assertions.assertEquals(1, result.size());
        // Add additional assertions as needed
    }

    @Test
    public void testDeleteItem() {
        // Mock data
        Long userId = 1L;
        Long itemId = 1L;
        Item item = new Item();
        item.setOwner(new User(userId));
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        // Invoke the method
        itemService.deleteItem(userId, itemId);

        // Verify the behavior and assertions
        verify(itemRepository, times(1)).findById(itemId);
        verify(itemRepository, times(1)).deleteById(itemId);
        // Add additional assertions as needed
    }

    @Test
    public void testUpdateItem() {
        // Mock data
        Long userId = 1L;
        Long itemId = 1L;
        Item item = new Item();
        item.setOwner(new User(userId));
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        when(itemRepository.save(any(Item.class))).thenReturn(item);

        ItemDto itemDto = new ItemDto();
        itemDto.setName("Updated Name");

        // Invoke the method
        ItemDto result = itemService.updateItem(userId, itemId, itemDto);

        // Verify the behavior and assertions
        verify(itemRepository, times(1)).findById(itemId);
        verify(itemRepository, times(1)).save(item);
        Assertions.assertEquals(itemDto.getName(), result.getName());
        // Add additional assertions as needed
    }


    @Test
    public void getNextBookingValidData() {
        Item item = new Item();
        item.setId(1L);

        LocalDateTime now = LocalDateTime.now().minusMinutes(1);
        Booking nextBooking = new Booking();
        nextBooking.setId(1L);
        nextBooking.setItem(item);
        nextBooking.setStatus(Status.APPROVED);

        when(bookingRepository.findFirstByItemAndStartAfterAndStatusOrderByStartAsc(item.getId(), now, "APPROVED"))
                .thenReturn(Optional.of(nextBooking));

        Booking result = itemService.getNextBooking(item);

        assertEquals(null, result);
    }

    @Test
    void getItemValidUserIdAndItemId() {
        Long userId = 1L;
        Long itemId = 1L;
        Item item = new Item(itemId, "Item 1", "Description 1", true, new ItemRequest());
        item.setOwner(new User(userId, "User 1", "user1@example.com"));
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        when(bookingRepository.findFirstByItemAndStartAfterAndStatusOrderByStartAsc(anyLong(), any(LocalDateTime.class), any())).thenReturn(Optional.empty());
        when(bookingRepository.findFirstByItemAndStartBeforeAndStatusOrderByStartDesc(anyLong(), any(LocalDateTime.class), any())).thenReturn(Optional.empty());
        when(commentRepository.findAllByItemId(itemId)).thenReturn(new ArrayList<>());

        ItemDto result = itemService.getItem(userId, itemId);

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
    void getItemInvalidItemId() {
        Long userId = 1L;
        Long itemId = 1L;
        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> itemService.getItem(userId, itemId));
        verify(itemRepository).findById(itemId);
        verify(bookingRepository, never()).findFirstByItemAndStartAfterAndStatusOrderByStartAsc(anyLong(), any(LocalDateTime.class), any());
        verify(bookingRepository, never()).findFirstByItemAndStartBeforeAndStatusOrderByStartDesc(anyLong(), any(LocalDateTime.class), any());
        verify(commentRepository, never()).findAllByItemId(anyLong());
    }


    @Test
    void addNewItemValidUserIdAndItem() {
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

        ItemDto result = itemService.addNewItem(userId, itemDto);

        assertNotNull(result);
        assertEquals(itemDto.getName(), result.getName());
        assertEquals(itemDto.getDescription(), result.getDescription());
        assertEquals(itemDto.getAvailable(), result.getAvailable());
        verify(userRepository).findById(userId);
        verify(itemRepository).save(any(Item.class));
    }

    @Test
    void deleteItemInvalidItemId() {
        Long userId = 1L;
        Long itemId = 1L;
        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> itemService.deleteItem(userId, itemId));
        verify(itemRepository).findById(itemId);
        verify(itemRepository, never()).deleteById(itemId);
    }

    @Test
    void deleteItemInvalidUserId() {

        Long userId = 1L;
        Long itemId = 1L;
        Item item = new Item(itemId, "Item 1", "Description 1", true, new ItemRequest());
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));


        verify(itemRepository, never()).deleteById(itemId);
    }


    @Test
    void updateItemInvalidItemId() {

        Long userId = 1L;
        Long itemId = 1L;
        ItemDto itemDto = new ItemDto();
        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> itemService.updateItem(userId, itemId, itemDto));
        verify(itemRepository).findById(itemId);
        verify(itemRepository, never()).save(any(Item.class));
    }

    @Test
    void updateItemInvalidUserId() {

        Long userId = 1L;
        Long itemId = 1L;
        ItemDto itemDto = new ItemDto();
        Item item = new Item(itemId, "Item 1", "Description 1", true, new ItemRequest());
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
        ;
        verify(itemRepository, never()).save(any(Item.class));
    }

    @Test
    void searchForItemsValidText() {
        String searchText = "item";
        int from = 0;
        int size = 10;
        Item item1 = new Item(1L, "Item 1", "Description 1", true, new ItemRequest());
        Item item2 = new Item(2L, "Item 2", "Description 2", true, new ItemRequest());
        List<Item> items = Arrays.asList(item1, item2);
        when(itemRepository.findAllItemsByDescriptionContainingIgnoreCaseAndAvailableTrue(searchText)).thenReturn(items);

        List<ItemDto> result = itemService.searchForItems(searchText, from, size);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(item1.getName(), result.get(0).getName());
        assertEquals(item1.getDescription(), result.get(0).getDescription());
        assertEquals(item2.getName(), result.get(1).getName());
        assertEquals(item2.getDescription(), result.get(1).getDescription());
        verify(itemRepository).findAllItemsByDescriptionContainingIgnoreCaseAndAvailableTrue(searchText);
    }


    @Test
    public void getNextBookingNoBookingFound() {
        Item item = new Item();
        item.setId(1L);

        LocalDateTime now = LocalDateTime.now();

        when(bookingRepository.findFirstByItemAndStartAfterAndStatusOrderByStartAsc(item.getId(), now, "APPROVED"))
                .thenReturn(Optional.empty());

        Booking result = itemService.getNextBooking(item);

        assertNull(result);
    }

    @Test
    public void addNewCommentValidData() {
        Long userId = 1L;
        Long itemId = 1L;

        Item item = new Item();
        item.setId(itemId);

        User author = new User();
        author.setId(userId);

        LocalDateTime now = LocalDateTime.now();

        CommentDto commentDto = new CommentDto();
        commentDto.setItemId(itemId);
        commentDto.setAuthorName(author.getName());

        List<Booking> bookings = new ArrayList<>();
        Booking pastBooking = new Booking();
        pastBooking.setEnd(now.minusDays(1));
        pastBooking.setItem(item);
        bookings.add(pastBooking);

        when(bookingRepository.findAllBookingsByBookerIdOrderByStartDesc(userId)).thenReturn(bookings);
        when(userRepository.findById(userId)).thenReturn(Optional.of(author));
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        Comment comment = new Comment();
        comment.setItem(item);
        comment.setAuthor(author);
        comment.setCreated(LocalDateTime.now());

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentDto result = itemService.addNewComment(userId, itemId, commentDto);

        assertNotNull(result);
        assertEquals(comment.getItem().getId(), result.getItemId());
    }

    @Test
    public void addNewCommentNoPastBookings() {
        Long userId = 1L;
        Long itemId = 1L;
        CommentDto commentDto = new CommentDto();
        List<Booking> bookings = new ArrayList<>();

        when(bookingRepository.findAllBookingsByBookerIdOrderByStartDesc(userId)).thenReturn(bookings);

        assertThrows(ValidationException.class, () -> itemService.addNewComment(userId, itemId, commentDto));

    }

    @Test
    public void addNewCommentInvalidUser() {
        Long userId = 1L;
        Long itemId = 1L;
        Item item = new Item(itemId);
        CommentDto commentDto = new CommentDto();
        List<Booking> bookings = new ArrayList<>();
        Booking pastBooking = new Booking();
        pastBooking.setEnd(LocalDateTime.now().minusDays(1));
        pastBooking.setItem(item);
        bookings.add(pastBooking);

        when(bookingRepository.findAllBookingsByBookerIdOrderByStartDesc(userId)).thenReturn(bookings);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> itemService.addNewComment(userId, itemId, commentDto));

    }

}
