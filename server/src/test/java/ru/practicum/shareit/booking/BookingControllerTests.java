package ru.practicum.shareit.booking;

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
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreate;
import ru.practicum.shareit.dataSet.BookingTestSet;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {BookingController.class})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ExtendWith(MockitoExtension.class)
public class BookingControllerTests {

    private final MockMvc mvc;

    private final ObjectMapper jsonMapper;

    @MockBean
    private final BookingService service;

    private final BookingTestSet testSet = new BookingTestSet();

    @Test
    void createTest() throws Exception {
        BookingDtoCreate dtoCreate = testSet.getDtoCreate();
        Mockito.when(service.create(any(), anyLong())).thenReturn(testSet.getDto());
        Long userId = testSet.getUserId();

        mvc.perform(post("/bookings")
                .content(jsonMapper.writeValueAsString(dtoCreate))
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("X-Sharer-User-Id", userId))
           .andExpect(status().isCreated());

        verify(service).create(dtoCreate, userId);
    }

    @Test
    void findAllOfBookerTest() throws Exception {
        Long userId = testSet.getUserId();
        BookingDto dto = testSet.getDto();
        Mockito.when(service.findAllOfBooker(anyLong(), any(), anyInt(), anyInt())).thenReturn(List.of(dto));

        mvc.perform(get("/bookings")
                   .characterEncoding(StandardCharsets.UTF_8)
                   .accept(MediaType.APPLICATION_JSON)
                   .header("X-Sharer-User-Id", userId))
           .andExpect(status().isOk());

        verify(service).findAllOfBooker(userId, BookingStatus.ALL, 0, 20);
    }

    @Test
    void updateStateTest() throws Exception {
        Long userId = testSet.getUserId();
        BookingDto dto = testSet.getDto();
        Long itemId = dto.getId();
        Mockito.when(service.approveBooking(anyLong(), anyLong(), anyBoolean())).thenReturn(dto);

        mvc.perform(patch("/bookings/" + itemId + "?approved=true")
                   .characterEncoding(StandardCharsets.UTF_8)
                   .accept(MediaType.APPLICATION_JSON)
                   .header("X-Sharer-User-Id", userId))
           .andExpect(status().isOk());

        verify(service).approveBooking(itemId, userId, true);
    }

    @Test
    void findAllOfOwnerTest() throws Exception {
        Long userId = testSet.getUserId();
        BookingDto dto = testSet.getDto();
        Mockito.when(service.findAllOfOwner(anyLong(), any(), anyInt(), anyInt())).thenReturn(List.of(dto));

        mvc.perform(get("/bookings/owner")
                   .characterEncoding(StandardCharsets.UTF_8)
                   .accept(MediaType.APPLICATION_JSON)
                   .header("X-Sharer-User-Id", userId))
           .andExpect(status().isOk());

        verify(service).findAllOfOwner(userId, BookingStatus.ALL, 0, 20);
    }

    @Test
    void findByIdTest() throws Exception {
        Long userId = testSet.getUserId();
        BookingDto dto = testSet.getDto();
        Long bookingId = dto.getId();
        Mockito.when(service.findById(anyLong(), anyLong())).thenReturn(dto);

        mvc.perform(get("/bookings/" + bookingId)
                .characterEncoding(StandardCharsets.UTF_8)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("X-Sharer-User-Id", userId))
           .andExpect(status().isOk());

        verify(service).findById(bookingId, userId);
    }
}