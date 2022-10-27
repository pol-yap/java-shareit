package ru.practicum.shareit.user;

public interface UserMapper {

    UserDto toDTO(User user);

    User toModel(UserDto userDto);
}