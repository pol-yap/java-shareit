package ru.practicum.shareit.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.user.User;

@Getter
@Setter
@NoArgsConstructor
public class UserDtoBrief {
    private Long id;

    public UserDtoBrief(User user) {
        setId(user.getId());
    }
}