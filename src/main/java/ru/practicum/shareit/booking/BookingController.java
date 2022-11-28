package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreate;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
        return service.approveBooking(bookingId, userId, approved);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookingDto> findAllOfBooker(
            @RequestHeader("X-Sharer-User-Id") long userId,
            @RequestParam(value = "state", required = false) Optional<BookingStatus> status,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "20") int size) {

        return service.findAllOfBooker(userId, status.orElse(BookingStatus.ALL), from, size);
    }

    @GetMapping(path = "/owner")
    @ResponseStatus(HttpStatus.OK)
    public List<BookingDto> findAllOfOwner(
            @RequestHeader("X-Sharer-User-Id") long userId,
            @Valid @RequestParam(value = "state", required = false) Optional<BookingStatus> status,
            @RequestParam(defaultValue = "0") int from,
            @RequestParam(defaultValue = "20") int size) {

        return service.findAllOfOwner(userId, status.orElse(BookingStatus.ALL), from, size);
    }

    @GetMapping(path = "/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public BookingDto findById(@RequestHeader("X-Sharer-User-Id") long userId,
                               @PathVariable("bookingId") long bookingId) {
        return service.findById(bookingId, userId);
    }
}
