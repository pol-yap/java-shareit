package ru.practicum.shareit.booking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BookingDtoCreate {

    private LocalDateTime start;

    private LocalDateTime end;

    private Long itemId;
}
