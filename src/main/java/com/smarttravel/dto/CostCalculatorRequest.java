package com.smarttravel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CostCalculatorRequest {
    private Integer numberOfPeople = 1;
    private Integer numberOfDays = 1;
    private Integer numberOfRooms = 1;
    private BigDecimal hotelPricePerNight = BigDecimal.ZERO;
    private BigDecimal transportationCost = BigDecimal.ZERO;
    private BigDecimal foodCostPerDay = BigDecimal.ZERO;
    private BigDecimal activitiesCost = BigDecimal.ZERO;
    private BigDecimal otherCosts = BigDecimal.ZERO;
    private Integer destinationId;
}
