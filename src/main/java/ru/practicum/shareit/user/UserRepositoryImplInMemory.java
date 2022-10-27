package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.common.errors.NotUniqueException;

import java.util.*;

@Repository
public class UserRepositoryImplInMemory implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private long nextUserId = 1;

    public User create(User user) {
        checkEmailUniqueOrThrow(user);
        user.setId(nextUserId);
        users.put(nextUserId, user);
        nextUserId++;

        return user;
    }

    public Optional<User> findById(Long id) {
        if (isExists(id)) {
            return Optional.of(copyOf(users.get(id)));
        } else {
            return Optional.empty();
        }
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public User update(Long id, User user) {
        checkEmailUniqueOrThrow(user);
        users.put(id, user);

        return user;
    }

    public void deleteById(Long id) {
        users.remove(id);
    }

    public boolean isExists(Long id) {
        return users.containsKey(id);
    }

    private void checkEmailUniqueOrThrow(User user) {
        if (isDuplicateEmail(user)) {
            throw new NotUniqueException("User with such email is already exists");
        }
    }

    private boolean isDuplicateEmail(User user) {
        return users.values()
                    .stream()
                    .filter(u -> !Objects.equals(u.getId(), user.getId()))
                    .anyMatch(u -> u.getEmail().equals(user.getEmail()));
    }

    private User copyOf(User user) {
        return User.builder()
                   .id(user.getId())
                   .name(user.getName())
                   .email(user.getEmail())
                   .build();
    }
}
