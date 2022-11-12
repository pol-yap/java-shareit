package ru.practicum.shareit.booking.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.ItemDtoBrief;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDtoBrief;

@Component
@RequiredArgsConstructor
public class BookingMapperImpl implements BookingMapper{
    public BookingDto toDto(Booking booking) {
        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setStart(booking.getStartDate());
        dto.setEnd(booking.getEndDate());
        dto.setStatus(booking.getStatus());
        dto.setItem(getNullableItem(booking.getItem()));
        dto.setBooker(getNullableUser(booking.getBooker()));

        return dto;
    }

    public Booking fromDto(BookingDto dto) {
        Booking booking = new Booking();
        booking.setId(dto.getId());
        booking.setStartDate(dto.getStart());
        booking.setEndDate(dto.getEnd());
        booking.setStatus(dto.getStatus());

        return booking;
    }

    public Booking fromDtoCreate(BookingDtoCreate dto) {
        Booking booking = new Booking();
        booking.setStartDate(dto.getStart());
        booking.setEndDate(dto.getEnd());

        return booking;
    }

    public BookingDtoBrief toDtoBrief(Booking booking) {
        BookingDtoBrief dto = new BookingDtoBrief();
        dto.setId(booking.getId());
        dto.setBookerId(booking.getBooker().getId());

        return dto;
    }

    private ItemDtoBrief getNullableItem(Item item) {
        if (item == null) return null;

        return new ItemDtoBrief(item);
    }

    private UserDtoBrief getNullableUser(User user) {
        if (user == null) return null;

        return new UserDtoBrief(user);
    }
}
