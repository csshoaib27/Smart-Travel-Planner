package com.smarttravel.service;

import com.smarttravel.dto.DestinationDTO;
import com.smarttravel.model.Destination;
import com.smarttravel.repository.DestinationRepository;
import com.smarttravel.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DestinationService {

    private final DestinationRepository destinationRepository;
    private final ReviewRepository reviewRepository;

    public DestinationDTO createDestination(Destination destination) {
        log.info("Creating new destination: {}", destination.getName());
        Destination saved = destinationRepository.save(destination);
        return mapToDTO(saved);
    }

    public DestinationDTO getDestinationById(Integer id) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        // Update rating
        Double avgRating = reviewRepository.getAverageRatingForDestination(id);
        if (avgRating != null) {
            destination.setAverageRating(avgRating);
            destination.setReviewCount(
                    (int) reviewRepository.findByDestinationId(id).size()
            );
            destinationRepository.save(destination);
        }

        return mapToDTO(destination);
    }

    public List<DestinationDTO> getAllDestinations() {
        return destinationRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<DestinationDTO> getDestinationsByCountry(String country) {
        log.info("Fetching destinations for country: {}", country);
        return destinationRepository.findByCountry(country)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<DestinationDTO> getDestinationsByTravelType(String travelType) {
        try {
            Destination.TravelType type = Destination.TravelType.valueOf(travelType);
            return destinationRepository.findByTravelType(type)
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid travel type: " + travelType);
        }
    }

    public List<DestinationDTO> getDestinationsByBudget(String budgetCategory) {
        try {
            Destination.BudgetCategory category = Destination.BudgetCategory.valueOf(budgetCategory);
            return destinationRepository.findByBudgetCategory(category)
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid budget category: " + budgetCategory);
        }
    }

    public List<DestinationDTO> searchDestinations(String country, String travelType, String budgetCategory) {
        try {
            Destination.TravelType type = Destination.TravelType.valueOf(travelType);
            Destination.BudgetCategory category = Destination.BudgetCategory.valueOf(budgetCategory);

            return destinationRepository.searchDestinations(country, type, category)
                    .stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid filters provided");
        }
    }

    public List<DestinationDTO> getTopRatedDestinations() {
        log.info("Fetching top rated destinations");
        return destinationRepository.findTopRatedDestinations()
                .stream()
                .map(this::mapToDTO)
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<DestinationDTO> getDestinationsByTemperatureRange(Double minTemp, Double maxTemp) {
        return destinationRepository.findAll()
                .stream()
                .filter(d -> d.getTemperatureAvg() != null &&
                        d.getTemperatureAvg() >= minTemp &&
                        d.getTemperatureAvg() <= maxTemp)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public DestinationDTO updateDestination(Integer id, Destination updated) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        destination.setName(updated.getName() != null ? updated.getName() : destination.getName());
        destination.setDescription(updated.getDescription() != null ? updated.getDescription() : destination.getDescription());
        destination.setBestTimeToVisit(updated.getBestTimeToVisit() != null ? updated.getBestTimeToVisit() : destination.getBestTimeToVisit());
        destination.setTemperatureAvg(updated.getTemperatureAvg() != null ? updated.getTemperatureAvg() : destination.getTemperatureAvg());
        destination.setImageUrl(updated.getImageUrl() != null ? updated.getImageUrl() : destination.getImageUrl());

        Destination saved = destinationRepository.save(destination);
        return mapToDTO(saved);
    }

    public void deleteDestination(Integer id) {
        destinationRepository.deleteById(id);
        log.info("Destination deleted: {}", id);
    }

    private DestinationDTO mapToDTO(Destination destination) {
        return DestinationDTO.builder()
                .destinationId(destination.getDestinationId())
                .name(destination.getName())
                .country(destination.getCountry())
                .description(destination.getDescription())
                .temperatureAvg(destination.getTemperatureAvg())
                .bestTimeToVisit(destination.getBestTimeToVisit())
                .currency(destination.getCurrency())
                .language(destination.getLanguage())
                .travelType(destination.getTravelType() != null ? destination.getTravelType().toString() : null)
                .budgetCategory(destination.getBudgetCategory() != null ? destination.getBudgetCategory().toString() : null)
                .averageRating(destination.getAverageRating())
                .reviewCount(destination.getReviewCount())
                .imageUrl(destination.getImageUrl())
                .build();
    }
}
