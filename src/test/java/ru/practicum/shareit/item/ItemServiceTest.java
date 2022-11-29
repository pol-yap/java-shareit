package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.common.errors.BadRequestException;
import ru.practicum.shareit.common.errors.ForbiddenException;
import ru.practicum.shareit.common.errors.NotFoundException;
import ru.practicum.shareit.dataSet.ItemTestSet;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentDtoCreate;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.transaction.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest(
        properties = "db.name=test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemServiceTest {
    private final ItemService service;
    private final ItemTestSet testSet = new ItemTestSet();

    @Test
    void createTest() {
        Long userId = testSet.getUserId();
        ItemDto dto = service.create(testSet.getDtoCreate(), userId);

        assertThat(dto.getId(), notNullValue());
        assertThat(dto.getName(), notNullValue());
        assertThat(dto.getName(), equalTo(testSet.getDto().getName()));
    }

    @Test
    void updateTest() {
        Long userId = testSet.getUserId();
        ItemDto dto = service.create(testSet.getDtoCreate(), userId);
        dto.setName("new name");

        ItemDto updatedDto = service.update(dto.getId(), dto, userId);
        assertThat(dto.getName(), equalTo(updatedDto.getName()));

        ForbiddenException ex = assertThrows(ForbiddenException.class, () -> service.update(dto.getId(), dto, 2L));
        assertThat(ex.getMessage(), equalTo("user is not owner of item"));
    }

    @Test
    void findByIdTest() {
        Long userId = testSet.getUserId();
        ItemDto dto = service.create(testSet.getDtoCreate(), userId);

        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.findById(66L, userId));
        assertThat(ex.getId(), equalTo(66L));

        ItemDto foundDto = service.findById(dto.getId(), userId);
        assertThat(foundDto.getName(), equalTo(dto.getName()));
    }

    @Test
    void findAllByOwnerTest() {
        Long userId = testSet.getUserId();
        List<ItemDto> dtoList = service.findAllByOwner(userId);
        assertThat(dtoList, hasSize(1));
    }

    @Test
    void searchTest() {
        List<ItemDto> dtoList = service.search("uSeF");
        assertThat(dtoList, hasSize(1));

        dtoList = service.search("");
        assertThat(dtoList, hasSize(0));
    }

    @Test
    void addCommentsTest() {
        Long userId = testSet.getUserId();
        CommentDtoCreate commentDtoCreate = testSet.getCommentDtoCreate();
        ItemDto dto = testSet.getDto();

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> service.addComment(dto.getId(), 2L, commentDtoCreate));

        CommentDto commentDto = service.addComment(dto.getId(), userId, commentDtoCreate);
        assertThat(commentDto.getText(), equalTo(commentDtoCreate.getText()));
    }
}