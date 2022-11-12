package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.common.errors.BadRequestException;
import ru.practicum.shareit.common.errors.ForbiddenException;
import ru.practicum.shareit.common.errors.NotFoundException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserMapper;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository repository;
    private final ItemMapper mapper;
    private final UserService userService;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserMapper userMapper;

    @Transactional
    public ItemDto create(ItemDtoCreate itemDto, Long userId) {
        userService.throwIfNoUser(userId);
        Item item = mapper.fromDtoCreate(itemDto);
        item.setOwnerId(userId);
        item.setAvailable(true);

        return mapper.toDto(repository.save(item));
    }

    @Transactional
    public ItemDto update(Long itemId, ItemDto itemDto, Long userId) {
        userService.throwIfNoUser(userId);
        Item item = findItemById(itemId);
        throwIfUserNotOwner(item, userId);
        updateItemData(item, mapper.fromDto(itemDto));

        return mapper.toDto(item);
    }

    @Transactional
    public ItemDto findById(Long itemId, Long userId) {
        Item item = findItemById(itemId);
        enrichByLastAndNextBookings(item, userId);
        return mapper.toDto(item);
    }

    @Transactional
    public List<ItemDto> findAllByOwner(final Long userId) {
        List<Item> items = repository.findByOwnerId(userId);
        items.forEach(i -> enrichByLastAndNextBookings(i, userId));

        return repository.findByOwnerId(userId)
                         .stream()
                         .map(mapper::toDto)
                         .collect(Collectors.toList());
    }

    @Transactional
    public List<ItemDto> search(String criteria) {
        if (criteria.isBlank()) {
            return new ArrayList<>();
        }

        return repository.search("%" + criteria + "%")
                         .stream()
                         .map(mapper::toDto)
                         .collect(Collectors.toList());
    }

    @Transactional
    public CommentDto addComment(Long itemId, Long userId, CommentDtoCreate commentDtoCreate) {
        Item item = findItemById(itemId);
        User user = userMapper.fromDto(userService.findById(userId));
        throwIfUserNotBookedItem(item, userId);

        Comment comment = commentMapper.fromDtoCreate(commentDtoCreate);
        comment.setCreated(LocalDateTime.now());
        comment.setAuthor(user);
        comment.setItem(item);
        commentRepository.saveAndFlush(comment);

        return commentMapper.toDto(comment);
    }

    private void throwIfUserNotOwner(Item item, Long userId) {
        if (! Objects.equals(item.getOwnerId(), userId)) {
            throw new ForbiddenException("user is not owner of item");
        }
    }

    private void updateItemData(Item item, Item dataToUpdate) {
        if (dataToUpdate.getName() != null) {
            item.setName(dataToUpdate.getName());
        }

        if (dataToUpdate.getDescription() != null) {
            item.setDescription(dataToUpdate.getDescription());
        }

        if (dataToUpdate.getAvailable() != null) {
            item.setAvailable(dataToUpdate.getAvailable());
        }
    }

    private Item findItemById(Long itemId) {
        return repository.findById(itemId)
                         .orElseThrow(() -> new NotFoundException(itemId, "item"));
    }

    private void enrichByLastAndNextBookings(Item item, Long userId) {
        if (item.getOwnerId().equals(userId)) {
            item.setNextBooking(findNextBooking(item.getBookings()).orElse(null));
            item.setLastBooking(findLastBooking(item.getBookings()).orElse(null));
        }
    }

    private Optional<Booking> findNextBooking(List<Booking> bookings) {
        return bookings.stream()
                       .filter(b -> b.getStartDate().isAfter(LocalDateTime.now()))
                       .min(Comparator.comparing(Booking::getStartDate));
    }

    private Optional<Booking> findLastBooking(List<Booking> bookings) {
        return bookings.stream()
                       .filter(b -> b.getEndDate().isBefore(LocalDateTime.now()))
                       .max(Comparator.comparing(Booking::getEndDate));
    }

    private void throwIfUserNotBookedItem(Item item, Long userId) {
        if (item.getBookings()
               .stream()
                .filter(b -> b.getStatus().equals(BookingStatus.APPROVED))
                .filter(b -> b.getEndDate().isBefore(LocalDateTime.now()))
               .noneMatch(b -> b.getBooker().getId().equals(userId))) {
            throw new BadRequestException("User shouldn't comment item he's never booked");
        }
    }
}
