package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentDtoCreate;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoCreate;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private final ItemClient client;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> findAll(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Get items of user {}", userId);
        return client.findAllByOwner(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(@Valid @RequestBody ItemDtoCreate itemDto,
                                         @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Create new item of user {}", userId);
        return client.create(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> update(@PathVariable long itemId,
                                         @RequestBody ItemDto itemDto,
                                         @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Update item {} of user {}", itemId, userId);
        return client.update(userId, itemId, itemDto);
    }

    @GetMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> findById(@PathVariable long itemId,
                                           @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Get item {} of user {}", itemId, userId);
        return client.findById(userId, itemId);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> search(@RequestParam(name = "text") String criteria) {
        log.info("Search items by criteria '{}'", criteria);
        return client.search(criteria);
    }

    @PostMapping("/{itemId}/comment")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> addComment(@PathVariable("itemId") long itemId,
                                             @Valid @RequestBody CommentDtoCreate commentDtoCreate,
                                             @RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("Add comment about item {} by user {}", itemId, userId);
        return client.addComment(userId, itemId, commentDtoCreate);
    }
}
