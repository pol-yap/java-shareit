package ru.practicum.shareit.item;

import ru.practicum.shareit.common.CommonCRUDRepository;

import java.util.List;

public interface ItemRepository extends CommonCRUDRepository<Item> {

    List<Item> findAllByOwner(Long userId);

    List<Item> search(String criteria);
}
