package ru.practicum.shareit.booking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.BookingState;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

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

    private Item item;

    private Long bookerId;

    private User booker;

    private BookingState status;
}
