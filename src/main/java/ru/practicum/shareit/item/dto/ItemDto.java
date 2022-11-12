package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.dto.BookingDtoBrief;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ItemDto {

    private Long id;

    @NotBlank(message = "Item name shouldn't be empty")
    private String name;

    @NotBlank(message = "Item description shouldn't be empty")
    private String description;

    private Boolean available;

    private Long ownerId;

    private BookingDtoBrief nextBooking;

    private BookingDtoBrief lastBooking;
}
