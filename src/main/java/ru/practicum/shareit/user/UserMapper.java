package ru.practicum.shareit.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final UserRepository userRepository;

    public User mapDtoToUser(UserDto userDto) {
        int id = userDto.getId();
        String name = userDto.getName();
        String email = userDto.getEmail();
        if (name == null) {
            name = userRepository.getUser(id).getName();
        } else if (email == null) {
            email = userRepository.getUser(id).getEmail();
        }
        return new User(id, name, email);
    }

    public UserDto mapUserToDto(User user) {
        int id = user.getId();
        String name = user.getName();
        String email = user.getEmail();
        return new UserDto(id, name, email);
    }
}
