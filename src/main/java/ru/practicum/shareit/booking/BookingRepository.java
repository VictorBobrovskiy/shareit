package ru.practicum.shareit.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Page<Booking> findAllBookingsByBookerIdOrderByStartDesc(@Param("bookerId") Long bookerId, Pageable pageable);


    List<Booking> findAllBookingsByBookerIdOrderByStartDesc(@Param("bookerId") Long bookerId);


    Page<Booking> findAllBookingsByItemOwnerIdOrderByStartDesc(@Param("ownerId") Long ownerId, Pageable pageable);


    @Query(value = "SELECT * FROM booking b " +
            "JOIN users u ON b.user_id = u.id " +
            "JOIN item i ON b.item_id = i.id " +
            "WHERE b.item_id = :itemId " +
            "  AND b.start > :now " +
            "  AND b.status = :approved " +
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
            "  AND b.status = :approved " +
            "ORDER BY b.start DESC " +
            "LIMIT 1", nativeQuery = true)
    Optional<Booking> findFirstByItemAndStartBeforeAndStatusOrderByStartDesc(
            @Param("itemId") Long itemId,
            @Param("now") LocalDateTime now,
            @Param("approved") String approved
    );
}




