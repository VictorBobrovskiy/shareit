package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User mapDtoToUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getEmail());
    }

    public static UserDto mapUserToDto(User user) {
        Long id = user.getId();
        String name = user.getName();
        String email = user.getEmail();
        return new UserDto(id, name, email);
    }
}
