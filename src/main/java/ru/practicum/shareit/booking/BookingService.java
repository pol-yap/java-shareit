package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.common.errors.BadRequestException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserMapper;
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

    public BookingDto create(BookingDto dto, Long userId) {
        enrichData(dto, userId);
        //validate();
        Booking booking = bookingMapper.toModel(dto);
        repository.save(booking);

        return bookingMapper.toDTO(booking);
    }

    private void enrichData(BookingDto dto, Long userId) {
        User user = userMapper.toModel(userService.findById(userId));
        Item item = itemMapper.toModel(itemService.findById(dto.getItemId()));
        dto.setBookerId(userId);
        dto.setBooker(user);
        dto.setItem(item);
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
