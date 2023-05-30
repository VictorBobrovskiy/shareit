package ru.practicum.shareit.booking;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class BookingDto {

    private Long id;

    @NotNull(message = "Date from should not be empty")
    @Future(message = "Date should be in future")
    @DateTimeFormat(pattern = "YYYY-MM-DDTHH:mm:ss")
    private LocalDateTime start;

    @Future(message = "Date should be in future")
    @NotNull(message = "Date to should not be empty")
    @DateTimeFormat(pattern = "YYYY-MM-DDTHH:mm:ss")
    private LocalDateTime end;

    @NotNull(message = "Item from should not be empty")
    private Long itemId;

    private Long bookerId;

    private String itemName;

    private String status;

    public BookingDto(Long id) {
        this.id = id;
    }
}
