package com.smarttravel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TripCostDTO {
    private BigDecimal accommodationCost;
    private BigDecimal transportationCost;
    private BigDecimal foodCost;
    private BigDecimal activitiesCost;
    private BigDecimal otherCosts;
    private BigDecimal totalCost;
    private BigDecimal costPerPerson;
}
