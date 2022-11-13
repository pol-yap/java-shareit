package ru.practicum.shareit.user.dto;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.User;

@Component
public class UserMapperImpl implements UserMapper {
    public UserDto toDto(User user) {
        return UserDto.builder()
                      .id(user.getId())
                      .name(user.getName())
                      .email(user.getEmail())
                      .build();
    }

    public User fromDto(UserDto dto) {
        return User.builder()
                   .id(dto.getId())
                   .name(dto.getName())
                   .email(dto.getEmail())
                   .build();
    }

    public User fromDtoCreate(UserDtoCreate dto) {
        return User.builder()
                   .name(dto.getName())
                   .email(dto.getEmail())
                   .build();
    }
}
