package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ItemDtoCreate {

    @NotNull(message = "Item name shouldn't be empty")
    @NotBlank(message = "Item name shouldn't be empty")
    private String name;

    @NotNull(message = "Item description shouldn't be empty")
    @NotBlank(message = "Item description shouldn't be empty")
    private String description;

    @NotNull(message = "Availability of item should be defined")
    private Boolean available;
}