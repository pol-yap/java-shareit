package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.common.errors.BadRequestException;
import ru.practicum.shareit.common.errors.NotFoundException;
import ru.practicum.shareit.dataSet.ItemRequestTestSet;
import ru.practicum.shareit.dataSet.UserTestSet;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.UserService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@SpringBootTest(
        properties = "db.name=test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemRequestServiceTests {
    private final ItemRequestService service;
    private final UserService userService;
    private final ItemRequestTestSet testSet = new ItemRequestTestSet();
    private final UserTestSet userTestSet = new UserTestSet();

    @Test
    void commonTest() {
        userTestSet.setDto(userService.create(userTestSet.getDtoCreate()));
        Long userId = userTestSet.getDto().getId();

        //create test
        ItemRequestDto dto = service.create(testSet.getDtoCreate(), userId);

        assertThat(dto.getId(), notNullValue());
        assertThat(dto.getCreated(), notNullValue());
        assertThat(dto.getDescription(), equalTo(testSet.getDto().getDescription()));

        //getOwn test
        List<ItemRequestDto> dtoList = service.getOwn(userId);
        assertThat(dtoList, hasSize(1));
        assertThat(dtoList, hasItem(any(ItemRequestDto.class)));

        //getAll test
        Exception ex = assertThrows(BadRequestException.class, () -> service.getAll(userId, 0, 0));
        assertThat(ex.getMessage(), equalTo("Wrong pagination parameter value"));

        dtoList = service.getAll(66L, 0, 20);
        assertThat(dtoList, hasSize(1));
        assertThat(dtoList, hasItem(any(ItemRequestDto.class)));

        //findById test
        ex = assertThrows(NotFoundException.class, () -> service.findById(66L, 0L));
        assertThat(((NotFoundException) ex).getId(), equalTo(66L));

        ItemRequestDto foundDto = service.findById(userId, dto.getId());
        assertThat(foundDto.getDescription(), equalTo(dto.getDescription()));
    }
}
