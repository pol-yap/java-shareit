package ru.practicum.shareit.booking;

import org.springframework.stereotype.Component;

@Component
public class BookingMapperImpl implements BookingMapper{
    public BookingDto toDTO(Booking booking) {
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

    public Booking toModel(BookingDto dto) {
        Booking booking = new Booking();
        booking.setId(dto.getId());
        booking.setStartDate(dto.getStart());
        booking.setEndDate(dto.getEnd());
        booking.setItem(dto.getItem());
        booking.setBooker(dto.getBooker());
        booking.setStatus(dto.getStatus());

        return booking;
    }
}
