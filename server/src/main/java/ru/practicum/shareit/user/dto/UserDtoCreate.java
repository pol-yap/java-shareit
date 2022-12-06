package ru.practicum.shareit.user.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class UserDtoCreate {

    @NotNull(message = "User name shouldn't be empty")
    @NotBlank(message = "User name shouldn't be empty")
    private String name;

    @NotNull(message = "User email shouldn't be empty")
    @NotBlank(message = "User email shouldn't be empty")
    @Email(message = "User email should be valid address")
    private String email;
}