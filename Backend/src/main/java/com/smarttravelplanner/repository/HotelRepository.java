package com.smarttravelplanner.repository;

import com.smarttravelplanner.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByDestinationId(Long destinationId);
    
    List<Hotel> findByCity(String city);
    
    @Query("SELECT h FROM Hotel h WHERE h.pricePerNight BETWEEN :minPrice AND :maxPrice")
    List<Hotel> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);
    
    @Query("SELECT h FROM Hotel h WHERE h.rating >= :minRating")
    List<Hotel> findByMinimumRating(@Param("minRating") Double minRating);
    
    List<Hotel> findByDestinationIdAndPricePerNightBetween(Long destinationId, Double minPrice, Double maxPrice);
}
