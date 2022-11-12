package ru.practicum.shareit.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserDto {

    private Long id;

    @NotBlank(message = "User name shouldn't be empty")
    private String name;

    @Email(message = "User email should be valid address")
    @NotBlank(message = "User email shouldn't be empty")
    private String email;
}
