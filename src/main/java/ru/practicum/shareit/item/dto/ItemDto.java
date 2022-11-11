package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookingForItemDto;

import javax.persistence.Transient;
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

    private BookingForItemDto nextBooking;

    private BookingForItemDto lastBooking;
}
