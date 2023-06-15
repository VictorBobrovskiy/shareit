package ru.practicum.shareit.ItemRequest;


import lombok.Data;
import ru.practicum.shareit.item.ItemDto;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ItemRequestDto {

    private Long id;

    @NotBlank(message = "Description should not be empty")
    private String description;

    private LocalDateTime created;

    private List<ItemDto> items;

}
