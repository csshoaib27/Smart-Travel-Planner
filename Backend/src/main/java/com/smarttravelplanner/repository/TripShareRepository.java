package com.smarttravelplanner.repository;

import com.smarttravelplanner.entity.TripShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TripShareRepository extends JpaRepository<TripShare, Long> {
    List<TripShare> findByTripId(Long tripId);
    List<TripShare> findBySharedWithEmail(String email);
}
