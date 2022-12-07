package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDtoCreate;
import ru.practicum.shareit.booking.dto.BookingStatus;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
	private final BookingClient client;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") long userId,
										 @Valid @RequestBody BookingDtoCreate dto) {
		return client.create(userId, dto);
	}

	@PatchMapping(path = "/{bookingId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> updateState(@RequestHeader("X-Sharer-User-Id") long userId,
											  @RequestParam("approved") boolean approved,
											  @PathVariable("bookingId") long bookingId) {
		return client.approve(userId, bookingId, approved);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> findAllOfBooker(
			@RequestHeader("X-Sharer-User-Id") long userId,
			@Valid @RequestParam(value = "state", defaultValue = "all") String stateParam,
			@PositiveOrZero @RequestParam(defaultValue = "0") int from,
			@Positive @RequestParam(defaultValue = "20") int size) {

		BookingStatus status = BookingStatus.from(stateParam)
											.orElseThrow(() -> new IllegalArgumentException("Unknown state: " + stateParam));

		return client.findAllOfBooker(userId, status, from, size);
	}

	@GetMapping(path = "/owner")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> findAllOfOwner(
			@RequestHeader("X-Sharer-User-Id") long userId,
			@Valid @RequestParam(value = "state", defaultValue = "all") String stateParam,
			@PositiveOrZero @RequestParam(defaultValue = "0") int from,
			@Positive @RequestParam(defaultValue = "20") int size) {

		BookingStatus status = BookingStatus.from(stateParam)
				.orElseThrow(() -> new IllegalArgumentException("Unknown state: " + stateParam));

		return client.findAllOfOwner(userId, status, from, size);
	}

	@GetMapping(path = "/{bookingId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> findById(@RequestHeader("X-Sharer-User-Id") long userId,
										   @PathVariable("bookingId") long bookingId) {
		return client.findById(userId, bookingId);
	}

//	@GetMapping
//	public ResponseEntity<Object> getBookings(@RequestHeader("X-Sharer-User-Id") long userId,
//			@RequestParam(name = "state", defaultValue = "all") String stateParam,
//			@PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
//			@Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
//		BookingState state = BookingState.from(stateParam)
//				.orElseThrow(() -> new IllegalArgumentException("Unknown state: " + stateParam));
//		log.info("Get booking with state {}, userId={}, from={}, size={}", stateParam, userId, from, size);
//		return bookingClient.getBookings(userId, state, from, size);
//	}
//
//	@PostMapping
//	public ResponseEntity<Object> bookItem(@RequestHeader("X-Sharer-User-Id") long userId,
//			@RequestBody @Valid BookItemRequestDto requestDto) {
//		log.info("Creating booking {}, userId={}", requestDto, userId);
//		return bookingClient.bookItem(userId, requestDto);
//	}
//
//	@GetMapping("/{bookingId}")
//	public ResponseEntity<Object> getBooking(@RequestHeader("X-Sharer-User-Id") long userId,
//			@PathVariable Long bookingId) {
//		log.info("Get booking {}, userId={}", bookingId, userId);
//		return bookingClient.getBooking(userId, bookingId);
//	}
}
