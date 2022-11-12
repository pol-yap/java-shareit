package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository  extends JpaRepository<Booking, Long> {

    List<Booking> findByBooker_IdOrderByStartDateDesc(Long id);

    List<Booking> findByBooker_IdAndStatusOrderByStartDateDesc(Long id, BookingStatus status);

    List<Booking> findByBooker_IdAndStartDateAfterOrderByStartDateDesc(Long id, LocalDateTime startDate);

    List<Booking> findByBooker_IdAndEndDateBeforeOrderByStartDateDesc(Long id, LocalDateTime endDate);

    List<Booking> findByItem_OwnerIdOrderByStartDateDesc(Long id);

    List<Booking> findByItem_OwnerIdAndStatusOrderByStartDateDesc(Long id, BookingStatus status);

    List<Booking> findByItem_OwnerIdAndStartDateAfterOrderByStartDateDesc(Long id, LocalDateTime startDate);

    List<Booking> findByItem_OwnerIdAndEndDateBeforeOrderByStartDateDesc(Long id, LocalDateTime startDate);

    @Query("SELECT b FROM Booking b WHERE b.booker.id = :bookerId " +
            "AND b.startDate < :nowDate AND b.endDate > :nowDate")
    List<Booking> findBookersCurrentBookings(@Param("bookerId") Long bookerId,
                                             @Param("nowDate") LocalDateTime nowDate);

    @Query("SELECT b FROM Booking b WHERE b.item.ownerId = :ownerId " +
            "AND b.startDate < :nowDate AND b.endDate > :nowDate")
    List<Booking> findOwnersCurrentBookings(@Param("ownerId") Long ownerId,
                                            @Param("nowDate") LocalDateTime nowDate);

    List<Booking> findByItem_IdAndStatusNot(Long id, BookingStatus status);
}
