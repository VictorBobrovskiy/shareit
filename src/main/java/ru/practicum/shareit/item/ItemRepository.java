package ru.practicum.shareit.item;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT i FROM Item i JOIN FETCH i.owner WHERE i.owner.id = :userId ORDER BY i.id")
    List<Item> findAllItemsByOwnerIdOrderById(@Param("userId") Long userId);

    @Query("SELECT i FROM Item i WHERE LOWER(i.description) LIKE %:text% AND i.available = true")
    List<Item> findAllItemsByDescriptionContainingIgnoreCaseAndAvailableTrue(@Param("text") String text);
}