package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoBrief;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.common.errors.BadRequestException;
import ru.practicum.shareit.common.errors.NotFoundException;
import ru.practicum.shareit.dataSet.BookingTestSet;
import ru.practicum.shareit.dataSet.UserTestSet;

import javax.transaction.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest(
        properties = "db.name=test",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingServiceTests {
    private final BookingService service;
    private final BookingMapper mapper;
    private final BookingTestSet testSet = new BookingTestSet();
    private final UserTestSet userTestSet = new UserTestSet();

    @Test
    void createTest() {
        Long userId = testSet.getUserId();
        NotFoundException ex = assertThrows(NotFoundException.class,
                () -> service.create(testSet.getDtoCreate(), userId));

        Long anotherUserId = testSet.getAnotherUserId();
        BookingDto dto = service.create(testSet.getDtoCreate(), anotherUserId);
        assertThat(dto.getId(), notNullValue());
        assertThat(dto.getBooker().getId(), equalTo(anotherUserId));
    }

    @Test
    void approveBookingTest() {
        Long userId = testSet.getUserId();
        Long bookingId = testSet.getDto().getId();
        BookingDto dtoApproved = service.approveBooking(bookingId, userId, true);
        assertThat(dtoApproved.getStatus(), equalTo(BookingStatus.APPROVED));

        NotFoundException notFoundException;
        notFoundException = assertThrows(NotFoundException.class,
                () -> service.approveBooking(66L, userId, true));

        notFoundException = assertThrows(NotFoundException.class,
                () -> service.approveBooking(bookingId, 66L, true));

        BadRequestException badRequestException = assertThrows(BadRequestException.class,
                () -> service.approveBooking(bookingId, userId, true));
    }

    @Test
    void findAllOfBookerTest() {
        Long userId = testSet.getUserId();
        List<BookingDto> dtoList = service.findAllOfBooker(userId, BookingStatus.ALL, 0, 20);
        assertThat(dtoList, hasSize(2));

        dtoList = service.findAllOfBooker(userId, BookingStatus.PAST, 0, 20);
        assertThat(dtoList, hasSize(2));

        dtoList = service.findAllOfBooker(userId, BookingStatus.FUTURE, 0, 20);
        assertThat(dtoList, hasSize(0));

        BadRequestException badRequestException = assertThrows(BadRequestException.class,
                () -> service.findAllOfBooker(userId, BookingStatus.PAST, 0, 0));
    }

    @Test
    void findAllOfOwnerTest() {
        Long userId = testSet.getUserId();
        List<BookingDto> dtoList = service.findAllOfOwner(userId, BookingStatus.ALL, 0, 20);
        assertThat(dtoList, hasSize(2));

        dtoList = service.findAllOfBooker(userId, BookingStatus.CURRENT, 0, 20);
        assertThat(dtoList, hasSize(0));

        dtoList = service.findAllOfBooker(userId, BookingStatus.REJECTED, 0, 20);
        assertThat(dtoList, hasSize(0));

        dtoList = service.findAllOfBooker(userId, BookingStatus.WAITING, 0, 20);
        assertThat(dtoList, hasSize(1));
    }

    @Test
    void findByIdTest() {
        Long userId = testSet.getUserId();
        BookingDto dto = testSet.getDto();
        BookingDto dtoFound = service.findById(userId, dto.getId());
        assertThat(dto.getId(), equalTo(dtoFound.getId()));

        Booking booking = mapper.fromDto(dtoFound);
        assertThat(booking.getId(), equalTo(dto.getId()));

        booking.setBooker(userTestSet.getUser());
        BookingDtoBrief dtoBrief = mapper.toDtoBrief(booking);
        assertThat(dtoBrief.getId(), equalTo(booking.getId()));

        NotFoundException notFoundException;
        notFoundException = assertThrows(NotFoundException.class,
                () -> service.findById(66L, dto.getId()));

        notFoundException = assertThrows(NotFoundException.class,
                () -> service.findById(userId, 66L));
    }
}
