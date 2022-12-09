package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDtoCreate;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class RequestController {
    private final RequestClient client;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") long userId,
                                         @Valid @RequestBody ItemRequestDtoCreate itemRequestDtoCreate) {
        log.info("User {} makes new request: {}", userId, itemRequestDtoCreate);

        return client.create(userId, itemRequestDtoCreate);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getOwn(@RequestHeader("X-Sharer-User-Id") long userId) {
        log.info("User {} finds his requests", userId);

        return client.getOwn(userId);
    }

    @GetMapping(path = "/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getAll(@RequestHeader("X-Sharer-User-Id") long userId,
                                         @PositiveOrZero @RequestParam(defaultValue = "0") int from,
                                         @Positive @RequestParam(defaultValue = "20") int size) {
        log.info("User {} view all requests", userId);

        return client.getAll(userId, from, size);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> findById(@RequestHeader("X-Sharer-User-Id") long userId,
                                           @PathVariable long id) {
        log.info("User {} finds request {}", userId, id);

        return client.findById(userId, id);
    }
}
