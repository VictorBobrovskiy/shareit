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

    private Long itemId;

    private String authorName;

    public CommentDto(Long id) {
        this.id = id;
    }
}
