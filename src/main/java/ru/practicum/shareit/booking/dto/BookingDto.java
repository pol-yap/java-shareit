package ru.practicum.shareit.booking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDtoBrief;
import ru.practicum.shareit.user.dto.UserDtoBrief;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BookingDto {

    private Long id;

    @FutureOrPresent(message = "We can't book anything in the past")
    private LocalDateTime start;

    @FutureOrPresent(message = "We can't book anything in the past")
    private LocalDateTime end;

    @NotNull(message = "Booking item's ID shouldn't be empty")
    private Long itemId;

    private ItemDtoBrief item;

    private Long bookerId;

    private UserDtoBrief booker;

    private BookingStatus status;
}
