package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.common.errors.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public UserDto create(UserDto userDto) {
        User user = mapper.toModel(userDto);
        repository.saveAndFlush(user);

        return mapper.toDTO(user);
    }

    public UserDto update(Long userId, UserDto userDto) {
        User user = findUserById(userId);
        updateUserData(user, mapper.toModel(userDto));
        repository.saveAndFlush(user);

        return mapper.toDTO(user);
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
        return repository.existsById(userId);
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
//        if (! repository.isEmailUnique(user)) {
//            throw new NotUniqueException("User with such email is already exists");
//        }
    }
}
