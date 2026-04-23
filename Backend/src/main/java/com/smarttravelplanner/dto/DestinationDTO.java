package com.smarttravelplanner.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DestinationDTO {
    private Long id;
    private String name;
    private String description;
    private String country;
    private String city;
    private String region;
    private Double latitude;
    private Double longitude;
    private String budget;
    private Integer temperature;
    private String bestTimeToVisit;
    private Integer distance;
    private Double rating;
    private Integer reviewCount;
    private String interests;
    private String image;
    private String travelTime;
    private Integer costPerDay;
    private String activities;
    private Integer safetyRating;
}
