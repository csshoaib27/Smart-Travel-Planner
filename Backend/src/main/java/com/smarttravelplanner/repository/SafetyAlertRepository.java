package com.smarttravelplanner.repository;

import com.smarttravelplanner.entity.SafetyAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SafetyAlertRepository extends JpaRepository<SafetyAlert, Long> {
    List<SafetyAlert> findByDestinationId(Long destinationId);
    List<SafetyAlert> findByDestinationIdAndType(Long destinationId, SafetyAlert.AlertType type);
}
