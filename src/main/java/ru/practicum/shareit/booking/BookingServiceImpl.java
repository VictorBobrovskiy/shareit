package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemNotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.UserAccessException;
import ru.practicum.shareit.user.UserNotFoundException;
import ru.practicum.shareit.user.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public Booking addNewBooking(Long userId, BookingDto bookingDto) {
        Booking booking = BookingMapper.toBooking(bookingDto);
        booking.setBooker(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found")));
        Item item = itemRepository.findById(bookingDto.getItemId()).orElseThrow(() -> new ItemNotFoundException("Item not found"));
        if (!item.getAvailable()) {
            throw new ValidationException("Item currently unavailable");
        }
        if (booking.getStart().isAfter(booking.getEnd()) || booking.getStart().equals(booking.getEnd()))  {
            throw new ValidationException("Minimal period of booking is one day");
        }
        if (item.getOwner().getId() ==  userId) {
            throw new UserAccessException("You cannot book your own item");
        } else {
            booking.setItem(item);
            booking.setStatus("WAITING");
            return bookingRepository.save(booking);
        }
    }

    @Override
    public Booking approveBooking(Long userId, Long id, Boolean approved) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException("Booking not found"));
        if ("APPROVED".equals(booking.getStatus())) {
            throw new ValidationException("Booking has already been approved");
        }
        if (!userId.equals(booking.getItem().getOwner().getId())) {
            throw new UserNotFoundException("Only owners can change status");
        } else {
            booking.setStatus(approved ? "APPROVED" : "REJECTED");
            return booking;
        }
    }

    @Override
    public Booking getBooking(Long userId, Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException("Booking not found"));
        if (!Objects.equals(userId, booking.getItem().getOwner().getId()) && !Objects.equals(userId, booking.getBooker().getId())) {
            throw new UserAccessException("You are neither the booker nor the owner of the item to be booked");
        } else {
            return booking;
        }
    }

    @Override
    public List<Booking> getAllByBookerId(Long bookerId, String state) {
        checkUserExists(bookerId);
        String status;
        switch (state.toUpperCase()) {
            case "ALL":
                return bookingRepository.findAllBookingsByBookerIdOrderByStartDesc(bookerId);
            case "PAST":
                return bookingRepository.findAllPastBookingsByBookerIdOrderByStartDesc(bookerId);
            case "CURRENT":
                return bookingRepository.findAllCurrentBookingsByBookerIdOrderByStartDesc(bookerId);
            case "FUTURE":
                return bookingRepository.findAllFutureBookingsByBookerIdOrderByStartDesc(bookerId);
            case "WAITING":
                status = "WAITING";
                break;
            case "APPROVED":
                status = "APPROVED";
                break;
            case "REJECTED":
                status = "REJECTED";
                break;
            case "CANCELLED":
                status = "CANCELLED";
                break;
            default:
                throw new IllegalArgumentException("Unknown state: " + state);
        }

        return bookingRepository.findAllBookingsByBookerIdAndStatusOrderByStartDesc(bookerId, status);
    }

    @Override
    public List<Booking> getAllByOwnerId(Long ownerId, String state) {
        checkUserExists(ownerId);
        String status;
        switch (state.toUpperCase()) {
            case "ALL":
                return bookingRepository.findAllBookingsByItemOwnerIdOrderByStartDesc(ownerId);
            case "PAST":
                return bookingRepository.findAllPastBookingsByItemOwnerIdOrderByStartDesc(ownerId);
            case "CURRENT":
                return bookingRepository.findAllCurrentBookingsByItemOwnerIdOrderByStartDesc(ownerId);
            case "FUTURE":
                return bookingRepository.findAllFutureBookingsByItemOwnerIdOrderByStartDesc(ownerId);
            case "WAITING":
                status = "WAITING";
                break;
            case "APPROVED":
                status = "APPROVED";
                break;
            case "REJECTED":
                status = "REJECTED";
                break;
            case "CANCELLED":
                status = "CANCELLED";
                break;
            default:
                throw new IllegalArgumentException("Unknown state: " + state);
        }
        return bookingRepository.findAllBookingsByItemOwnerIdAndStatusOrderByStartDesc(ownerId, status);
    }

    private void checkUserExists(Long ownerId) {
        if (!userRepository.existsById(ownerId)) {
            throw new UserNotFoundException("User Not Found");
        }
    }



}
