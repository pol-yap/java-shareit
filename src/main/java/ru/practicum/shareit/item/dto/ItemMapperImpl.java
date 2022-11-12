package ru.practicum.shareit.item.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingDtoBrief;
import ru.practicum.shareit.item.Item;

@Component
@RequiredArgsConstructor
public class ItemMapperImpl implements ItemMapper {
    public ItemDto toDto(Item item) {
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setOwnerId(item.getOwnerId());
        dto.setAvailable(item.getAvailable());
        if (item.getNextBooking() == null) {
            dto.setNextBooking(null);
        } else  {
            dto.setNextBooking(new BookingDtoBrief(item.getNextBooking()));
        }
        if (item.getLastBooking() == null) {
            dto.setLastBooking(null);
        } else  {
            dto.setLastBooking(new BookingDtoBrief(item.getLastBooking()));
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
