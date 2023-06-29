package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    @Test
    public void testMapDtoToUser() {
        Long id = 1L;
        String name = "John Doe";
        String email = "johndoe@example.com";

        UserDto userDto = new UserDto(id, name, email);

        User user = UserMapper.mapDtoToUser(userDto);

        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
    }

    @Test
    public void testMapUserToDto() {
        Long id = 1L;
        String name = "John Doe";
        String email = "johndoe@example.com";

        User user = new User(id, name, email);

        UserDto userDto = UserMapper.mapUserToDto(user);

        assertEquals(id, userDto.getId());
        assertEquals(name, userDto.getName());
        assertEquals(email, userDto.getEmail());
    }
}
