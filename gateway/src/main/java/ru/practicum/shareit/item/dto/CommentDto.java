package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentDto {

    private Long id;

    @NotBlank
    private String text;

    private String authorName;

    private LocalDateTime created;
}
