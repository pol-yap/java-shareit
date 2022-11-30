package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.common.errors.BadRequestException;
import ru.practicum.shareit.common.errors.NotFoundException;
import ru.practicum.shareit.dataSet.ItemRequestTestSet;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@SpringBootTest(
        properties = "db.name=test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemRequestServiceTests {
    private final ItemRequestService service;
    private final ItemRequestTestSet testSet = new ItemRequestTestSet();

    @Test
    void createTest() {
        ItemRequestDto dto = service.create(testSet.getDtoCreate(), testSet.getUserId());

        assertThat(dto.getId(), notNullValue());
        assertThat(dto.getCreated(), notNullValue());
        assertThat(dto.getDescription(), equalTo(testSet.getDto().getDescription()));
    }

    @Test
    void getOwnTest() {
        ItemRequestDto dto = service.create(testSet.getDtoCreate(), testSet.getUserId());

        List<ItemRequestDto> dtoList = service.getOwn(testSet.getUserId());
        assertThat(dtoList, hasSize(1));
        assertThat(dtoList, hasItem(any(ItemRequestDto.class)));
    }

    @Test
    void getAllTest() {
        ItemRequestDto dto = service.create(testSet.getDtoCreate(), testSet.getUserId());

        Exception ex = assertThrows(BadRequestException.class, () -> service.getAll(testSet.getUserId(), 0, 0));
        assertThat(ex.getMessage(), equalTo("Wrong pagination parameter value"));

        List<ItemRequestDto> dtoList = service.getAll(66L, 0, 20);
        assertThat(dtoList, hasSize(1));
        assertThat(dtoList, hasItem(any(ItemRequestDto.class)));
    }

    @Test
    void findByIdTest() {
        Long userId = testSet.getUserId();
        ItemRequestDto dto = service.create(testSet.getDtoCreate(), userId);

        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.findById(66L, 0L));
        assertThat(ex.getId(), equalTo(66L));

        ItemRequestDto foundDto = service.findById(userId, dto.getId());
        assertThat(foundDto.getDescription(), equalTo(dto.getDescription()));
        assertTrue(foundDto.equals(dto));
    }
}
