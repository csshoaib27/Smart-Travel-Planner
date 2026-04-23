package com.smarttravelplanner.repository;

import com.smarttravelplanner.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByDestinationId(Long destinationId);
    List<Review> findByUserId(Long userId);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.destination.id = :destinationId")
    Double getAverageRating(@Param("destinationId") Long destinationId);
}
