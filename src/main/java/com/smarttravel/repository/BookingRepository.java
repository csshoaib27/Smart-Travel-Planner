package com.smarttravel.repository;

import com.smarttravel.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByUserId(Integer userId);
    List<Booking> findByHotelId(Integer hotelId);
    List<Booking> findByStatus(Booking.BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.hotelId = :hotelId AND (b.checkInDate <= :checkOutDate AND b.checkOutDate >= :checkInDate)")
    List<Booking> findConflictingBookings(@Param("hotelId") Integer hotelId,
                                          @Param("checkInDate") LocalDate checkInDate,
                                          @Param("checkOutDate") LocalDate checkOutDate);
}
