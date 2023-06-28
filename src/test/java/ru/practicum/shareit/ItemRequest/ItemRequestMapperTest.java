package ru.practicum.shareit.ItemRequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class ItemRequestMapperTest {

    @Test
    public void testToEntity() {
        // Prepare test data
        Long requestId = 1L;
        String description = "Request Description";
        LocalDateTime created = LocalDateTime.now();

        ItemRequestDto dto = new ItemRequestDto();
        dto.setId(requestId);
        dto.setDescription(description);
        dto.setCreated(created);

        // Convert DTO to entity
        ItemRequest entity = ItemRequestMapper.toEntity(dto);

        // Assertions
        Assertions.assertEquals(requestId, entity.getId());
        Assertions.assertEquals(description, entity.getDescription());
        Assertions.assertEquals(created, entity.getCreated());
    }

    @Test
    public void testToDto() {
        // Prepare test data
        Long requestId = 1L;
        String description = "Request Description";
        LocalDateTime created = LocalDateTime.now();

        ItemRequest entity = new ItemRequest();
        entity.setId(requestId);
        entity.setDescription(description);
        entity.setCreated(created);

        // Convert entity to DTO
        ItemRequestDto dto = ItemRequestMapper.toDto(entity);

        // Assertions
        Assertions.assertEquals(requestId, dto.getId());
        Assertions.assertEquals(description, dto.getDescription());
        Assertions.assertEquals(created, dto.getCreated());
    }
}
