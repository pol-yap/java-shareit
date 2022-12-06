package ru.practicum.shareit.dataSet;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoCreate;
import ru.practicum.shareit.item.dto.ItemDtoBrief;
import ru.practicum.shareit.user.dto.UserDtoBrief;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookingTestSet {
    private BookingDto dto;
    private BookingDtoCreate dtoCreate;
    private final Long userId = 1L;
    private final Long anotherUserId = 2L;
    private final ItemTestSet itemTestSet = new ItemTestSet();
    private final UserTestSet userTestSet = new UserTestSet();

    public BookingTestSet() {
        dto = new BookingDto();
        dto.setId(1L);
        dto.setItem(new ItemDtoBrief(itemTestSet.getItem()));
        dto.setBooker(new UserDtoBrief(userTestSet.getUser()));
        dto.setStart(LocalDateTime.now().plusDays(1));
        dto.setEnd(LocalDateTime.now().plusDays(2));
        dto.setStatus(BookingStatus.APPROVED);

        dtoCreate = new BookingDtoCreate();
        dtoCreate.setItemId(dto.getItem().getId());
        dtoCreate.setStart(dto.getStart());
        dtoCreate.setEnd(dto.getEnd());
    }
}
