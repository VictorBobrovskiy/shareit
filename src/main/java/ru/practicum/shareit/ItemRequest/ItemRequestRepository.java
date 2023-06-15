package ru.practicum.shareit.ItemRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {

    @Query("SELECT ir FROM ItemRequest ir JOIN FETCH ir.requester WHERE ir.requester.id = :userId ORDER BY ir.created")
    List<ItemRequest> findAllByRequesterId(Long userId);

    @Query("SELECT ir FROM ItemRequest ir ORDER BY ir.created")
    Page<ItemRequest> findAllOrderByCreated(Pageable pageable);
}
