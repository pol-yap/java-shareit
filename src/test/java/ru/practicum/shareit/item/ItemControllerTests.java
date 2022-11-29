package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.dataSet.ItemTestSet;
import ru.practicum.shareit.item.dto.CommentDtoCreate;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoCreate;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = {ItemController.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ExtendWith(MockitoExtension.class)
public class ItemControllerTests {
    private final MockMvc mvc;
    private final ObjectMapper jsonMapper;

    @MockBean
    private final ItemService service;

    private final ItemTestSet testSet = new ItemTestSet();

    @Test
    void finAllTest() throws Exception {
        Long userId = testSet.getUserId();
        Mockito.when(service.findAllByOwner(anyLong())).thenReturn(List.of(testSet.getDto()));

        mvc.perform(get("/items")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("X-Sharer-User-Id", userId));


        verify(service).findAllByOwner(userId);
    }

    @Test
    void createTest() throws Exception {
        ItemDtoCreate dtoCreate = testSet.getDtoCreate();
        Mockito.when(service.create(any(), anyLong())).thenReturn(testSet.getDto());
        Long userId = testSet.getUserId();

        mvc.perform(post("/items")
                .content(jsonMapper.writeValueAsString(dtoCreate))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("X-Sharer-User-Id", userId));


        verify(service).create(dtoCreate, userId);
    }

    @Test
    void updateTest() throws Exception {
        ItemDto dto = testSet.getDto();
        Mockito.when(service.update(anyLong(), any(), anyLong())).thenReturn(dto);
        Long userId = testSet.getUserId();

        mvc.perform(patch("/items/" + dto.getId())
                .content(jsonMapper.writeValueAsString(dto))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("X-Sharer-User-Id", userId));

        verify(service).update(dto.getId(), dto, userId);
    }

    @Test
    void findByIdTest() throws Exception {
        Long userId = testSet.getUserId();
        ItemDto dto = testSet.getDto();
        Long itemId = dto.getId();
        Mockito.when(service.findById(anyLong(), anyLong())).thenReturn(dto);

        mvc.perform(get("/items/" + itemId)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("X-Sharer-User-Id", userId));


        verify(service).findById(itemId, userId);
    }

    @Test
    void searchTest() throws Exception {
        Long userId = testSet.getUserId();
        Mockito.when(service.search(anyString())).thenReturn(List.of(testSet.getDto()));
        String criteria = "test";

        mvc.perform(get("/items/search?text=" + criteria)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("X-Sharer-User-Id", userId));

        verify(service).search(criteria);
    }

    @Test
    void addCommentTest() throws Exception {
        CommentDtoCreate dtoCreate = testSet.getCommentDtoCreate();
        Long itemId = 1L;
        Mockito.when(service.addComment(anyLong(), anyLong(), any())).thenReturn(testSet.getCommentDto());
        Long userId = testSet.getUserId();

        mvc.perform(post("/items/" + itemId + "/comment")
                .content(jsonMapper.writeValueAsString(dtoCreate))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("X-Sharer-User-Id", userId));

        verify(service).addComment(itemId, userId, dtoCreate);
    }
}