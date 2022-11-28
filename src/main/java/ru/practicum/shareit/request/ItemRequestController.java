package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.common.errors.BadRequestException;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoCreate;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    private final ItemRequestService service;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ItemRequestDto create(@RequestHeader("X-Sharer-User-Id") long userId,
                                 @Valid @RequestBody ItemRequestDtoCreate itemRequestDtoCreate) {
        return service.create(itemRequestDtoCreate, userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ItemRequestDto> getOwn(@RequestHeader("X-Sharer-User-Id") long userId) {
        return service.getOwn(userId);
    }

    @GetMapping(path = "/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemRequestDto> getAll(@RequestHeader("X-Sharer-User-Id") long userId,
                                       @RequestParam(defaultValue = "0") int from,
                                       @RequestParam(defaultValue = "20") int size) {
        if (from < 0 || size <= 0) {
            throw new BadRequestException("Wrong pagination parameter value");
        }

        return service.getAll(userId, PageRequest.of(from, size)).getContent();
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ItemRequestDto findById(@RequestHeader("X-Sharer-User-Id") long userId,
                                   @PathVariable long id) {
        return service.findById(userId, id);
    }
}
