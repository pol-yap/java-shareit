package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.common.errors.NotFoundException;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoCreate;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemRequestService {
    private final ItemRequestRepository repository;
    private final UserService userService;

    @Transactional
    public ItemRequestDto create(ItemRequestDtoCreate createDto, Long userId) {
        userService.throwIfNoUser(userId);

        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription(createDto.getDescription());
        itemRequest.setUserId(userId);
        itemRequest.setCreated(LocalDateTime.now());
        log.info("Created item request: " + itemRequest);

        return new ItemRequestDto(repository.save(itemRequest));
    }

    @Transactional
    public List<ItemRequestDto> getOwn(Long userId) {
        userService.throwIfNoUser(userId);

        return repository.findByUserIdOrderByCreatedDesc(userId)
                         .stream()
                         .map(ItemRequestDto::new)
                         .collect(Collectors.toList());
    }

    @Transactional
    public List<ItemRequestDto> getAll(Long userId, int from, int size) {
        return repository.findByUserIdNotOrderByCreatedDesc(userId, PageRequest.of(from / size, size))
                         .map(ItemRequestDto::new).getContent();
    }

    @Transactional
    public ItemRequestDto findById(Long userId, Long id) {
        userService.throwIfNoUser(userId);

        return new ItemRequestDto(repository.findById(id)
                                            .orElseThrow(() -> new NotFoundException(id, "item request")));
    }
}
