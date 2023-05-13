package ru.practicum.shareit.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

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
    public UserDto getUser(@PathVariable("id") int id) {
        log.debug("Users with id " + id + " requested");
        return userService.getUser(id);
    }


    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto) {
        log.debug("New user requested");
        return userService.addUser(userDto);
    }

    @PatchMapping(path = "/{id}")
    public UserDto update(@PathVariable int id, @RequestBody UserDto userDto) {
        userDto.setId(id);
        log.debug("User update requested");
        return userService.update(userDto);

    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") int id) {
        log.debug("User delete requested");
        userService.delete(id);
    }


}


