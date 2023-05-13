package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.ExistsException;
import ru.practicum.shareit.exceptions.ValidationException;
import ru.practicum.shareit.item.ItemMapper;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

   private final UserRepository userRepository;

    private final UserMapper userMapper;
   private int userId = 0;


    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(userMapper::mapUserToDto).collect(Collectors.toList());
    }

    public UserDto getUser(int id) {
        return userMapper.mapUserToDto(userRepository.getUser(id));
    }


    public UserDto addUser(UserDto userDto) {
        User user = userMapper.mapDtoToUser(validateUser(userDto));
        user.setId(++userId);
        return userMapper.mapUserToDto(userRepository.addUser(user));
    }

    public UserDto update(UserDto userDto) {
        userRepository.getUser(userDto.getId());
        return userMapper.mapUserToDto(userRepository.update(userMapper.mapDtoToUser(userDto)));
    }


    public void delete(int id) {
        userRepository.delete(id);
    }

    private UserDto validateUser (UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
            throw new ValidationException("Email should not be empty");
        } else if (userRepository.findAll().stream().anyMatch(user -> user.getEmail().equals(userDto.getEmail()))) {
            throw new ExistsException("This email belongs to another user");
        } else if (!userDto.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new ValidationException("Email should be valid");
        } else {
            return userDto;
        }
    }

}
