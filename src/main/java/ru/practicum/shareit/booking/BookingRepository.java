package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.Item;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {


    List<Booking> findAllBookingsByBookerIdOrderByStartDesc(Long bookerId);

    List<Booking> findAllBookingsByItemOwnerIdOrderByStartDesc(Long ownerId);

    List<Booking> findByItemOrderByStartAsc(Item item);

    List<Booking> findByItemOrderByStartDesc(Item item);


}
