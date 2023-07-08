package ru.practicum.shareit.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;

    @NotNull(message = "Text from should not be empty")
    private String text;

    private Long itemId;

    private String authorName;

    private LocalDateTime created;


    public CommentDto(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    public CommentDto(String text) {
        this.text = text;
    }
}
