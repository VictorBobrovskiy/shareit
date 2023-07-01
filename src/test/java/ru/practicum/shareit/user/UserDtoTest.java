package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDtoTest {

    @Test
    public void testUserDto() {
        Long id = 1L;
        String name = "John Doe";
        String email = "johndoe@example.com";

        UserDto userDto = new UserDto(id, name, email);

        assertEquals(id, userDto.getId());
        assertEquals(name, userDto.getName());
        assertEquals(email, userDto.getEmail());
    }

    @Test
    public void testUserDtoWithNoArgsConstructor() {
        UserDto userDto = new UserDto();

        assertEquals(null, userDto.getId());
        assertEquals(null, userDto.getName());
        assertEquals(null, userDto.getEmail());
    }

    @Test
    public void testUserDtoWithIdAndNameConstructor() {
        Long id = 1L;
        String name = "John Doe";

        UserDto userDto = new UserDto(id, name);

        assertEquals(id, userDto.getId());
        assertEquals(name, userDto.getName());
        assertEquals(null, userDto.getEmail());
    }

    @Test
    public void testUserDtoWithInvalidConstructor() {
        long l = 1L;
        String name = "John";

        UserDto userDto = new UserDto(l, name);

        assertEquals(l, userDto.getId());
        assertEquals(name, userDto.getName());
        assertEquals(null, userDto.getEmail());
    }
}
