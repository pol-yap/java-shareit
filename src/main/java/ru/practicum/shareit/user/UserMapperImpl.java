package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper{
    public UserDto toDTO(User user) {
        return UserDto.builder()
                      .id(user.getId())
                      .name(user.getName())
                      .email(user.getEmail())
                      .build();
    }

    public User toModel(UserDto dto) {
        return User.builder()
                   .id(dto.getId())
                   .name(dto.getName())
                   .email(dto.getEmail())
                   .build();
    }
}
