package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.Item;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @NotNull
    private int id;

    @NotNull
    private String name;

    @Email
    @NotNull
    private String email;

    private List<Item> items;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.items = new ArrayList<>();
    }
}
