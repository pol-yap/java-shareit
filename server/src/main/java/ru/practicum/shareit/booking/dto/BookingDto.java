package ru.practicum.shareit.booking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDtoBrief;
import ru.practicum.shareit.user.dto.UserDtoBrief;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BookingDto {

    private Long id;

    private LocalDateTime start;

    private LocalDateTime end;

    private ItemDtoBrief item;

    private UserDtoBrief booker;

    private BookingStatus status;
}
