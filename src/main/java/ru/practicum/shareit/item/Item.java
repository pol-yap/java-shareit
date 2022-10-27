package ru.practicum.shareit.item;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO Sprint add-controllers.
 */
@Data
@NoArgsConstructor
public class Item {

    private Long id;

    private String name;

    private String description;

    private Boolean available;

    private Long owner;

    private Long request;
}
