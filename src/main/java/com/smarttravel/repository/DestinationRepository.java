package com.smarttravel.repository;

import com.smarttravel.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Integer> {

    @Query("SELECT d FROM Destination d WHERE " +
           "(:country IS NULL OR d.country = :country) AND " +
           "(:travelType IS NULL OR d.travelType = :travelType) AND " +
           "(:budgetCategory IS NULL OR d.budgetCategory = :budgetCategory) AND " +
           "(:city IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%', :city, '%')))")
    List<Destination> filterDestinations(
            @Param("country") String country,
            @Param("travelType") Destination.TravelType travelType,
            @Param("budgetCategory") Destination.BudgetCategory budgetCategory,
            @Param("city") String city);

    @Query("SELECT d FROM Destination d ORDER BY d.averageRating DESC")
    List<Destination> findTopRatedDestinations();

    @Query("SELECT DISTINCT d.country FROM Destination d ORDER BY d.country")
    List<String> findDistinctCountries();

    @Query("SELECT DISTINCT d.name FROM Destination d WHERE d.country = :country ORDER BY d.name")
    List<String> findNamesByCountry(@Param("country") String country);
}
