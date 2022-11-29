package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import ru.practicum.shareit.dataSet.ItemRequestTestSet;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ItemRequestController.class, ItemRequestService.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ExtendWith(MockitoExtension.class)
public class ItemRequestControllerTests {
    private final MockMvc mvc;
    private final ObjectMapper jsonMapper;

    @MockBean
    private final ItemRequestService service;

    private final ItemRequestTestSet testSet = new ItemRequestTestSet();


    @BeforeEach
    void setUp(WebApplicationContext wac) {
    }

    @Test
    void createTest() throws Exception {
        ItemRequestDto dto = testSet.getDto();
        Mockito.when(service.create(any(), anyLong())).thenReturn(dto);

        mvc.perform(post("/requests")
                .content(jsonMapper.writeValueAsString(testSet.getDtoCreate()))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("X-Sharer-User-Id",testSet.getUserId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(dto.getId()), Long.class))
                .andExpect(jsonPath("$.description", is(dto.getDescription())));
    }

    @Test
    void getOwnTest() throws Exception {
        ItemRequestDto dto = testSet.getDto();
        Mockito.when(service.getOwn(anyLong())).thenReturn(List.of(dto));

        mvc.perform(get("/requests")
                   .characterEncoding(StandardCharsets.UTF_8)
                   .accept(MediaType.APPLICATION_JSON)
                   .header("X-Sharer-User-Id",testSet.getUserId()))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$", hasSize(1)))
           .andExpect(jsonPath("$[0].id", is(dto.getId()), Long.class))
           .andExpect(jsonPath("$[0].description", is(dto.getDescription())));
    }

    @Test
    void getAllTest() throws Exception {
        ItemRequestDto dto = testSet.getDto();
        Mockito.when(service.getAll(anyLong(), anyInt(), anyInt())).thenReturn(List.of(dto));

        mvc.perform(get("/requests/all")
                   .characterEncoding(StandardCharsets.UTF_8)
                   .accept(MediaType.APPLICATION_JSON)
                   .header("X-Sharer-User-Id",66))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$", hasSize(1)))
           .andExpect(jsonPath("$[0].id", is(dto.getId()), Long.class))
           .andExpect(jsonPath("$[0].description", is(dto.getDescription())));
    }

    @Test
    void findByIdTest() throws Exception {
        ItemRequestDto dto = testSet.getDto();
        Mockito.when(service.findById(anyLong(), anyLong())).thenReturn(dto);

        mvc.perform(get("/requests/" + testSet.getUserId())
                   .characterEncoding(StandardCharsets.UTF_8)
                   .accept(MediaType.APPLICATION_JSON)
                   .header("X-Sharer-User-Id",testSet.getUserId()))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id", is(dto.getId()), Long.class))
           .andExpect(jsonPath("$.description", is(dto.getDescription())));
    }
}
