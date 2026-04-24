package com.smarttravel.repository;

import com.smarttravel.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// ==================== USER REPOSITORY ====================
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    List<User> findByIsAdmin(Boolean isAdmin);
}

// ==================== DESTINATION REPOSITORY ====================
@Repository
public interface DestinationRepository extends JpaRepository<Destination, Integer> {
    List<Destination> findByCountry(String country);
    List<Destination> findByTravelType(Destination.TravelType travelType);
    List<Destination> findByBudgetCategory(Destination.BudgetCategory budgetCategory);

    @Query("SELECT d FROM Destination d WHERE " +
           "d.country LIKE %:country% AND " +
           "d.travelType = :travelType AND " +
           "d.budgetCategory = :budgetCategory")
    List<Destination> searchDestinations(@Param("country") String country,
                                         @Param("travelType") Destination.TravelType travelType,
                                         @Param("budgetCategory") Destination.BudgetCategory budgetCategory);

    @Query("SELECT d FROM Destination d ORDER BY d.averageRating DESC")
    List<Destination> findTopRatedDestinations();
}

// ==================== HOTEL REPOSITORY ====================
@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    List<Hotel> findByDestinationId(Integer destinationId);

    @Query("SELECT h FROM Hotel h WHERE h.destinationId = :destinationId " +
           "AND h.pricePerNight BETWEEN :minPrice AND :maxPrice")
    List<Hotel> findByDestinationAndPriceRange(@Param("destinationId") Integer destinationId,
                                               @Param("minPrice") java.math.BigDecimal minPrice,
                                               @Param("maxPrice") java.math.BigDecimal maxPrice);

    @Query("SELECT h FROM Hotel h WHERE h.destinationId = :destinationId " +
           "ORDER BY h.averageRating DESC")
    List<Hotel> findTopHotelsInDestination(@Param("destinationId") Integer destinationId);
}

// ==================== BOOKING REPOSITORY ====================
@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByUserId(Integer userId);
    List<Booking> findByHotelId(Integer hotelId);
    List<Booking> findByStatus(Booking.BookingStatus status);

    @Query("SELECT b FROM Booking b WHERE b.hotelId = :hotelId " +
           "AND ((b.checkInDate <= :checkOutDate AND b.checkOutDate >= :checkInDate))")
    List<Booking> findConflictingBookings(@Param("hotelId") Integer hotelId,
                                          @Param("checkInDate") LocalDate checkInDate,
                                          @Param("checkOutDate") LocalDate checkOutDate);
}

// ==================== ITINERARY REPOSITORY ====================
@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, Integer> {
    List<Itinerary> findByUserId(Integer userId);
    List<Itinerary> findByIsPublic(Boolean isPublic);
    List<Itinerary> findByPackageType(Itinerary.PackageType packageType);

    @Query("SELECT i FROM Itinerary i WHERE i.userId = :userId " +
           "ORDER BY i.createdAt DESC")
    List<Itinerary> findUserItinerariesByDate(@Param("userId") Integer userId);
}

// ==================== ITINERARY DAY REPOSITORY ====================
@Repository
public interface ItineraryDayRepository extends JpaRepository<ItineraryDay, Integer> {
    List<ItineraryDay> findByItineraryId(Integer itineraryId);

    @Query("SELECT id FROM ItineraryDay id WHERE id.itineraryId = :itineraryId " +
           "ORDER BY id.dayNumber ASC")
    List<ItineraryDay> findByItineraryIdOrderByDay(@Param("itineraryId") Integer itineraryId);
}

// ==================== REVIEW REPOSITORY ====================
@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByDestinationId(Integer destinationId);
    List<Review> findByHotelId(Integer hotelId);
    List<Review> findByUserId(Integer userId);
    List<Review> findByReviewType(Review.ReviewType reviewType);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.destinationId = :destinationId")
    Double getAverageRatingForDestination(@Param("destinationId") Integer destinationId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.hotelId = :hotelId")
    Double getAverageRatingForHotel(@Param("hotelId") Integer hotelId);
}

// ==================== SEARCH HISTORY REPOSITORY ====================
@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Integer> {
    List<SearchHistory> findByUserId(Integer userId);

    @Query("SELECT sh FROM SearchHistory sh WHERE sh.userId = :userId " +
           "ORDER BY sh.searchDate DESC")
    List<SearchHistory> findUserSearchHistory(@Param("userId") Integer userId);
}

// ==================== COST BREAKDOWN REPOSITORY ====================
@Repository
public interface CostBreakdownRepository extends JpaRepository<CostBreakdown, Integer> {
    List<CostBreakdown> findByItineraryId(Integer itineraryId);

    @Query("SELECT SUM(cb.amount) FROM CostBreakdown cb WHERE cb.itineraryId = :itineraryId")
    java.math.BigDecimal getTotalCostForItinerary(@Param("itineraryId") Integer itineraryId);
}

// ==================== PAYMENT SPLIT REPOSITORY ====================
@Repository
public interface PaymentSplitRepository extends JpaRepository<PaymentSplit, Integer> {
    List<PaymentSplit> findByBookingId(Integer bookingId);
    List<PaymentSplit> findByUserId(Integer userId);
    List<PaymentSplit> findByStatus(PaymentSplit.PaymentStatus status);
}
