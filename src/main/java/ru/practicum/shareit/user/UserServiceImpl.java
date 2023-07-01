package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public List<UserDto> findAll() {
        log.debug("All users found");
        return userRepository.findAll().stream().map(UserMapper::mapUserToDto).collect(Collectors.toList());
    }

    public UserDto getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        log.debug("User with id " + id + " delivered");
        return UserMapper.mapUserToDto(user);
    }

    public UserDto addUser(UserDto userDto) {
        User user = UserMapper.mapDtoToUser(userDto);
        log.debug("New user added");
        return UserMapper.mapUserToDto(userRepository.save(user));
    }

    public UserDto update(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        log.debug("User with id " + userDto.getId() + " updated");
        return UserMapper.mapUserToDto(userRepository.save(user));
    }

    public void delete(Long id) {
        log.debug("User with id " + id + " deleted");
        userRepository.deleteById(id);
    }

}
