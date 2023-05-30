package ru.practicum.shareit.item;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.User;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllItemsByOwnerId (Long userId);
    List<Item> findAllItemsByDescriptionContainingIgnoreCaseAndAvailableTrue(String text);
}