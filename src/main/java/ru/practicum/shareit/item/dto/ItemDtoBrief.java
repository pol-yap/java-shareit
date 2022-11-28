package ru.practicum.shareit.item.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.item.Item;

@Getter
@Setter
@NoArgsConstructor
public class ItemDtoBrief {
    private Long id;
    private String name;
    private String description;
    private Long ownerId;
    private Boolean available;
    private Long requestId;

    public ItemDtoBrief(Item item) {
        setId(item.getId());
        setName(item.getName());
        setOwnerId(item.getOwnerId());
        setDescription(item.getDescription());
        setAvailable(item.getAvailable());
        setRequestId(item.getRequestId());
    }
}
