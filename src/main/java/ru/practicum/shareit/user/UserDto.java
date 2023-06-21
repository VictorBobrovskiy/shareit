package ru.practicum.shareit.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank(message = "Name should not be empty")
    private String name;

    @Email
    @NotBlank(message = "Email should not be empty")
    private String email;

    public UserDto(long l, String john) {
    }
}
