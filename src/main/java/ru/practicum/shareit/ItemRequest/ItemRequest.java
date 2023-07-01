package ru.practicum.shareit.ItemRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "item_request")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Description should not be empty")
    @Column(name = "description")
    private String description;

    @NotNull(message = "User from should not be empty")
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User requester;

    @NotNull(message = "Date should not be empty")
    @Column(name = "created")
    private LocalDateTime created;

    public ItemRequest(Long id) {
        this.id = id;
    }

    public ItemRequest(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemRequest)) return false;
        ItemRequest that = (ItemRequest) o;
        return Objects.equals(description, that.description) && Objects.equals(requester, that.requester) && Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, requester, created);
    }

    @Override
    public String toString() {
        return "ItemRequest{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", created=" + created +
                '}';
    }
}
