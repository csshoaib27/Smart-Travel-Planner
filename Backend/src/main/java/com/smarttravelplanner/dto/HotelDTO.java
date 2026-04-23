package com.smarttravelplanner.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelDTO {
    private Long id;
    private String name;
    private Long destinationId;
    private String city;
    private String address;
    private Double pricePerNight;
    private Double rating;
    private String amenities;
    private String image;
    private String description;
    private String roomTypes;
    private String contact;
    private String checkInTime;
    private String checkOutTime;
    private Boolean wifi;
    private Boolean parking;
    private Boolean gym;
    private Boolean restaurant;
}
