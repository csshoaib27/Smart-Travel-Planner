package com.smarttravel.repository;

import com.smarttravel.model.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItineraryRepository extends JpaRepository<Itinerary, Integer> {
    List<Itinerary> findByUserId(Integer userId);
    List<Itinerary> findByIsPublic(Boolean isPublic);
    List<Itinerary> findByPackageType(Itinerary.PackageType packageType);

    @Query("SELECT i FROM Itinerary i WHERE i.userId = :userId ORDER BY i.createdAt DESC")
    List<Itinerary> findUserItinerariesByDate(@Param("userId") Integer userId);
}
