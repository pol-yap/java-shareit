package ru.practicum.shareit.booking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BookingDto {

    private Long id;

    @FutureOrPresent(message = "We can't book anything in the past")
    private LocalDateTime start;

    @FutureOrPresent(message = "We can't book anything in the past")
    private LocalDateTime end;

    private BookingStatus status;
}
