package ru.practicum.shareit.booking;

import java.util.List;

public interface BookingService {
    Booking addNewBooking(Long userId, BookingDto bookingDto);

    Booking approveBooking(Long userId, Long id, Boolean available);

    Booking getBooking(Long userId, Long id);

    List<Booking> getAllByBookerId(Long ownerId, String status, int from, int size);

    List<Booking> getAllByOwnerId(Long ownerId, String state, int from, int size);
}
