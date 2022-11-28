package ru.practicum.shareit.request;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {

    List<ItemRequest> findByUserIdOrderByCreatedDesc(Long userId);

    Page<ItemRequest> findByUserIdNotOrderByCreatedDesc(Long userId, Pageable pageable);
}
