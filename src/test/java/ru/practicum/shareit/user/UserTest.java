package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    public void testUser() {
        Long id = 1L;
        String name = "John Doe";
        String email = "johndoe@example.com";

        User user = new User(id, name, email);

        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
    }

    @Test
    public void testUserWithIdConstructor() {
        Long id = 1L;

        User user = new User(id);

        assertEquals(id, user.getId());
    }

    @Test
    public void testUserWithNameConstructor() {
        String name = "John Doe";

        User user = new User(name);

        assertEquals(name, user.getName());
    }

    @Test
    public void testUserWithUsernameAndEmailConstructor() {
        String username = "johndoe";
        String email = "johndoe@example.com";

        User user = new User(username, email);

        assertEquals(username, user.getName());
        assertEquals(email, user.getEmail());
    }
}
