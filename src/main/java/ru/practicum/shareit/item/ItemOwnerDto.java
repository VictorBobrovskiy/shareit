package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.Booking;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;


public class ItemOwnerDto extends ItemDto{

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

    public ItemOwnerDto(
        Long id,
        @NotBlank(message = "Name should not be empty") String name,
        @NotBlank(message = "Description should not be empty") String description,
        @NotNull(message = "Available should not be empty") Boolean available,
        Long requestId,
        Booking lastBooking,
        Booking nextBooking
    ) {
        super(id, name, description, available, requestId, lastBooking, nextBooking);
        this.lastBooking = lastBooking;
        this.nextBooking = nextBooking;
    }
}
