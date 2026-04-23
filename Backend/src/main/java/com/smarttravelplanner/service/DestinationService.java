package com.smarttravelplanner.service;

import com.smarttravelplanner.dto.DestinationDTO;
import com.smarttravelplanner.entity.Destination;
import com.smarttravelplanner.repository.DestinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DestinationService {
    private final DestinationRepository destinationRepository;

    public List<DestinationDTO> getAllDestinations() {
        return destinationRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DestinationDTO getDestinationById(Long id) {
        return destinationRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<DestinationDTO> getDestinationsByBudget(String budget) {
        try {
            Destination.BudgetType budgetType = Destination.BudgetType.valueOf(budget.toUpperCase());
            return destinationRepository.findByBudget(budgetType)
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            return List.of();
        }
    }

    public List<DestinationDTO> getDestinationsByBudgetRange(Integer minCost, Integer maxCost) {
        return destinationRepository.findByBudgetRange(minCost, maxCost)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<DestinationDTO> getDestinationsByMinimumRating(Double minRating) {
        return destinationRepository.findByMinimumRating(minRating)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<DestinationDTO> searchDestinations(String query) {
        return destinationRepository.findByCountryContainingIgnoreCaseOrCityContainingIgnoreCase(query, query)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DestinationDTO saveDestination(DestinationDTO dto) {
        Destination destination = convertToEntity(dto);
        Destination saved = destinationRepository.save(destination);
        return convertToDTO(saved);
    }

    public DestinationDTO updateDestination(Long id, DestinationDTO dto) {
        return destinationRepository.findById(id)
                .map(existing -> {
                    existing.setName(dto.getName());
                    existing.setDescription(dto.getDescription());
                    existing.setCity(dto.getCity());
                    existing.setRating(dto.getRating());
                    existing.setCostPerDay(dto.getCostPerDay());
                    Destination updated = destinationRepository.save(existing);
                    return convertToDTO(updated);
                })
                .orElse(null);
    }

    public void deleteDestination(Long id) {
        destinationRepository.deleteById(id);
    }

    private DestinationDTO convertToDTO(Destination destination) {
        return DestinationDTO.builder()
                .id(destination.getId())
                .name(destination.getName())
                .description(destination.getDescription())
                .country(destination.getCountry())
                .city(destination.getCity())
                .region(destination.getRegion())
                .latitude(destination.getLatitude())
                .longitude(destination.getLongitude())
                .budget(destination.getBudget().toString())
                .temperature(destination.getTemperature())
                .bestTimeToVisit(destination.getBestTimeToVisit())
                .distance(destination.getDistance())
                .rating(destination.getRating())
                .reviewCount(destination.getReviewCount())
                .interests(destination.getInterests())
                .image(destination.getImage())
                .travelTime(destination.getTravelTime())
                .costPerDay(destination.getCostPerDay())
                .activities(destination.getActivities())
                .safetyRating(destination.getSafetyRating())
                .build();
    }

    private Destination convertToEntity(DestinationDTO dto) {
        return Destination.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .country(dto.getCountry())
                .city(dto.getCity())
                .region(dto.getRegion())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .budget(Destination.BudgetType.valueOf(dto.getBudget().toUpperCase()))
                .temperature(dto.getTemperature())
                .bestTimeToVisit(dto.getBestTimeToVisit())
                .distance(dto.getDistance())
                .rating(dto.getRating())
                .reviewCount(dto.getReviewCount() != null ? dto.getReviewCount() : 0)
                .interests(dto.getInterests())
                .image(dto.getImage())
                .travelTime(dto.getTravelTime())
                .costPerDay(dto.getCostPerDay())
                .activities(dto.getActivities())
                .safetyRating(dto.getSafetyRating())
                .build();
    }
}
