package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.practicum.shareit.common.errors.NotFoundException;
import ru.practicum.shareit.dataSet.UserTestSet;
import ru.practicum.shareit.user.dto.UserDto;

import javax.transaction.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest(
        properties = "db.name=test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(scripts={"classpath:schema.sql", "classpath:data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserServiceTests {
    private final UserService service;
    private final UserTestSet testSet = new UserTestSet();

    @Test
    void createTest() {
        UserDto dto = service.create(testSet.getDtoCreate());

        assertThat(dto.getId(), notNullValue());
        assertThat(dto.getName(), notNullValue());
        assertThat(dto.getEmail(), equalTo(testSet.getDto().getEmail()));
    }

    @Test
    void updateTest() {
        UserDto dto = service.create(testSet.getDtoCreate());
        dto.setName("new name");
        UserDto updatedDto = service.update(dto.getId(), dto);

        assertThat(dto.getName(), equalTo(updatedDto.getName()));
    }

    @Test
    void findByIdTest() {
        UserDto dto = service.create(testSet.getDtoCreate());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.findById(66L));
        assertThat(ex.getId(), equalTo(66L));

        UserDto foundDto = service.findById(dto.getId());
        assertThat(foundDto.getName(), equalTo(dto.getName()));
    }

    @Test
    void findAllTest() {
        List<UserDto> dtoList = service.findAll();
        assertThat(dtoList, hasSize(2));
    }

    @Test
    void deleteByIdTest() {
        service.deleteById(2L);
        List<UserDto> dtoList = service.findAll();
        assertThat(dtoList, hasSize(1));
    }

    @Test
    void throwIfNoUserTest() {
        NotFoundException ex = assertThrows(NotFoundException.class, () -> service.throwIfNoUser(66L));
        assertThat(ex.getId(), equalTo(66L));
    }
}
