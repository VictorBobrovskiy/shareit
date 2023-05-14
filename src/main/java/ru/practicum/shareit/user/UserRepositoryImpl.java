package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.ExistsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public List<User> findAll() {
        log.debug("All users sent");
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUser(int id) {
        if (users.get(id) == null) {
            throw new UserNotFoundException("User not found");
        }
        log.debug("User with id " + id + " sent");
        return users.get(id);
    }

    @Override
    public User addUser(User user) {
        users.put(user.getId(), user);
        log.debug("User with id " + user.getId() + " created");
        return user;
    }

    @Override
    public User update(User user) {
        User userTemp = users.remove(user.getId());
        if (findAll().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
            users.put(user.getId(), userTemp);
            throw new ExistsException("This email belongs to another user");
        }
        users.put(user.getId(), user);
        log.debug("User with id " + user.getId() + " updated");
        return getUser(user.getId());
    }

    @Override
    public void delete(int id) {
        log.debug("User with id " + id + " deleted");
        users.remove(id);
    }
}
