package ru.practicum.shareit.request.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDtoBrief;
import ru.practicum.shareit.request.ItemRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ItemRequestDto {
    private Long id;
    private String description;
    private LocalDateTime created;
    private List<ItemDtoBrief> items;

    public ItemRequestDto(ItemRequest itemRequest) {
        setId(itemRequest.getId());
        setDescription(itemRequest.getDescription());
        setCreated(itemRequest.getCreated());
        setItems(itemRequest.getItems()
                            .stream()
                            .map(ItemDtoBrief::new)
                            .collect(Collectors.toList()));
    }
}
