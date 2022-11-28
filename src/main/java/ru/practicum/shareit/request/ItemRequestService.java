package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoCreate;
import ru.practicum.shareit.user.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
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
}
