package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreate;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDto create(@RequestHeader("X-Sharer-User-Id") long userId,
                             @Valid @RequestBody BookingDtoCreate bookingDto) {
        return service.create(bookingDto, userId);
    }

    @PatchMapping(path = "/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public BookingDto updateState(@RequestHeader("X-Sharer-User-Id") long userId,
                                   @RequestParam("approved") boolean approved,
                                   @PathVariable("bookingId") long bookingId) {
        return null;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookingDto> findAll(@RequestHeader("X-Sharer-User-Id") long userId,
                                    @RequestParam("state") BookingState state) {
        return null;
    }

    @GetMapping(path = "/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public BookingDto findById(@RequestHeader("X-Sharer-User-Id") long userId,
                               @PathVariable("bookingId") long bookingId) {
        return null;
    }
}
