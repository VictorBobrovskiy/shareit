package ru.practicum.shareit.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Name should not be empty")
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private User owner;

    @NotBlank(message = "Name should not be empty")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Description should not be empty")
    @Column(name = "description")
    private String description;

    @NotNull(message = "Available should not be empty")
    @Column(name = "available")
    private Boolean available;

    @OneToOne
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private ItemRequest request;

    @OneToMany(mappedBy="item")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Set<Booking> bookings;

    public Item(Long id, String name, String description, Boolean available, ItemRequest request) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.request = request;
    }
}
