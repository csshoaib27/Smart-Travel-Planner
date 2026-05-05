package com.smarttravel.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data @NoArgsConstructor
public class ItineraryPdfRequest {
    private String tripTitle;
    private String destinationName;
    private String startDate;
    private String endDate;
    private String currency = "INR";

    private Integer numberOfPeople = 1;
    private Integer numberOfDays = 1;
    private Integer numberOfRooms = 1;
    private BigDecimal hotelPricePerNight = BigDecimal.ZERO;
    private BigDecimal transportationCost = BigDecimal.ZERO;
    private BigDecimal foodCostPerDay = BigDecimal.ZERO;
    private BigDecimal activitiesCost = BigDecimal.ZERO;
    private BigDecimal otherCosts = BigDecimal.ZERO;
}
