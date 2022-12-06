package ru.practicum.shareit.common;

public interface CommonMapper<E, U, C> {

    U toDto(E entity);

    E fromDto(U dto);

    E fromDtoCreate(C dtoCreate);
}
