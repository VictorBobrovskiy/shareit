package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Date from should not be empty")
    @Column(name = "start")
    private LocalDate from;

    @NotNull(message = "Date to should not be empty")
    @Column(name = "finish")
    private LocalDate to;

    @NotNull(message = "Item from should not be empty")
    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Item item;

    @NotNull(message = "User from should not be empty")
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private User booker;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;

    public Booking(LocalDate from, LocalDate to, Item item, User booker) {
        this.from = from;
        this.to = to;
        this.item = item;
        this.booker = booker;
        state = State.WAITING;
    }

}
