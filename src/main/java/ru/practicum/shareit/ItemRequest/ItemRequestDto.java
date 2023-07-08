package ru.practicum.shareit.ItemRequest;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.ItemDto;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDto {

    private Long id;

    @NotBlank(message = "Description should not be empty")
    private String description;

    private LocalDateTime created;

    private List<ItemDto> items;

}
