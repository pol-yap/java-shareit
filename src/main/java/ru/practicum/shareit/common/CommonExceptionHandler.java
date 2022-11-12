package ru.practicum.shareit.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.shareit.common.errors.*;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFound(NotFoundException e) {
        String message = String.format("Не найден %s с id=%d", e.getEntity(), e.getId());

        return handleAndResponse(HttpStatus.NOT_FOUND, message);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessage> handleBadRequest(BadRequestException e) {
        return handleAndResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

//    @ExceptionHandler(NotUniqueException.class)
//    public ResponseEntity<ErrorMessage> handleNotUnique(NotUniqueException e) {
//        return handleAndResponse(HttpStatus.CONFLICT, e.getMessage());
//    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorMessage> handleForbidden(ForbiddenException e) {
        return handleAndResponse(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                          .getAllErrors()
                          .stream()
                          .map(DefaultMessageSourceResolvable::getDefaultMessage)
                          .collect(Collectors.toList())
                          .toString();

        return handleAndResponse(HttpStatus.BAD_REQUEST, message);
    }

//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<ErrorMessage> handleConstraintViolation(DataIntegrityViolationException e) {
//        return handleAndResponse(HttpStatus.CONFLICT, "Data constraint violation");
//    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> handleRequestParamViolation(MethodArgumentTypeMismatchException e) {
        return handleAndResponse(
                HttpStatus.BAD_REQUEST,
                String.format("Unknown %s: %s", e.getName(), e.getValue())
        );
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorMessage> handleMissingRequestHeader(MissingRequestHeaderException e) {
        String message = "Missing HTTP header: " + e.getHeaderName();
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
