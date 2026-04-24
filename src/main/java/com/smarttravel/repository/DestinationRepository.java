package com.smarttravel.repository;

import com.smarttravel.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Integer> {
    List<Destination> findByCountry(String country);
    List<Destination> findByTravelType(Destination.TravelType travelType);
    List<Destination> findByBudgetCategory(Destination.BudgetCategory budgetCategory);

    @Query("SELECT d FROM Destination d WHERE d.country LIKE %:country% AND d.travelType = :travelType AND d.budgetCategory = :budgetCategory")
    List<Destination> searchDestinations(@Param("country") String country,
                                         @Param("travelType") Destination.TravelType travelType,
                                         @Param("budgetCategory") Destination.BudgetCategory budgetCategory);

    @Query("SELECT d FROM Destination d ORDER BY d.averageRating DESC")
    List<Destination> findTopRatedDestinations();
}
