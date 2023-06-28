package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.UserRepository;

@DataJpaTest
public class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindAllBookingsByBookerIdOrderByStartDesc() {
        // Prepare test data
        Long bookerId = 1L;

        // Perform the query
        Pageable pageable = PageRequest.of(0, 10);
        Page<Booking> bookings = bookingRepository.findAllBookingsByBookerIdOrderByStartDesc(bookerId, pageable);

        // Assertions
        Assertions.assertNotNull(bookings);
        Assertions.assertTrue(bookings.isEmpty()); // Modify this assertion based on your actual data setup
    }

    @Test
    public void testFindAllBookingsByItemOwnerIdOrderByStartDesc() {
        // Prepare test data
        Long ownerId = 1L;

        // Perform the query
        Pageable pageable = PageRequest.of(0, 10);
        Page<Booking> bookings = bookingRepository.findAllBookingsByItemOwnerIdOrderByStartDesc(ownerId, pageable);

        // Assertions
        Assertions.assertNotNull(bookings);
        Assertions.assertTrue(bookings.isEmpty()); // Modify this assertion based on your actual data setup
    }

//
//    @Test
//    public void testFindFirstByItemAndStartAfterAndStatusOrderByStartAsc() {
//
//        Long ownerId = 3L;
//        User owner = new User(ownerId, "Owner", "email@user.com");
//        userRepository.save(owner);
//        Long itemId = 2L;
//        Item item = new Item(itemId, owner, "Item", "Description", true);
//        itemRepository.save(item);
//        Long bookerId = 4L;
//        User booker = new User(bookerId, "Booker", "mail@go.com");
//        userRepository.save(booker);
//        LocalDateTime now = LocalDateTime.now();
//        String approved = "APPROVED";
//
//        Booking booking1 = new Booking();
//        booking1.setItem(item);
//        booking1.setStart(now.minusMinutes(30));
//        booking1.setEnd(now);
//        booking1.setStatus(Status.APPROVED);
//        booking1.setBooker(booker);
//        bookingRepository.save(booking1);
//
//        Booking booking2 = new Booking();
//        booking2.setItem(item);
//        booking2.setStart(now.plusMinutes(10));
//        booking2.setEnd(now.plusMinutes(100));
//        booking2.setStatus(Status.APPROVED);
//        booking2.setBooker(booker);
//        bookingRepository.save(booking2);
//
//        // Perform the query
//        Optional<Booking> booking = bookingRepository.findFirstByItemAndStartAfterAndStatusOrderByStartAsc(itemId, now, approved);
//
//        // Assertions
//        Assertions.assertTrue(booking.isPresent());
//        Assertions.assertEquals(booking2.getId(), booking.get().getId());
//        // Add more assertions based on your actual data setup and expected results
//    }
//
//    @Test
//    public void testFindFirstByItemAndStartBeforeAndStatusOrderByStartDesc() {
//
//        Long ownerId = 1L;
//        User owner = new User(ownerId, "Owner", "email@user.com");
//        userRepository.save(owner);
//        Long itemId = 1L;
//        Item item = new Item(itemId, owner, "Item", "Description", true);
//        itemRepository.save(item);
//        Long bookerId = 2L;
//        User booker = new User(bookerId, "Booker", "mail@go.com");
//        userRepository.save(booker);
//        LocalDateTime now = LocalDateTime.now();
//        String approved = "APPROVED";
//
//        Booking booking1 = new Booking();
//        booking1.setItem(item);
//        booking1.setStart(now.minusMinutes(200));
//        booking1.setEnd(now.minusMinutes(100));
//
//        booking1.setStatus(Status.APPROVED);
//        booking1.setBooker(booker);
//        bookingRepository.save(booking1);
//
//        Booking booking2 = new Booking();
//        booking2.setItem(item);
//        booking2.setStart(now.minusMinutes(100));
//        booking2.setEnd(now.minusMinutes(10));
//        booking2.setStatus(Status.APPROVED);
//        booking2.setBooker(booker);
//        bookingRepository.save(booking2);
//
//        // Perform the query
//        Optional<Booking> booking = bookingRepository.findFirstByItemAndStartBeforeAndStatusOrderByStartDesc(itemId, now, approved);
//
//        // Assertions
//        Assertions.assertTrue(booking.isPresent());
//        Assertions.assertEquals(booking2.getId(), booking.get().getId());
//        // Add more assertions based on your actual data setup and expected results
//    }
//

}