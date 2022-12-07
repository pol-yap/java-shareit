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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException e) {
        String message = e.getMessage();

        return handleAndResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getMessage();

        return handleAndResponse(HttpStatus.BAD_REQUEST, message);
    }

    private ResponseEntity<ErrorMessage> handleAndResponse(HttpStatus status, String message) {
        log.warn(message);

        return new ResponseEntity<>(
                ErrorMessage.builder()
                            .status(status.value())
                            .error(message)
                            .build(),
                status);
    }
}
