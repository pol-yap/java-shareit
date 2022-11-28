package ru.practicum.shareit.request.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class ItemRequestDtoCreate {

    @NotNull(message = "Description shouldn't be empty")
    @NotBlank(message = "Description shouldn't be empty")
    private String description;
}
