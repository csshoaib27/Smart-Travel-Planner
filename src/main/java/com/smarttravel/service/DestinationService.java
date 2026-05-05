package com.smarttravel.service;

import com.smarttravel.dto.DestinationDTO;
import com.smarttravel.model.Destination;
import com.smarttravel.repository.DestinationRepository;
import com.smarttravel.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DestinationService {

    private final DestinationRepository destinationRepository;
    private final ReviewRepository reviewRepository;
    private final GeminiService geminiService;

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

    public List<DestinationDTO> filterDestinations(String country, String travelType, String budgetCategory, String city) {
        String c = (country != null && !country.isEmpty()) ? country : null;
        String ci = (city != null && !city.isEmpty()) ? city : null;

        Destination.TravelType tt = null;
        if (travelType != null && !travelType.isEmpty()) {
            try { tt = Destination.TravelType.valueOf(travelType); } catch (IllegalArgumentException ignored) {}
        }

        Destination.BudgetCategory bc = null;
        if (budgetCategory != null && !budgetCategory.isEmpty()) {
            try { bc = Destination.BudgetCategory.valueOf(budgetCategory); } catch (IllegalArgumentException ignored) {}
        }

        return destinationRepository.filterDestinations(c, tt, bc, ci)
                .stream()
                .collect(Collectors.toMap(
                        d -> (d.getName() + "|" + d.getCountry()).toLowerCase(),
                        d -> d,
                        (a, b) -> a,
                        LinkedHashMap::new
                ))
                .values()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<String> getDistinctCountries() {
        return destinationRepository.findDistinctCountries();
    }

    public List<String> getCitiesForCountry(String country) {
        List<String> dbCities = destinationRepository.findNamesByCountry(country);
        List<String> aiCities = geminiService.getCitiesForCountry(country);
        LinkedHashSet<String> combined = new LinkedHashSet<>(dbCities);
        combined.addAll(aiCities);
        return new ArrayList<>(combined);
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
