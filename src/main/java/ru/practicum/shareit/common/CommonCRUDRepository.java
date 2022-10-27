package ru.practicum.shareit.common;

import java.util.List;
import java.util.Optional;

public interface CommonCRUDRepository<T> {
    T create(T t);
    Optional<T> findById(Long id);
    List<T> findAll();
    T update(Long id, T t);
    void deleteById(Long id);
    boolean isExists(Long id);
}
