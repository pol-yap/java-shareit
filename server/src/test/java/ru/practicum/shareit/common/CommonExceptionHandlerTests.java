package ru.practicum.shareit.common;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.common.errors.BadRequestException;
import ru.practicum.shareit.common.errors.ErrorMessage;
import ru.practicum.shareit.common.errors.ForbiddenException;
import ru.practicum.shareit.common.errors.NotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CommonExceptionHandlerTests {
    private final CommonExceptionHandler handler;

    @Test
    void handleNotFoundTest() {
        ResponseEntity<ErrorMessage> responseEntity = handler.handleNotFound(new NotFoundException(1, "something"));
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }

    @Test
    void handleBadRequestTest() {
        ResponseEntity<ErrorMessage> responseEntity = handler.handleBadRequest(new BadRequestException("some message"));
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    void handleForbiddenTest() {
        ResponseEntity<ErrorMessage> responseEntity = handler.handleForbidden(new ForbiddenException("some message"));
        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.FORBIDDEN));
    }

    @Test
    void handleConstraintViolationTest() {
        ResponseEntity<ErrorMessage> responseEntity = handler.handleConstraintViolation(
                new DataIntegrityViolationException("some message"));

        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.CONFLICT));
    }
}
