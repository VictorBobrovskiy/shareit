package ru.practicum.shareit.user;

import java.util.List;

public interface UserService {

    List<UserDto> findAll();

    UserDto getUser(Long id);

    UserDto addUser(UserDto userDto);

    UserDto update(UserDto userDto);


    void delete(Long id);

}
