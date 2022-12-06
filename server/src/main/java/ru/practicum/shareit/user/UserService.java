package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.errors.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoCreate;
import ru.practicum.shareit.user.dto.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public UserDto create(UserDtoCreate userDto) {
        User user = mapper.fromDtoCreate(userDto);
        repository.saveAndFlush(user);
        log.trace("Created user: " + user);

        return mapper.toDto(user);
    }

    public UserDto update(Long userId, UserDto userDto) {
        User user = findUserById(userId);
        updateUserData(user, mapper.fromDto(userDto));
        repository.saveAndFlush(user);
        log.trace("Updated user: " + user);

        return mapper.toDto(user);
    }

    public UserDto findById(Long userId) {
        return mapper.toDto(findUserById(userId));
    }

    public List<UserDto> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toDto)
                         .collect(Collectors.toList());
    }

    public void deleteById(Long userId) {
        repository.deleteById(userId);
        log.trace("User id=" + userId + " deleted");
    }

    public void throwIfNoUser(Long userId) {
        if (!repository.existsById(userId)) {
            throw new NotFoundException(userId, "user");
        }
    }

    private User findUserById(Long userId) {
        return repository.findById(userId)
                         .orElseThrow(() -> new NotFoundException(userId, "User"));
    }

    private void updateUserData(User user, User dataToUpdate) {
        if (dataToUpdate.getEmail() != null) {
            user.setEmail(dataToUpdate.getEmail());
        }

        if (dataToUpdate.getName() != null) {
            user.setName(dataToUpdate.getName());
        }
    }
}
