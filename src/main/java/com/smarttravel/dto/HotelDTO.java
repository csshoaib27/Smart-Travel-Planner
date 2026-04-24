package com.smarttravel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class HotelDTO {
    private Integer hotelId;
    private Integer destinationId;
    private String name;
    private String description;
    private String address;
    private Double starRating;
    private BigDecimal pricePerNight;
    private String currency;
    private String roomTypes;
    private String amenities;
    private Double averageRating;
    private Integer reviewCount;
    private Integer availableRooms;
}
