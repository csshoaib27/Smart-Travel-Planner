package com.smarttravel.repository;

import com.smarttravel.model.ItineraryDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ItineraryDayRepository extends JpaRepository<ItineraryDay, Integer> {
    List<ItineraryDay> findByItineraryId(Integer itineraryId);

    @Query("SELECT id FROM ItineraryDay id WHERE id.itineraryId = :itineraryId ORDER BY id.dayNumber ASC")
    List<ItineraryDay> findByItineraryIdOrderByDay(@Param("itineraryId") Integer itineraryId);
}
