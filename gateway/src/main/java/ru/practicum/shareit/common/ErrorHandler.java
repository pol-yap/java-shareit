package ru.practicum.shareit.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler({IllegalArgumentException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorMessage> handleBadRequestExceptions(Exception e) {
        return handleAndResponse(HttpStatus.BAD_REQUEST, e);
    }

    private ResponseEntity<ErrorMessage> handleAndResponse(HttpStatus status, Exception e) {
        log.warn("Something went wrong: {}", e.getMessage(), e);

        return new ResponseEntity<>(
                ErrorMessage.builder()
                            .status(status.value())
                            .error(e.getMessage())
                            .build(),
                status);
    }
}
