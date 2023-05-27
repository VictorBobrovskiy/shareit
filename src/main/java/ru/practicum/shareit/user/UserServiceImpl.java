package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.ExistsException;
import ru.practicum.shareit.exceptions.ValidationException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(UserMapper::mapUserToDto).collect(Collectors.toList());
    }

    public UserDto getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserMapper.mapUserToDto(user);
    }


    public UserDto addUser(UserDto userDto) {
        User user = UserMapper.mapDtoToUser(validateUser(userDto));
        return UserMapper.mapUserToDto(userRepository.save(user));
    }

    public UserDto update(UserDto userDto) {
        getUser(userDto.getId());
        return UserMapper.mapUserToDto(userRepository.save(UserMapper.mapDtoToUser(userDto)));
    }


    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    private UserDto validateUser(UserDto userDto) {
//        if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
//            throw new ValidationException("Email should not be empty");
//        } else if (userRepository.findAll().stream().anyMatch(user -> user.getEmail().equals(userDto.getEmail()))) {
//            throw new ExistsException("This email belongs to another user");
//        } else if (!userDto.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
//            throw new ValidationException("Email should be valid");
//        } else {
            return userDto;

    }

}
