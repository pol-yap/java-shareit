package ru.practicum.shareit.item.dto;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookingForItemDto;
import ru.practicum.shareit.item.Item;

import java.util.Optional;

@Component
public class ItemMapperImpl implements ItemMapper {
    public ItemDto toDto(Item item) {
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setOwnerId(item.getOwnerId());
        dto.setAvailable(item.getAvailable());
        Booking nextBooking = item.getNextBooking();
        if (nextBooking != null) {
            dto.setNextBooking(new BookingForItemDto(nextBooking.getId(), nextBooking.getBooker().getId()));
        }
        Booking lastBooking = item.getLastBooking();
        if (lastBooking != null) {
            dto.setLastBooking(new BookingForItemDto(lastBooking.getId(), lastBooking.getBooker().getId()));
        }


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
}
