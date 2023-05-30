package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "item_request")
@Data
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

    @NotNull(message = "Date to should not be empty")
    @Column(name = "created")
    private Date created;

    public ItemRequest(Long id) {
        this.id = id;
    }

}
