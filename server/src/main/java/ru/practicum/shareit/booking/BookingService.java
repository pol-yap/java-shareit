package ru.practicum.shareit.booking;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreate;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.common.errors.BadRequestException;
import ru.practicum.shareit.common.errors.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
    private final BookingRepository repository;
    private final UserService userService;
    private final ItemService itemService;
    private final BookingMapper bookingMapper;
    private final UserMapper userMapper;
    private final ItemMapper itemMapper;

    public BookingDto create(BookingDtoCreate dto, Long userId) {
        Booking booking = bookingMapper.fromDtoCreate(dto);
        enrich(booking, userId, dto.getItemId());
        throwIfNotValid(booking);
        booking.setStatus(BookingStatus.WAITING);
        repository.save(booking);
        log.trace("Created booking: " + booking);

        return bookingMapper.toDto(booking);
    }

    public BookingDto approveBooking(Long bookingId, Long userId, boolean approved) {
        Booking booking = findBookingById(bookingId);
        if (!userId.equals(booking.getItem().getOwnerId())) {
            throw new NotFoundException(bookingId, "booking");
        }
        if (booking.getStatus() == BookingStatus.APPROVED) {
            throw new BadRequestException("Booking is already approved");
        }
        booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        log.trace(String.format("Booking id=%d status changed to %s", booking.getId(), booking.getStatus()));

        return bookingMapper.toDto(repository.save(booking));
    }

    public List<BookingDto> findAllOfBooker(Long userId, BookingStatus status, int page, int size) {
        userService.throwIfNoUser(userId);

        return findAllByStatus(QBooking.booking.booker.id.eq(userId), status, page, size);
    }

    public List<BookingDto> findAllOfOwner(Long userId, BookingStatus status, int page, int size) {
        userService.throwIfNoUser(userId);

        return findAllByStatus(QBooking.booking.item.ownerId.eq(userId), status, page, size);
    }

    public BookingDto findById(Long bookingId, Long userId) {
        Booking booking = findBookingById(bookingId);
        if (!userId.equals(booking.getBooker().getId()) && !userId.equals(booking.getItem().getOwnerId())) {
            throw new NotFoundException(bookingId, "booking");
        }

        return bookingMapper.toDto(booking);
    }

    private Booking findBookingById(Long bookingId) {
        return repository.findById(bookingId)
                         .orElseThrow(() -> new NotFoundException(bookingId, "booking"));
    }

    private List<BookingDto> findAllByStatus(BooleanExpression initialCondition, BookingStatus status, int from, int size) {
        if (from < 0 || size <= 0) {
            throw new BadRequestException("Wrong pagination parameter value");
        }

        LocalDateTime now = LocalDateTime.now();
        List<BooleanExpression> conditions = new ArrayList<>();
        conditions.add(initialCondition);

        switch (status) {
            case REJECTED:
            case WAITING:
                conditions.add(QBooking.booking.status.eq(status));
                break;

            case FUTURE:
                conditions.add(QBooking.booking.startDate.after(now));
                break;

            case PAST:
                conditions.add(QBooking.booking.endDate.before(now));
                break;

            case CURRENT:
                conditions.add(QBooking.booking.startDate.before(now));
                conditions.add(QBooking.booking.endDate.after(now));
                break;

            default:
        }

        BooleanExpression finalCondition = conditions.stream()
                                                     .reduce(BooleanExpression::and)
                                                     .get();
        Pageable pageable = PageRequest.of(from / size, size, Sort.by("startDate").descending());

        return repository.findAll(finalCondition, pageable)
                         .map(bookingMapper::toDto)
                         .toList();
    }

    private void enrich(Booking booking, Long userId, Long itemId) {
        User user = userMapper.fromDto(userService.findById(userId));
        Item item = itemMapper.fromDto(itemService.findById(itemId, userId));
        booking.setBooker(user);
        booking.setItem(item);
    }

    private void throwIfNotValid(Booking booking) {
        if (! booking.getItem().getAvailable()) {
            throw new BadRequestException("Item is not available");
        }

        if (booking.getEndDate().isBefore(booking.getStartDate())) {
            throw new BadRequestException("Booking end date shouldn't precede start date");
        }

        if (booking.getBooker().getId().equals(booking.getItem().getOwnerId())) {
            throw new NotFoundException(booking.getItem().getId(), "item");
        }

        List<Booking> otherBookings = repository.findByItemIdAndStatusNot(
                booking.getItem().getId(),
                BookingStatus.REJECTED);

        otherBookings.forEach(b -> {
            if (b.getStartDate().isAfter(booking.getStartDate()) && b.getStartDate().isBefore(booking.getEndDate()) ||
                    b.getEndDate().isAfter(booking.getStartDate()) && b.getEndDate().isBefore(booking.getEndDate())) {
                throw new NotFoundException(booking.getItem().getId(), "item");
            }
        });
    }
}
