package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreate;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.common.errors.BadRequestException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.UserService;

@Service
@RequiredArgsConstructor
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
        repository.save(booking);

        return bookingMapper.toDto(booking);
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
            throw new BadRequestException("Booking end date shouldn't precede start date ");
        }
    }
}
