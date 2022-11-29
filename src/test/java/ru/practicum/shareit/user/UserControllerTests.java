package ru.practicum.shareit.user;

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
import ru.practicum.shareit.dataSet.UserTestSet;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoCreate;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = {UserController.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {
    private final MockMvc mvc;
    private final ObjectMapper jsonMapper;

    @MockBean
    private final UserService service;

    private final UserTestSet testSet = new UserTestSet();

    @Test
    void createTest() throws Exception {
        UserDtoCreate dtoCreate = testSet.getDtoCreate();
        Mockito.when(service.create(any())).thenReturn(testSet.getDto());

        mvc.perform(post("/users")
                .content(jsonMapper.writeValueAsString(testSet.getDtoCreate()))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));


        verify(service).create(dtoCreate);
    }

    @Test
    void getAllTest() throws Exception {
        Mockito.when(service.findAll()).thenReturn(List.of(testSet.getDto()));

        mvc.perform(get("/users")
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));


        verify(service).findAll();
    }

    @Test
    void findByIdTest() throws Exception {
        Long userId = testSet.getUser().getId();
        Mockito.when(service.findById(anyLong())).thenReturn(testSet.getDto());

        mvc.perform(get("/users/" + userId)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));


        verify(service).findById(userId);
    }

    @Test
    void updateTest() throws Exception {
        UserDto dto = testSet.getDto();
        Mockito.when(service.update(anyLong(), any())).thenReturn(dto);

        mvc.perform(patch("/users/" + dto.getId())
                .content(jsonMapper.writeValueAsString(dto))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        verify(service).update(dto.getId(), dto);
    }


    @Test
    void deleteTest() throws Exception {
        Long userId = testSet.getUser().getId();

        mvc.perform(delete("/users/" + userId)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));


        verify(service).deleteById(userId);
    }
}
