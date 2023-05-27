package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.common.aliasing.qual.Unique;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;



@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @Email
    @Column(name = "email", unique = true)
    private String email;
}

//    public void addItem(Item item) {
//        if (items == null) items = new HashSet<>();
//        items.add(item);
//        item.setOwner(this);
//    }

