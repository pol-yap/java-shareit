package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.errors.NotFoundException;
import ru.practicum.shareit.common.errors.NotUniqueException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public UserDto create(UserDto userDto) {
        User user = mapper.toModel(userDto);
        throwIfEmailNotUnique(user);
        return mapper.toDTO(repository.create(user));
    }

    public UserDto update(Long userId, UserDto userDto) {
        User userToUpdate = findUserById(userId);
        User newUserData = mapper.toModel(userDto);
        updateUserData(userToUpdate, newUserData);
        throwIfEmailNotUnique(userToUpdate);

        return mapper.toDTO(repository.update(userId, userToUpdate));
    }

    public UserDto findById(Long userId) {
        return mapper.toDTO(findUserById(userId));
    }

    public List<UserDto> findAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::toDTO)
                         .collect(Collectors.toList());
    }

    public void deleteById(Long userId) {
        repository.deleteById(userId);
    }

    public boolean isExists(Long userId) {
        return repository.isExists(userId);
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

    private void throwIfEmailNotUnique(User user) {
        if (! repository.isEmailUnique(user)) {
            throw new NotUniqueException("User with such email is already exists");
        }
    }
}
