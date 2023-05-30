package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.booking.Booking;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class ItemDto {

    private Long id;

    @NotBlank(message = "Name should not be empty")
    private String name;

    @NotBlank(message = "Description should not be empty")
    private String description;

    @NotNull(message = "Available should not be empty")
    private Boolean available;

    private Long requestId;

    private Booking lastBooking;

    private Booking nextBooking;

}
