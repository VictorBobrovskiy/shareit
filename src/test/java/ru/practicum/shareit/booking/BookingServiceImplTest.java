package ru.practicum.shareit.booking;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserAccessException;
import ru.practicum.shareit.user.UserNotFoundException;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    public void checkUserExistsTest() {
        User booker = new User(10L);
        BookingDto bookingDto = new BookingDto();
        assertThrows(UserNotFoundException.class, () -> bookingService.checkUserExists(booker.getId()));

    }

    @Test
    public void addNewBookingValidData() {
        Long userId = 1L;
        BookingDto bookingDto = new BookingDto();
        bookingDto.setItemId(1L);
        bookingDto.setStart(LocalDateTime.of(2023, 7, 25, 12, 59));
        bookingDto.setEnd(LocalDateTime.of(2023, 7, 27, 0, 0));
        Item item = new Item();
        item.setId(1L);
        item.setAvailable(true);
        User owner = new User(2L);
        item.setOwner(owner);
        User booker = new User(userId);
        bookingDto.setItemId(item.getId());
        bookingDto.setBookerId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(booker));
        when(itemRepository.findById(bookingDto.getItemId())).thenReturn(Optional.of(item));
        when(bookingRepository.save(any(Booking.class))).thenReturn(new Booking());

        Booking result = bookingService.addNewBooking(userId, bookingDto);

        assertNotNull(result);
    }

    @Test(expected = ValidationException.class)
    public void addNewBookingItemNA() {
        Long userId = 1L;
        BookingDto bookingDto = new BookingDto();
        bookingDto.setItemId(1L);
        bookingDto.setStart(LocalDateTime.of(2023, 7, 25, 12, 59));
        bookingDto.setEnd(LocalDateTime.of(2023, 7, 27, 0, 0));
        Item item = new Item();
        item.setId(1L);
        item.setAvailable(false);
        User owner = new User(2L);
        item.setOwner(owner);
        User booker = new User(userId);
        bookingDto.setItemId(item.getId());
        bookingDto.setBookerId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(booker));
        when(itemRepository.findById(bookingDto.getItemId())).thenReturn(Optional.of(item));

        Booking result = bookingService.addNewBooking(userId, bookingDto);

    }

    @Test(expected = UserNotFoundException.class)
    public void addNewBookingInvalidUser() {
        Long userId = 1L;
        BookingDto bookingDto = new BookingDto();
        bookingDto.setItemId(1L);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        bookingService.addNewBooking(userId, bookingDto);

    }

    @Test
    public void approveBookingValidData() {
        Long userId = 1L;
        Long bookingId = 1L;
        Booking booking = new Booking();
        booking.setStatus(Status.WAITING);
        Item item = new Item();
        item.setOwner(new User(userId));
        booking.setItem(item);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        Booking result = bookingService.approveBooking(userId, bookingId, true);

        assertNotNull(result);
        assertEquals(Status.APPROVED, result.getStatus());
    }

    @Test(expected = BookingNotFoundException.class)
    public void approveBookingInvalidBooking() {
        Long userId = 1L;
        Long bookingId = 1L;

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        bookingService.approveBooking(userId, bookingId, true);
    }

    @Test(expected = ValidationException.class)
    public void approveBookingInvalidUser() {
        Long userId = 10L;
        Long bookingId = 1L;
        LocalDateTime now = LocalDateTime.now();
        Booking booking = new Booking(bookingId, now.plusDays(1), now.plusDays(2), new Item(), new User(userId), Status.APPROVED);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        assertNull(bookingService.approveBooking(userId, booking.getId(), true));
    }

    @Test
    public void getAllByBookerIdValidData() {
        Long bookerId = 1L;
        String state = "PAST";
        int from = 0;
        int size = 10;
        int pageNum = 0;


        Booking booking1 = new Booking(1L, LocalDateTime.now().minusDays(2));
        User booker1 = new User();
        booker1.setId(bookerId);
        booking1.setBooker(booker1);

        Item item1 = new Item();
        item1.setId(1L);
        User owner1 = new User();
        owner1.setId(2L);
        item1.setOwner(owner1);
        booking1.setItem(item1);

        Booking booking2 = new Booking(2L, LocalDateTime.now().minusDays(1));
        User booker2 = new User();
        booker2.setId(bookerId);
        booking2.setBooker(booker2);
        Item item2 = new Item();
        item2.setId(2L);
        User owner2 = new User();
        owner2.setId(2L);
        item2.setOwner(owner2);
        booking2.setItem(item2);

        Booking booking3 = new Booking(3L, LocalDateTime.now().plusDays(1));

        User booker3 = new User();
        booker3.setId(bookerId);
        booking3.setBooker(booker3);
        Item item3 = new Item();
        item3.setId(3L);
        User owner3 = new User();
        owner3.setId(3L);
        item3.setOwner(owner3);
        booking3.setItem(item3);

        List<Booking> allBookings = List.of(booking1, booking2, booking3);
        Page<Booking> bookingPage = new PageImpl<>(allBookings);

        when(bookingRepository.findAllBookingsByBookerIdOrderByStartDesc(bookerId, PageRequest.of(pageNum, size)))
                .thenReturn(bookingPage);
        when(userRepository.existsById(bookerId)).thenReturn(true);


        List<Booking> result = bookingService.getAllByBookerId(bookerId, state, from, size);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(booking1));
        assertTrue(result.contains(booking2));
    }

    @Test
    public void getAllByBookerIdInvalidPageParameters() {
        Long bookerId = 1L;
        String state = "ALL";
        int from = -1;
        int size = 10;
        User booker = new User();
        booker.setId(bookerId);
        when(userRepository.existsById(bookerId)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> bookingService.getAllByBookerId(bookerId, state, from, size));
    }

    @Test
    public void getAllByOwnerIdInvalidPageParameters() {
        Long bookerId = 1L;
        String state = "ALL";
        int from = -1;
        int size = 10;
        User booker = new User();
        booker.setId(bookerId);
        when(userRepository.existsById(bookerId)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> bookingService.getAllByOwnerId(bookerId, state, from, size));
    }

    @Test
    public void getAllByOwnerIdValidData() {
        Long ownerId = 1L;
        String state = "PAST";
        int from = 0;
        int size = 10;
        int pageNum = 0;
        LocalDateTime now = LocalDateTime.now();

        User owner = new User();
        owner.setId(ownerId);

        List<Booking> bookingList = new ArrayList<>();
        Booking booking1 = new Booking();
        booking1.setEnd(now.minusDays(1));
        Item item = new Item();
        item.setId(1L);
        item.setOwner(owner);
        booking1.setItem(item);
        bookingList.add(booking1);

        Booking booking2 = new Booking();
        booking2.setEnd(now.plusDays(1));
        Item item2 = new Item();
        item2.setId(2L);
        User owner2 = new User();
        owner.setId(2L);
        item2.setOwner(owner2);
        booking2.setItem(item2);
        bookingList.add(booking2);

        Page<Booking> bookingPage = new PageImpl<>(bookingList);

        when(bookingRepository.findAllBookingsByItemOwnerIdOrderByStartDesc(ownerId, PageRequest.of(pageNum, size)))
                .thenReturn(bookingPage);
        when(userRepository.existsById(ownerId)).thenReturn(true);

        List<Booking> result = bookingService.getAllByOwnerId(ownerId, state, from, size);


        assertNotNull(result);
        assertEquals(1, result.size());
        assertSame(booking1, result.get(0));
    }

    @Test
    public void getBookingExistingBooking() {
        Long userId = 1L;
        Long bookingId = 1L;

        Booking booking = new Booking();
        booking.setId(bookingId);
        User booker = new User();
        booker.setId(userId);
        booking.setBooker(booker);
        Item item = new Item();
        item.setId(1L);
        User owner = new User();
        owner.setId(2L);
        item.setOwner(owner);
        booking.setItem(item);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        Booking result = bookingService.getBooking(userId, bookingId);

        assertNotNull(result);
        assertEquals(bookingId, result.getId());
        assertSame(booking, result);
    }

    @Test
    public void getBookingNonExistingBooking() {
        Long userId = 1L;
        Long bookingId = 1L;

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        assertThrows(BookingNotFoundException.class, () -> bookingService.getBooking(userId, bookingId));
    }

    @Test
    public void getBookingInvalidUser() {
        Long userId = 1L;
        Long bookingId = 1L;

        Booking booking = new Booking();
        booking.setId(bookingId);
        User booker = new User();
        booker.setId(2L);
        booking.setBooker(booker);
        Item item = new Item();
        item.setId(1L);
        User owner = new User();
        owner.setId(2L);
        item.setOwner(owner);
        booking.setItem(item);

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        assertThrows(UserAccessException.class, () -> bookingService.getBooking(userId, bookingId));
    }

    @Test
    public void testFilterBookingsByState_All() {
        List<Booking> bookingList = createBookingList();
        String state = "ALL";

        List<Booking> filteredList = bookingService.filterBookingsByState(bookingList, state);

        Assertions.assertEquals(bookingList, filteredList);
    }

    @Test
    public void testFilterBookingsByState_Past() {
        List<Booking> bookingList = createBookingList();
        String state = "PAST";

        List<Booking> filteredList = bookingService.filterBookingsByState(bookingList, state);

        // Only bookings with end dates before current date/time should be returned
        List<Booking> expectedList = new ArrayList<>();


        expectedList.add(new Booking(LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1), Status.REJECTED));

        Assertions.assertEquals(expectedList, filteredList);
    }

    @Test
    public void testFilterBookingsByState_Current() {
        List<Booking> bookingList = createBookingList();
        String state = "CURRENT";

        List<Booking> filteredList = bookingService.filterBookingsByState(bookingList, state);

        // Only bookings with start dates before current date/time and end dates after current date/time should be returned
        List<Booking> expectedList = new ArrayList<>();
        expectedList.add(new Booking(LocalDateTime.now().minusDays(2), LocalDateTime.now().plusHours(1), Status.WAITING));
        expectedList.add(new Booking(LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(1), Status.APPROVED));


        Assertions.assertEquals(expectedList, filteredList);
    }

    @Test
    public void testFilterBookingsByState_Future() {
        List<Booking> bookingList = createBookingList();
        String state = "FUTURE";

        List<Booking> filteredList = bookingService.filterBookingsByState(bookingList, state);

        // Only bookings with start dates after current date/time should be returned
        List<Booking> expectedList = new ArrayList<>();
        expectedList.add(new Booking(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), Status.CANCELLED));
        expectedList.add(new Booking(LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(3), Status.WAITING));

        Assertions.assertEquals(expectedList, filteredList);
    }

    @Test
    public void testFilterBookingsByState_Waiting() {
        List<Booking> bookingList = createBookingList();
        String state = "WAITING";

        List<Booking> filteredList = bookingService.filterBookingsByState(bookingList, state);

        // Only bookings with status = WAITING should be returned
        List<Booking> expectedList = new ArrayList<>();
        expectedList.add(new Booking(LocalDateTime.now().minusDays(2), LocalDateTime.now().plusHours(1), Status.WAITING));
        expectedList.add(new Booking(LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(3), Status.WAITING));

        Assertions.assertEquals(expectedList, filteredList);
    }

    @Test
    public void testFilterBookingsByState_Approved() {
        List<Booking> bookingList = createBookingList();
        String state = "APPROVED";

        List<Booking> filteredList = bookingService.filterBookingsByState(bookingList, state);

        // Only bookings with status = APPROVED should be returned
        List<Booking> expectedList = new ArrayList<>();
        expectedList.add(new Booking(LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(1), Status.APPROVED));

        Assertions.assertEquals(expectedList, filteredList);
    }

    @Test
    public void testFilterBookingsByState_Rejected() {
        List<Booking> bookingList = createBookingList();
        String state = "REJECTED";

        List<Booking> filteredList = bookingService.filterBookingsByState(bookingList, state);

        // Only bookings with status = REJECTED should be returned
        List<Booking> expectedList = new ArrayList<>();
        expectedList.add(new Booking(LocalDateTime.now().minusDays(1), LocalDateTime.now(), Status.REJECTED));

        Assertions.assertEquals(expectedList, filteredList);
    }

    @Test
    public void testFilterBookingsByState_Cancelled() {
        List<Booking> bookingList = createBookingList();
        String state = "CANCELLED";

        List<Booking> filteredList = bookingService.filterBookingsByState(bookingList, state);

        // Only bookings with status = CANCELLED should be returned
        List<Booking> expectedList = new ArrayList<>();
        expectedList.add(new Booking(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), Status.CANCELLED));

        Assertions.assertEquals(expectedList, filteredList);
    }

    @Test
    public void testFilterBookingsByState_UnknownState() {
        List<Booking> bookingList = createBookingList();
        String state = "UNKNOWN";

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bookingService.filterBookingsByState(bookingList, state);
        });
    }

    private List<Booking> createBookingList() {
        List<Booking> bookingList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        bookingList.add(new Booking(LocalDateTime.now().minusDays(2), LocalDateTime.now().plusHours(1), Status.WAITING));
        bookingList.add(new Booking(LocalDateTime.now().minusHours(1), LocalDateTime.now().plusHours(1), Status.APPROVED));
        bookingList.add(new Booking(LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1), Status.REJECTED));
        bookingList.add(new Booking(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), Status.CANCELLED));
        bookingList.add(new Booking(LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(3), Status.WAITING));

        return bookingList;
    }

}

