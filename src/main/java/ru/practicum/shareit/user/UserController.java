package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> findAll() {
        log.debug("All users requested");
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable("id") Long id) {
        log.debug("Users with id " + id + " requested");
        return userService.getUser(id);
    }


    @PostMapping
    public UserDto addUser(@RequestBody @Valid UserDto userDto) {
        log.debug("New user requested");
        return userService.addUser(userDto);
    }

    @PatchMapping(path = "/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody UserDto userDto) {
        userDto.setId(id);
        log.debug("User update requested");
        return userService.update(userDto);

    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        log.debug("User delete requested");
        userService.delete(id);
    }


}


