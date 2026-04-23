package com.smarttravelplanner.repository;

import com.smarttravelplanner.entity.TraditionalFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TraditionalFoodRepository extends JpaRepository<TraditionalFood, Long> {
    List<TraditionalFood> findByDestinationId(Long destinationId);
    List<TraditionalFood> findByDestinationIdAndCuisineType(Long destinationId, String cuisineType);
}
