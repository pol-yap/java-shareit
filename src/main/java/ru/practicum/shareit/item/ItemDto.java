package ru.practicum.shareit.item;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */
@Data
@NoArgsConstructor
public class ItemDto {

    private Long id;

    @NotBlank(message = "Item name shouldn't be empty")
    private String name;

    @NotBlank(message = "Item description shouldn't be empty")
    private String description;

    @NotNull(message = "Availability of item should be defined")
    private Boolean available;
}
