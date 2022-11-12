package ru.practicum.shareit.item.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookingDtoBrief;
import ru.practicum.shareit.item.Item;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemMapperImpl implements ItemMapper {
    private final CommentMapper commentMapper;

    public ItemDto toDto(Item item) {
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setOwnerId(item.getOwnerId());
        dto.setAvailable(item.getAvailable());
        dto.setNextBooking(getNullableBooking(item.getNextBooking()));
        dto.setLastBooking(getNullableBooking(item.getLastBooking()));
        dto.setComments(item.getComments().stream()
                            .map(commentMapper::toDto)
                            .collect(Collectors.toList()));

        return dto;
    }

    public Item fromDto(ItemDto dto) {
        Item item = new Item();
        item.setId(dto.getId());
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setOwnerId(dto.getOwnerId());
        item.setAvailable(dto.getAvailable());

        return item;
    }

    public Item fromDtoCreate(ItemDtoCreate dto) {
        Item item = new Item();
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setAvailable(dto.getAvailable());

        return item;
    }

    private BookingDtoBrief getNullableBooking(Booking booking) {
        if (booking == null) return null;

        return new BookingDtoBrief(booking);
    }
}
