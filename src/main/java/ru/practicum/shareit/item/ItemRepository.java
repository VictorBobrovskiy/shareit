package ru.practicum.shareit.item;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.User;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
//    List<Item> findAllByUserId (Long userId);
//    void deleteByUserIdAndItemId(Long userId, Long itemId);
//    List<Item> findItemByName(String text);
}