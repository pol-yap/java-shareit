package ru.practicum.shareit.common;

public interface CommonMapper<TModel, TDto> {

    TDto toDTO(TModel model);

    TModel toModel(TDto dto);
}
