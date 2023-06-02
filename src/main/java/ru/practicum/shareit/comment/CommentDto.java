package ru.practicum.shareit.comment;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentDto {

    private Long id;

    @NotNull(message = "Text from should not be empty")
    private String text;

    private Long itemId;

    private String authorName;

    private LocalDateTime created;


    public CommentDto(Long id) {
        this.id = id;
    }
}
