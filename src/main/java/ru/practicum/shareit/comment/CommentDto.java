package ru.practicum.shareit.comment;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CommentDto {

    private Long id;

    @NotNull(message = "Text from should not be empty")
    private String text;

    @NotNull(message = "Item from should not be empty")
    private Long itemId;

    private Long authorId;

    public CommentDto(Long id) {
        this.id = id;
    }
}
