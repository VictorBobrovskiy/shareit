package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.Item;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {


    List<Booking> findAllBookingsByBookerIdOrderByStartDesc(Long bookerId);

    List<Booking> findAllBookingsByBookerIdAndStatusOrderByStartDesc(Long bookerId, String status);

    List<Booking> findAllBookingsByItemOwnerIdAndStatusOrderByStartDesc(Long ownerId, String status);

    List<Booking> findAllBookingsByItemOwnerIdOrderByStartDesc(Long ownerId);
    
    
    //@Query("select * from booking where bookerId = ")
    List<Booking> findAllCurrentBookingsByItemOwnerIdOrderByStartDesc(Long ownerId);

    List<Booking> findAllFutureBookingsByItemOwnerIdOrderByStartDesc(Long ownerId);

    List<Booking> findAllPastBookingsByItemOwnerIdOrderByStartDesc(Long ownerId);

    List<Booking> findAllPastBookingsByBookerIdOrderByStartDesc(Long bookerId);

    List<Booking> findAllCurrentBookingsByBookerIdOrderByStartDesc(Long bookerId);

    List<Booking> findAllFutureBookingsByBookerIdOrderByStartDesc(Long bookerId);

    List<Booking> findByItemOrderByStartAsc(Item item);

    List<Booking> findByItemOrderByStartDesc(Item item);
}
