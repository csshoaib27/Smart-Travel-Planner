package com.smarttravel.repository;

import com.smarttravel.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    List<Hotel> findByDestinationId(Integer destinationId);

    @Query("SELECT h FROM Hotel h WHERE h.destinationId = :destinationId AND h.pricePerNight BETWEEN :minPrice AND :maxPrice")
    List<Hotel> findByDestinationAndPriceRange(@Param("destinationId") Integer destinationId,
                                               @Param("minPrice") BigDecimal minPrice,
                                               @Param("maxPrice") BigDecimal maxPrice);

    @Query("SELECT h FROM Hotel h WHERE h.destinationId = :destinationId ORDER BY h.averageRating DESC")
    List<Hotel> findTopHotelsInDestination(@Param("destinationId") Integer destinationId);
}
