package ru.practicum.shareit.booking.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.user.User;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BookingDto {

    private Long id;

    @FutureOrPresent(message = "We can't book anything in the past")
    private LocalDate start;

    @FutureOrPresent(message = "We can't book anything in the past")
    private LocalDate end;

    @NotNull(message = "Booking item's ID shouldn't be empty")
    private Long itemId;

    private Item item;

    private Long bookerId;

    private User booker;

    private BookingStatus status;
}
