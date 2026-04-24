package com.smarttravel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DestinationDTO {
    private Integer destinationId;
    private String name;
    private String country;
    private String description;
    private Double temperatureAvg;
    private String bestTimeToVisit;
    private String currency;
    private String language;
    private String travelType;
    private String budgetCategory;
    private Double averageRating;
    private Integer reviewCount;
    private String imageUrl;
}
