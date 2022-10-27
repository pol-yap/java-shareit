package ru.practicum.shareit.user;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UserDto {

    private Long id;

    private String name;

    @Email(message = "User email should be valid address")
    @NotBlank(message = "User email shouldn't bw empty")
    private String email;
}
