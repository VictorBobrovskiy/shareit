package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.request.ItemRequest;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

    private Long id;

    @NotBlank(message = "Name should not be empty")
    private String name;

    @NotBlank(message = "Description should not be empty")
    private String description;

    @NotBlank(message = "Available should not be empty")
    private Boolean available;

    private Long requestId;

}
