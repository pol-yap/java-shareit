package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoCreate;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {
    private final UserClient client;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        log.info("Get users");
        return client.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        log.info("Get user {}", id);
        return client.findById(id);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid UserDtoCreate dto) {
        log.info("Create new user");
        return client.create(dto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@RequestBody UserDto dto,
                                         @PathVariable Long id) {
        log.info("Update user {}", id);
        return client.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        log.info("Delete user {}", id);
        return client.delete(id);
    }
}
