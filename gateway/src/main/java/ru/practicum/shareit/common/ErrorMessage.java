package ru.practicum.shareit.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ErrorMessage {
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String error;
    private String path;
}
