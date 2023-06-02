package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public Booking addNewBooking(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody @Valid BookingDto bookingDto) {
        log.debug("Create booking requested");
        return bookingService.addNewBooking(userId, bookingDto);
    }

    @PatchMapping("/{id}")
    public Booking approveBooking(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable("id") Long id, @RequestParam Boolean approved) {
        log.debug("Update booking requested");
        return bookingService.approveBooking(userId, id, approved);
    }

    @GetMapping({"/{id}"})
    public Booking getBooking(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable("id") Long id) {
        log.debug("Booking with ID requested");
        return bookingService.getBooking(userId, id);
    }

    @GetMapping
    public List<Booking> getAllByBookerId(@RequestHeader("X-Sharer-User-Id") Long bookerId, @RequestParam(defaultValue = "ALL") String state) {
        log.debug("Bookings by booker requested");
        return bookingService.getAllByBookerId(bookerId, state);
    }

    @GetMapping({"/owner"})
    public List<Booking> getAllByOwnerId(@RequestHeader("X-Sharer-User-Id") Long ownerId, @RequestParam(defaultValue = "ALL") String state) {
        log.debug("Bookings by owner requested");
        return bookingService.getAllByOwnerId(ownerId, state);
    }

}
