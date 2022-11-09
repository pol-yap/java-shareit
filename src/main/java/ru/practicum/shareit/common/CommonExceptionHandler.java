package ru.practicum.shareit.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.shareit.common.errors.BadRequestException;
import ru.practicum.shareit.common.errors.ForbiddenException;
import ru.practicum.shareit.common.errors.NotFoundException;
import ru.practicum.shareit.common.errors.NotUniqueException;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String  handleNotFound(NotFoundException e) {
        String message = String.format("Не найден %s с id=%d", e.getEntity(), e.getId());
        log.warn(message);

        return message;
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequest(BadRequestException e) {
        log.warn(e.getMessage());

        return e.getMessage();
    }

    @ExceptionHandler(NotUniqueException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleNotUnique(NotUniqueException e) {
        log.warn(e.getMessage());

        return e.getMessage();
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleForbidden(ForbiddenException e) {
        log.warn(e.getMessage());

        return e.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                          .getAllErrors()
                          .stream()
                          .map(DefaultMessageSourceResolvable::getDefaultMessage)
                          .collect(Collectors.toList())
                          .toString();
        log.warn(message);

        return message;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleConstraintViolation(DataIntegrityViolationException e) {
        log.warn(e.getMessage());

        return "Data constraint violation";
    }
}
