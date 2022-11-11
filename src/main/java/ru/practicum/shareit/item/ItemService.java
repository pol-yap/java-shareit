package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.common.errors.ForbiddenException;
import ru.practicum.shareit.common.errors.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoCreate;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository repository;
    private final ItemMapper mapper;
    private final UserService userService;

    @Transactional
    public ItemDto create(ItemDtoCreate itemDtoDto, Long userId) {
        throwIfUserNotExists(userId);
        Item item = mapper.fromDtoCreate(itemDtoDto);
        item.setOwnerId(userId);
        item.setAvailable(true);

        return mapper.toDto(repository.save(item));
    }

    @Transactional
    public ItemDto update(Long itemId, ItemDto itemDto, Long userId) {
        throwIfUserNotExists(userId);
        Item item = findItemById(itemId);
        throwIfUserNotOwner(item, userId);
        updateItemData(item, mapper.fromDto(itemDto));

        return mapper.toDto(item);
    }

    @Transactional
    public ItemDto findById(Long itemId, Long userId) {
        Item item = findItemById(itemId);
        findBookings(item, userId);
        return mapper.toDto(item);
    }

    @Transactional
    public List<ItemDto> findAllByOwner(final Long userId) {
        List<Item> items = repository.findByOwnerId(userId);
        items.forEach(i->findBookings(i, userId));

        return repository.findByOwnerId(userId)
                         .stream()
                         .map(mapper::toDto)
                         .collect(Collectors.toList());
    }

    public List<ItemDto> search(String criteria) {
        if (criteria.isBlank()) {
            return new ArrayList<>();
        }

        return repository.search("%" + criteria + "%")
                         .stream()
                         .map(mapper::toDto)
                         .collect(Collectors.toList());
    }

    private void throwIfUserNotExists(Long userId) {
        if (! userService.isExists(userId)) {
            throw new NotFoundException(userId, "user");
        }
    }

    private void throwIfItemNotExists(Long itemId) {
        if (! userService.isExists(itemId)) {
            throw new NotFoundException(itemId, "item");
        }
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

    private void findBookings(Item item, Long userId) {
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
}
