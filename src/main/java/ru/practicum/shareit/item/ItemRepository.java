package ru.practicum.shareit.item;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllItemsByOwnerIdOrderById(Long userId);

    List<Item> findAllItemsByDescriptionContainingIgnoreCaseAndAvailableTrue(String text);
}