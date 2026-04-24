package com.smarttravel.repository;

import com.smarttravel.model.CostBreakdown;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CostBreakdownRepository extends JpaRepository<CostBreakdown, Integer> {
    List<CostBreakdown> findByItineraryId(Integer itineraryId);

    @Query("SELECT SUM(cb.amount) FROM CostBreakdown cb WHERE cb.itineraryId = :itineraryId")
    BigDecimal getTotalCostForItinerary(@Param("itineraryId") Integer itineraryId);
}
