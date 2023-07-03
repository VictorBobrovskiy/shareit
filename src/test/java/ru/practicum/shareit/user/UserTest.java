package ru.practicum.shareit.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void testConstructorWithId() {
        Long id = 1L;
        User user = new User(id);
        Assertions.assertEquals(id, user.getId());
    }

    @Test
    public void testConstructorWithName() {
        String name = "John";
        User user = new User(name);
        Assertions.assertEquals(name, user.getName());
    }

    @Test
    public void testConstructorWithNameAndEmail() {
        String name = "John";
        String email = "john@example.com";
        User user = new User(name, email);
        Assertions.assertEquals(name, user.getName());
        Assertions.assertEquals(email, user.getEmail());
    }

    @Test
    public void testEqualsAndHashCode() {
        User user1 = new User("John", "john@example.com");
        User user2 = new User("John", "john@example.com");
        Assertions.assertEquals(user1, user2);
        Assertions.assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testToString() {
        Long id = 1L;
        String name = "John";
        String email = "john@example.com";
        User user = new User(id, name, email);
        String expectedToString = "User{id=1, name='John', email='john@example.com'}";
        Assertions.assertEquals(expectedToString, user.toString());
    }
}
