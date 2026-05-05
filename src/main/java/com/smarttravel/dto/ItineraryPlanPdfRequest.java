package com.smarttravel.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Data @NoArgsConstructor
public class ItineraryPlanPdfRequest {
    private String title;
    private String destinationName;
    private String startDate;
    private String endDate;
    private String currency = "INR";
    private String packageType;
    private String budgetTier;
    private Integer numberOfDays;
    private BigDecimal totalCost;
    private List<DayPlan> days;

    @Data @NoArgsConstructor
    public static class DayPlan {
        private Integer dayNumber;
        private String activities;
        private String note;
        private BigDecimal budget;
    }

    @Data @NoArgsConstructor
    public static class HotelInfo {
        private String name;
        private Double starRating;
        private String priceRange;
        private Integer reviewCount;
    }

    private List<HotelInfo> hotels;
}
