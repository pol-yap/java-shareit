package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentDtoCreate;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoCreate;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> findAll(@RequestHeader("X-Sharer-User-Id") long userId) {
        return service.findAllByOwner(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto create(@Valid @RequestBody ItemDtoCreate itemDto,
                          @RequestHeader("X-Sharer-User-Id") long userId) {
        return service.create(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto update(@PathVariable long itemId,
                          @RequestBody ItemDto itemDto,
                          @RequestHeader("X-Sharer-User-Id") long userId) {
        return service.update(itemId, itemDto, userId);
    }

    @GetMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto findById(@PathVariable long itemId,
                            @RequestHeader("X-Sharer-User-Id") long userId) {
        return service.findById(itemId, userId);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> search(@RequestParam(name = "text") String criteria) {
        return service.search(criteria);
    }

    @PostMapping("/{itemId}/comment")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto addComment(@PathVariable("itemId") long itemId,
                                 @Valid @RequestBody CommentDtoCreate commentDtoCreate,
                                 @RequestHeader("X-Sharer-User-Id") long userId) {
        return service.addComment(itemId, userId, commentDtoCreate);
    }
}
