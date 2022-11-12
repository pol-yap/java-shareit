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

    public ItemDtoBrief(Item item) {
        setId(item.getId());
        setName(item.getName());
    }
}
