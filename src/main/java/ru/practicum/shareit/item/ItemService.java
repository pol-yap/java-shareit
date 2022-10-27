package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    public ItemDto create(ItemDto itemDtoDto, Long userId) {
        checkUserIdOrThrow(userId);
        Item item = mapper.toModel(itemDtoDto);
        item.setOwner(userId);
        item.setAvailable(true);

        return mapper.toDTO(repository.create(item));
    }

    public ItemDto findById(Long itemId) {
        return mapper.toDTO(findItemById(itemId));
    }

    public List<ItemDto> findAllByOwner(Long userId) {
        return repository.findAllByOwner(userId)
                         .stream()
                         .map(mapper::toDTO)
                         .collect(Collectors.toList());
    }

    public List<ItemDto> search(String criteria) {
        if (criteria.isBlank()) {
            return new ArrayList<>();
        }
        return repository.search(criteria)
                         .stream()
                         .map(mapper::toDTO)
                         .collect(Collectors.toList());
    }

    public ItemDto update(Long itemId, ItemDto itemDto, Long userId) {
        checkUserIdOrThrow(userId);
        Item itemToUpdate = findItemById(itemId);
        checkItemOwnerOrThrow(itemToUpdate, userId);
        Item newItemData = mapper.toModel(itemDto);
        updateItemData(itemToUpdate, newItemData);

        return mapper.toDTO(repository.update(itemId, itemToUpdate));
    }

    private void checkUserIdOrThrow(Long userId) {
        if (! userService.isExists(userId)) {
            throw new NotFoundException(userId, "user");
        }
    }

    private void checkItemIdOrThrow(Long itemId) {
        if (! userService.isExists(itemId)) {
            throw new NotFoundException(itemId, "item");
        }
    }

    private void checkItemOwnerOrThrow(Item item, Long userId) {
        if(! Objects.equals(item.getOwner(), userId)) {
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
        return repository.findById(itemId).orElseThrow(() -> new NotFoundException(itemId, "item"));
    }
}
