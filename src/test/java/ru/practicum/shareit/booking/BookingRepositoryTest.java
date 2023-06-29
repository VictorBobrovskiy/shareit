package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
public class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;


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

}