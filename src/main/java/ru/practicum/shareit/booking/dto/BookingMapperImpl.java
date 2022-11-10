package ru.practicum.shareit.booking.dto;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.Booking;

@Component
public class BookingMapperImpl implements BookingMapper{
    public BookingDto toDto(Booking booking) {
        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setStart(booking.getStartDate());
        dto.setEnd(booking.getEndDate());
        dto.setItem(booking.getItem());
        if (booking.getItem() != null) {
            dto.setItemId(booking.getItem().getId());
        }
        dto.setBooker(booking.getBooker());
        if (booking.getBooker() != null) {
            dto.setBookerId(booking.getBooker().getId());
        }
        dto.setStatus(booking.getStatus());

        return dto;
    }

    public Booking fromDto(BookingDto dto) {
        Booking booking = new Booking();
        booking.setId(dto.getId());
        booking.setStartDate(dto.getStart());
        booking.setEndDate(dto.getEnd());
        booking.setItem(dto.getItem());
        booking.setBooker(dto.getBooker());
        booking.setStatus(dto.getStatus());

        return booking;
    }

    public Booking fromDtoCreate(BookingDtoCreate dto) {
        Booking booking = new Booking();
        booking.setStartDate(dto.getStart());
        booking.setEndDate(dto.getEnd());

        return booking;
    }
}
