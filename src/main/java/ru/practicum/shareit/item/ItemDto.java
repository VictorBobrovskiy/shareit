package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * TODO Sprint add-controllers.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

    private int id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private Boolean available;

    private int requestId;
}
