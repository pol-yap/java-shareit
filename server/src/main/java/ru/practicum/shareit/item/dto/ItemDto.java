package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingDtoBrief;

import java.util.List;

@Data
@NoArgsConstructor
public class ItemDto {

    private Long id;

    private String name;

    private String description;

    private Boolean available;

    private Long ownerId;

    private BookingDtoBrief nextBooking;

    private BookingDtoBrief lastBooking;

    private List<CommentDto> comments;

    private Long requestId;
}
