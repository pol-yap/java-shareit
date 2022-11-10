package ru.practicum.shareit.booking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BookingDtoCreate {

    @NotNull(message = "Booking start date shouldn't be empty")
    @FutureOrPresent(message = "We can't book anything in the past")
    private LocalDateTime start;

    @NotNull(message = "Booking end date shouldn't be empty")
    @FutureOrPresent(message = "We can't book anything in the past")
    private LocalDateTime end;

    @NotNull(message = "Booking item's ID shouldn't be empty")
    private Long itemId;
}
