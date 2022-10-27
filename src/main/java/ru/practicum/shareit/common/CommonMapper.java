package ru.practicum.shareit.common;

public interface CommonMapper<T, U> {

    U toDTO(T model);

    T toModel(U dto);
}
