package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreate;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.common.errors.BadRequestException;
import ru.practicum.shareit.common.errors.ForbiddenException;
import ru.practicum.shareit.common.errors.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        validate(booking);
        booking.setStatus(BookingStatus.WAITING);
        repository.save(booking);

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

        if (approved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }

        return bookingMapper.toDto(repository.save(booking));
    }

    public List<BookingDto> findAllOfBooker(Long userId, BookingStatus status) {
        User user = userMapper.fromDto(userService.findById(userId));
        List<Booking> result;

        switch (status) {
            case REJECTED:
            case WAITING:
                result = repository.findByBooker_IdAndStatusOrderByStartDateDesc(userId, status);
                break;

            case FUTURE:
                result = repository.findByBooker_IdAndStartDateAfterOrderByStartDateDesc(userId, LocalDateTime.now());
                break;

            case PAST:
                result = repository.findByBooker_IdAndEndDateBeforeOrderByStartDateDesc(userId, LocalDateTime.now());
                break;

            default:
                result = repository.findByBooker_IdOrderByStartDateDesc(userId);
        }

        return result.stream()
                     .map(bookingMapper::toDto)
                     .collect(Collectors.toList());
    }

    public List<BookingDto> findAllOfOwner(Long userId, BookingStatus status) {
        User user = userMapper.fromDto(userService.findById(userId));
        List<Booking> result;
        switch (status) {
            case REJECTED:
            case WAITING:
                result = repository.findByItem_OwnerIdAndStatusOrderByStartDateDesc(userId, status);
                break;

            case FUTURE:
                result = repository.findByItem_OwnerIdAndStartDateAfterOrderByStartDateDesc(userId, LocalDateTime.now());
                break;

            case PAST:
                result = repository.findByItem_OwnerIdAndEndDateBeforeOrderByStartDateDesc(userId, LocalDateTime.now());
                break;

            default:
                result = repository.findByItem_OwnerIdOrderByStartDateDesc(userId);
        }

        return result.stream()
                     .map(bookingMapper::toDto)
                     .collect(Collectors.toList());
    }

    public BookingDto findById(Long bookingId, Long userId) {
        Booking booking = findBookingById(bookingId);
        if (!userId.equals(booking.getBooker().getId()) && !userId.equals(booking.getItem().getOwnerId())) {
            throw new NotFoundException(bookingId, "booking");
        }

        return bookingMapper.toDto(booking);
    }

    private Booking findBookingById(Long bookingId) {
        return repository.findById(bookingId).orElseThrow(() -> new NotFoundException(bookingId, "booking"));
    }

    private void enrich(Booking booking, Long userId, Long itemId) {
        User user = userMapper.fromDto(userService.findById(userId));
        Item item = itemMapper.fromDto(itemService.findById(itemId));
        booking.setBooker(user);
        booking.setItem(item);
    }

    private void validate(Booking booking) {
        if (! booking.getItem().getAvailable()) {
            throw new BadRequestException("Item is not available");
        }

        if (booking.getEndDate().isBefore(booking.getStartDate())) {
            throw new BadRequestException("Booking end date shouldn't precede start date");
        }

        if (booking.getBooker().getId().equals(booking.getItem().getOwnerId())) {
            //throw new BadRequestException("Booking item by its owner not provided");
            throw new NotFoundException(booking.getItem().getId(), "item");
        }

        List<Booking> otherBookings = repository.findByItem_IdAndStatusNot(
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
