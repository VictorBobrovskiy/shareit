package ru.practicum.shareit.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Text should not be empty")
    @Column(name = "text")
    private String text;

    @NotNull(message = "Item should not be null")
    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    @NotNull(message = "Author should not be null")
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User author;

    @NotNull(message = "Created to should not be empty")
    @Column(name = "created")
    @DateTimeFormat(pattern = "YYYY-MM-DDTHH:mm:ss")
    private LocalDateTime created;

    public Comment(Long id, String text, Item item, User author) {
        this.id = id;
        this.text = text;
        this.item = item;
        this.author = author;
        this.created = LocalDateTime.now();
    }

    public Comment(String text, Item item, User author) {
        this.text = text;
        this.item = item;
        this.author = author;
        this.created = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", created=" + created +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return Objects.equals(text, comment.text) && Objects.equals(item, comment.item) && Objects.equals(author, comment.author) && Objects.equals(created, comment.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, item, author, created);
    }
}
