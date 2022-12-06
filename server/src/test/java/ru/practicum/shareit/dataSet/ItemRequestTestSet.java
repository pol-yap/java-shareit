package ru.practicum.shareit.dataSet;

import lombok.Getter;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoCreate;

import java.time.LocalDateTime;

@Getter
public class ItemRequestTestSet {
    private final ItemRequest itemRequest;
    private final ItemRequestDto dto;
    private final ItemRequestDtoCreate dtoCreate;
    private final LocalDateTime now = LocalDateTime.now();
    private final Long userId = 1L;

    public ItemRequestTestSet() {
        itemRequest = new ItemRequest();
        itemRequest.setId(1L);
        itemRequest.setDescription("This is some description for testing item requests");
        itemRequest.setCreated(now);
        itemRequest.setUserId(userId);

        dto = new ItemRequestDto(itemRequest);

        dtoCreate  = new ItemRequestDtoCreate();
        dtoCreate.setDescription(itemRequest.getDescription());
    }
}
