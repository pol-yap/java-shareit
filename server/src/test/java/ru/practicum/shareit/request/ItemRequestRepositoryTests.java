package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.dataSet.ItemRequestTestSet;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemRequestRepositoryTests {
    private final ItemRequestRepository repository;
    private final ItemRequestTestSet testSet = new ItemRequestTestSet();

    @Test
    void commonTest() {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setUserId(testSet.getUserId());
        itemRequest.setDescription(testSet.getItemRequest().getDescription());
        itemRequest.setCreated(testSet.getNow());

        repository.save(itemRequest);
        assertThat(itemRequest.getUserId(), notNullValue());

        List<ItemRequest> itemRequestList = repository.findByUserIdOrderByCreatedDesc(testSet.getUserId());
        assertThat(itemRequestList, hasSize(1));

        itemRequestList = repository.findByUserIdNotOrderByCreatedDesc(66L, PageRequest.of(0,20))
                                    .getContent();
        assertThat(itemRequestList, hasSize(1));
    }
}
