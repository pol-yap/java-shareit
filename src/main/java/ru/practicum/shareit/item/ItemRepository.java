package ru.practicum.shareit.item;

import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.common.CommonCRUDRepository;
import ru.practicum.shareit.user.User;

import java.util.List;
import java.util.Set;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByOwnerId(Long userId);

    @Query("SELECT i FROM Item i WHERE " +
            "(upper(i.name) LIKE upper(:criteria) " +
            "OR upper(i.description) LIKE upper(:criteria)) " +
            "AND available = true")
    List<Item> search(@Param("criteria") String criteria);
}
