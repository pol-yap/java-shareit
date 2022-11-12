package ru.practicum.shareit.booking.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.dto.ItemDtoBrief;
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

        if (booking.getItem() == null) {
            dto.setItem(null);
        } else {
            dto.setItem(new ItemDtoBrief(booking.getItem()));
            dto.setItemId(booking.getItem().getId());
        }

        if (booking.getBooker() == null) {
            dto.setBooker(null);
        } else {
            dto.setBooker(new UserDtoBrief(booking.getBooker()));
            dto.setBookerId(booking.getBooker().getId());
        }

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
}
