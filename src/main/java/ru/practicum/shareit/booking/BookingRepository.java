package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.Item;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b JOIN FETCH b.booker JOIN FETCH b.item WHERE b.booker.id = :bookerId ORDER BY b.start DESC")
    List<Booking> findAllBookingsByBookerIdOrderByStartDesc(@Param("bookerId") Long bookerId);

    @Query("SELECT b FROM Booking b JOIN FETCH b.booker JOIN FETCH b.item i WHERE i.owner.id = :ownerId ORDER BY b.start DESC")
    List<Booking> findAllBookingsByItemOwnerIdOrderByStartDesc(@Param("ownerId") Long ownerId);


    @Query(value = "SELECT * FROM booking b " +
            "JOIN users u ON b.user_id = u.id " +
            "JOIN item i ON b.item_id = i.id " +
            "WHERE b.item_id = :itemId " +
            "  AND b.start > :now " +
            "  AND b.state = :approved " +
            "ORDER BY b.start ASC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Booking> findFirstByItemAndStartAfterAndStatusOrderByStartAsc(
            @Param("itemId") Long itemId,
            @Param("now") LocalDateTime now,
            @Param("approved") String approved
    );

    @Query(value = "SELECT * FROM booking b " +
            "JOIN users u ON b.user_id = u.id " +
            "JOIN item i ON b.item_id = i.id " +
            "WHERE b.item_id = :itemId " +
            "  AND b.start < :now " +
            "  AND b.state = :approved " +
            "ORDER BY b.start DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Booking> findFirstByItemAndStartBeforeAndStatusOrderByStartDesc(
            @Param("itemId") Long itemId,
            @Param("now") LocalDateTime now,
            @Param("approved") String approved
    );
}




