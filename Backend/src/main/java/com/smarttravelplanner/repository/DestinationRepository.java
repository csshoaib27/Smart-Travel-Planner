package com.smarttravelplanner.repository;

import com.smarttravelplanner.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    List<Destination> findByBudget(Destination.BudgetType budget);
    
    List<Destination> findByCity(String city);
    
    List<Destination> findByCountry(String country);
    
    @Query("SELECT d FROM Destination d WHERE d.rating >= :minRating")
    List<Destination> findByMinimumRating(@Param("minRating") Double minRating);
    
    @Query("SELECT d FROM Destination d WHERE d.costPerDay BETWEEN :minCost AND :maxCost")
    List<Destination> findByBudgetRange(@Param("minCost") Integer minCost, @Param("maxCost") Integer maxCost);
    
    List<Destination> findByCountryContainingIgnoreCaseOrCityContainingIgnoreCase(String country, String city);
}
