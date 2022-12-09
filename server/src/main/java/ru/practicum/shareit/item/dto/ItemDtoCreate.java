package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDtoCreate {

    private String name;

    private String description;

    private Boolean available;

    private Long requestId;
}