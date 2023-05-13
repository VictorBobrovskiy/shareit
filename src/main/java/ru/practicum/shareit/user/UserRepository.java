package ru.practicum.shareit.user;

import java.util.Collection;

public interface UserRepository {


    Collection<User> findAll();

    User getUser(int id);

    User addUser(User user);

    User update(User user);

    void delete(int id);

}
