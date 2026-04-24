package com.smarttravel.service;

import com.smarttravel.dto.CostBreakdown;
import com.smarttravel.model.Destination;
import com.smarttravel.model.Hotel;
import com.smarttravel.repository.DestinationRepository;
import com.smarttravel.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Slf4j
public class CostCalculatorService {

    private final HotelRepository hotelRepository;
    private final DestinationRepository destinationRepository;

    public CostBreakdown calculateTripCost(Integer numberOfDays,
                                          Integer numberOfPeople,
                                          Integer numberOfRooms,
                                          Integer hotelId,
                                          String budgetCategory) {
        log.info("Calculating trip cost for {} days, {} people, {} rooms", numberOfDays, numberOfPeople, numberOfRooms);

        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        // Accommodation Cost
        BigDecimal accommodationCost = hotel.getPricePerNight()
                .multiply(BigDecimal.valueOf(numberOfDays))
                .multiply(BigDecimal.valueOf(numberOfRooms));

        // Estimate other costs based on budget category
        BigDecimal travelCost = estimateTravelCost(budgetCategory, numberOfPeople);
        BigDecimal foodEstimate = estimateFoodCost(budgetCategory, numberOfDays, numberOfPeople);
        BigDecimal activitiesEstimate = estimateActivitiesCost(budgetCategory, numberOfDays, numberOfPeople);
        BigDecimal miscellaneous = estimateMiscellaneous(accommodationCost);

        BigDecimal totalCost = accommodationCost
                .add(travelCost)
                .add(foodEstimate)
                .add(activitiesEstimate)
                .add(miscellaneous);

        return CostBreakdown.builder()
                .travelCost(travelCost)
                .accommodationCost(accommodationCost)
                .foodEstimate(foodEstimate)
                .activitiesEstimate(activitiesEstimate)
                .miscellaneous(miscellaneous)
                .totalEstimatedCost(totalCost)
                .build();
    }

    public CostBreakdown calculateDestinationCost(Integer destinationId,
                                                 Integer numberOfDays,
                                                 Integer numberOfPeople,
                                                 Integer numberOfRooms) {
        Destination destination = destinationRepository.findById(destinationId)
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        // Use average hotel price for the destination
        java.util.List<Hotel> hotels = hotelRepository.findByDestinationId(destinationId);
        if (hotels.isEmpty()) {
            throw new RuntimeException("No hotels found for this destination");
        }

        BigDecimal avgPrice = hotels.stream()
                .map(Hotel::getPricePerNight)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(hotels.size()), RoundingMode.HALF_UP);

        BigDecimal accommodationCost = avgPrice
                .multiply(BigDecimal.valueOf(numberOfDays))
                .multiply(BigDecimal.valueOf(numberOfRooms));

        String budgetCategory = destination.getBudgetCategory().toString();

        BigDecimal travelCost = estimateTravelCost(budgetCategory, numberOfPeople);
        BigDecimal foodEstimate = estimateFoodCost(budgetCategory, numberOfDays, numberOfPeople);
        BigDecimal activitiesEstimate = estimateActivitiesCost(budgetCategory, numberOfDays, numberOfPeople);
        BigDecimal miscellaneous = estimateMiscellaneous(accommodationCost);

        BigDecimal totalCost = accommodationCost
                .add(travelCost)
                .add(foodEstimate)
                .add(activitiesEstimate)
                .add(miscellaneous);

        return CostBreakdown.builder()
                .travelCost(travelCost)
                .accommodationCost(accommodationCost)
                .foodEstimate(foodEstimate)
                .activitiesEstimate(activitiesEstimate)
                .miscellaneous(miscellaneous)
                .totalEstimatedCost(totalCost)
                .build();
    }

    private BigDecimal estimateTravelCost(String budgetCategory, Integer numberOfPeople) {
        BigDecimal baseCost = switch (budgetCategory) {
            case "Budget" -> BigDecimal.valueOf(300);
            case "MidRange" -> BigDecimal.valueOf(600);
            case "Luxury" -> BigDecimal.valueOf(1200);
            default -> BigDecimal.valueOf(500);
        };

        return baseCost.multiply(BigDecimal.valueOf(numberOfPeople));
    }

    private BigDecimal estimateFoodCost(String budgetCategory, Integer numberOfDays, Integer numberOfPeople) {
        BigDecimal dailyPerPersonCost = switch (budgetCategory) {
            case "Budget" -> BigDecimal.valueOf(20);
            case "MidRange" -> BigDecimal.valueOf(50);
            case "Luxury" -> BigDecimal.valueOf(100);
            default -> BigDecimal.valueOf(35);
        };

        return dailyPerPersonCost
                .multiply(BigDecimal.valueOf(numberOfDays))
                .multiply(BigDecimal.valueOf(numberOfPeople));
    }

    private BigDecimal estimateActivitiesCost(String budgetCategory, Integer numberOfDays, Integer numberOfPeople) {
        BigDecimal dailyPerPersonCost = switch (budgetCategory) {
            case "Budget" -> BigDecimal.valueOf(25);
            case "MidRange" -> BigDecimal.valueOf(75);
            case "Luxury" -> BigDecimal.valueOf(200);
            default -> BigDecimal.valueOf(50);
        };

        return dailyPerPersonCost
                .multiply(BigDecimal.valueOf(numberOfDays))
                .multiply(BigDecimal.valueOf(numberOfPeople));
    }

    private BigDecimal estimateMiscellaneous(BigDecimal accommodationCost) {
        // Miscellaneous is 10% of accommodation cost (tips, local transport, etc.)
        return accommodationCost.multiply(BigDecimal.valueOf(0.10));
    }

    public BigDecimal calculateCostPerPerson(CostBreakdown breakdown, Integer numberOfPeople) {
        return breakdown.getTotalEstimatedCost()
                .divide(BigDecimal.valueOf(numberOfPeople), 2, RoundingMode.HALF_UP);
    }

    public CostBreakdown adjustCostBySplits(CostBreakdown breakdown, Integer numberOfSplits) {
        if (numberOfSplits <= 0) {
            throw new RuntimeException("Number of splits must be greater than 0");
        }

        BigDecimal divisor = BigDecimal.valueOf(numberOfSplits);

        return CostBreakdown.builder()
                .travelCost(breakdown.getTravelCost().divide(divisor, 2, RoundingMode.HALF_UP))
                .accommodationCost(breakdown.getAccommodationCost().divide(divisor, 2, RoundingMode.HALF_UP))
                .foodEstimate(breakdown.getFoodEstimate().divide(divisor, 2, RoundingMode.HALF_UP))
                .activitiesEstimate(breakdown.getActivitiesEstimate().divide(divisor, 2, RoundingMode.HALF_UP))
                .miscellaneous(breakdown.getMiscellaneous().divide(divisor, 2, RoundingMode.HALF_UP))
                .totalEstimatedCost(breakdown.getTotalEstimatedCost().divide(divisor, 2, RoundingMode.HALF_UP))
                .build();
    }
}
