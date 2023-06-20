package ru.practicum.shareit.ItemRequest;
import org.springframework.stereotype.Component;

public class ItemRequestMapper {

    public static ItemRequest toEntity(ItemRequestDto dto) {
        ItemRequest entity = new ItemRequest();
        entity.setId(dto.getId());
        entity.setDescription(dto.getDescription());
        entity.setCreated(dto.getCreated());
        return entity;
    }

    public static ItemRequestDto toDto(ItemRequest entity) {
        ItemRequestDto dto = new ItemRequestDto();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setCreated(entity.getCreated());
        return dto;
    }
}
