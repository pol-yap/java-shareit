package ru.practicum.shareit.booking.dto;

import lombok.*;
import ru.practicum.shareit.booking.Booking;

@Getter
@Setter
@NoArgsConstructor
public class BookingDtoBrief {
    private Long id;
    private Long bookerId;

    public BookingDtoBrief(Booking booking) {
        setId(booking.getId());
        setBookerId(booking.getBooker().getId());
    }
}
