package ru.practicum.shareit.dataSet;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoCreate;

@Getter
@Setter
public class UserTestSet {
    private User user;
    private UserDtoCreate dtoCreate;
    private UserDto dto;

    public UserTestSet() {
        user = User.builder()
                   .name("User1")
                   .email("user1@server.io")
                   .build();

        dtoCreate = UserDtoCreate.builder()
                                 .name(user.getName())
                                 .email(user.getEmail())
                                 .build();
    }
}
