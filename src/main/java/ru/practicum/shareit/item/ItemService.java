package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.common.errors.ForbiddenException;
import ru.practicum.shareit.common.errors.NotFoundException;
import ru.practicum.shareit.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository repository;
    private final ItemMapper mapper;
    private final UserService userService;

    @Transactional
    public ItemDto create(ItemDto itemDtoDto, Long userId) {
        throwIfUserNotExists(userId);
        Item item = mapper.toModel(itemDtoDto);
        item.setOwnerId(userId);
        item.setAvailable(true);

        return mapper.toDTO(repository.save(item));
    }

    @Transactional
    public ItemDto update(Long itemId, ItemDto itemDto, Long userId) {
        throwIfUserNotExists(userId);
        Item item = findItemById(itemId);
        throwIfUserNotOwner(item, userId);
        updateItemData(item, mapper.toModel(itemDto));

        return mapper.toDTO(item);
    }

    public ItemDto findById(Long itemId) {
        return mapper.toDTO(findItemById(itemId));
    }

    public List<ItemDto> findAllByOwner(Long userId) {
        return repository.findByOwnerId(userId)
                         .stream()
                         .map(mapper::toDTO)
                         .collect(Collectors.toList());
    }

    public List<ItemDto> search(String criteria) {
        if (criteria.isBlank()) {
            return new ArrayList<>();
        }

        return repository.search("%" + criteria + "%")
                         .stream()
                         .map(mapper::toDTO)
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
}
