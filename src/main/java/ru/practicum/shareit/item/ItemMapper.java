package ru.practicum.shareit.item;

public interface ItemMapper {

    ItemDto toDTO(Item item);

    Item toModel(ItemDto itemDto);
}
