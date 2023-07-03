package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemNotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserAccessException;
import ru.practicum.shareit.user.UserNotFoundException;
import ru.practicum.shareit.user.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public Booking addNewBooking(Long userId, BookingDto bookingDto) {
        Booking booking = BookingMapper.toBooking(bookingDto);
        User booker = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Item item = itemRepository.findById(bookingDto.getItemId())
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));
        if (!item.getAvailable()) {
            throw new ValidationException("Item currently unavailable");
        }
        if (booking.getStart().isAfter(booking.getEnd()) || booking.getStart().equals(booking.getEnd())) {
            throw new ValidationException("Minimal period of booking is one day");
        }
        if (Objects.equals(item.getOwner().getId(), userId)) {
            throw new UserAccessException("You cannot book your own item");
        }

        booking.setBooker(booker);
        booking.setItem(item);
        booking.setStatus(Status.WAITING);
        log.debug("New booking by user " + userId + " created");
        return bookingRepository.save(booking);
    }


    @Override
    public Booking approveBooking(Long userId, Long id, Boolean approved) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException("Booking not found"));
        if (Status.APPROVED.equals(booking.getStatus())) {
            throw new ValidationException("Booking has already been approved");
        }
        if (!userId.equals(booking.getItem().getOwner().getId())) {
            throw new UserNotFoundException("Only owners can change status");
        }
        booking.setStatus(approved ? Status.APPROVED : Status.REJECTED);
        log.debug("Booking by user " + userId + " approved");
        return booking;
    }

    @Override
    public Booking getBooking(Long userId, Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(
                () -> new BookingNotFoundException("Booking not found"));

        if (!Objects.equals(userId, booking.getItem().getOwner().getId())
                && !Objects.equals(userId, booking.getBooker().getId())) {
            throw new UserAccessException("You are neither the booker nor the owner of the item to be booked");
        } else {
            log.debug("Booking with id " + id + " found");
            return booking;
        }
    }

    @Override
    public List<Booking> getAllByBookerId(Long bookerId, String state, int from, int size) {
        checkUserExists(bookerId);
        if (size < 1 || from < 0) {
            throw new IllegalArgumentException("Wrong page number");
        }
        int pageNum = from / size;
        Page<Booking> bookingPage = bookingRepository
                .findAllBookingsByBookerIdOrderByStartDesc(bookerId, PageRequest.of(pageNum, size));
        log.debug("Bookings by booker " + bookerId + " found");
        return filterBookingsByState(bookingPage.toList(), state);
    }

    @Override
    public List<Booking> getAllByOwnerId(Long ownerId, String state, int from, int size) {
        checkUserExists(ownerId);
        if (size < 1 || from < 0) {
            throw new IllegalArgumentException("Wrong page number");
        }
        int pageNum = from / size;
        Page<Booking> bookingList = bookingRepository
                .findAllBookingsByItemOwnerIdOrderByStartDesc(ownerId, PageRequest.of(pageNum, size));
        log.debug("Bookings for item owner " + ownerId + " found");
        return filterBookingsByState(bookingList.toList(), state);
    }

    public List<Booking> filterBookingsByState(List<Booking> bookingList, String state) {
        Status status;
        LocalDateTime now = LocalDateTime.now();
        switch (state.toUpperCase()) {
            case "ALL":
                return bookingList;
            case "PAST":
                return bookingList.stream()
                        .filter(b -> b.getEnd().isBefore(now))
                        .collect(Collectors.toList());
            case "CURRENT":
                return bookingList.stream()
                        .filter(b -> b.getStart().isBefore(now) && b.getEnd().isAfter(now))
                        .collect(Collectors.toList());
            case "FUTURE":
                return bookingList.stream()
                        .filter(b -> b.getStart().isAfter(now))
                        .collect(Collectors.toList());
            case "WAITING":
                status = Status.WAITING;
                break;
            case "APPROVED":
                status = Status.APPROVED;
                break;
            case "REJECTED":
                status = Status.REJECTED;
                break;
            case "CANCELLED":
                status = Status.CANCELLED;
                break;
            default:
                throw new IllegalArgumentException("Unknown state: " + state);
        }
        return bookingList.stream()
                .filter(b -> b.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    public void checkUserExists(Long ownerId) {
        if (!userRepository.existsById(ownerId)) {
            throw new UserNotFoundException("User Not Found");
        }
    }


}
