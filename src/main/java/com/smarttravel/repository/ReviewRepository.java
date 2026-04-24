package com.smarttravel.repository;

import com.smarttravel.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

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
