package com.smarttravel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

// ==================== AUTH DTOs ====================
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class LoginRequest {
    private String username;
    private String password;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String phone;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class AuthResponse {
    private Integer userId;
    private String username;
    private String email;
    private String fullName;
    private String token;
    private Boolean isAdmin;
    private LocalDateTime createdAt;
}

// ==================== DESTINATION DTOs ====================
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class DestinationDTO {
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class DestinationFilterRequest {
    private String country;
    private String travelType;
    private String budgetCategory;
    private Double maxTemperature;
    private Double minTemperature;
    private Double minRating;
    private Integer pageNumber;
    private Integer pageSize;
}

// ==================== HOTEL DTOs ====================
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class HotelDTO {
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class HotelFilterRequest {
    private Integer destinationId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Double minRating;
    private Integer pageNumber;
    private Integer pageSize;
}

// ==================== BOOKING DTOs ====================
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class BookingRequest {
    private Integer hotelId;
    private Integer destinationId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer numberOfGuests;
    private Integer numberOfRooms;
    private String roomType;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class BookingDTO {
    private Integer bookingId;
    private Integer userId;
    private Integer hotelId;
    private Integer destinationId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer numberOfNights;
    private Integer numberOfGuests;
    private BigDecimal totalPrice;
    private String status;
    private LocalDateTime bookingDate;
}

// ==================== COST CALCULATOR DTOs ====================
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class CostBreakdown {
    private BigDecimal travelCost;
    private BigDecimal accommodationCost;
    private BigDecimal foodEstimate;
    private BigDecimal activitiesEstimate;
    private BigDecimal miscellaneous;
    private BigDecimal totalEstimatedCost;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class CostCalculatorRequest {
    private Integer numberOfDays;
    private Integer numberOfPeople;
    private Integer numberOfRooms;
    private String budgetCategory;
    private Integer destinationId;
}

// ==================== ITINERARY DTOs ====================
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ItineraryRequest {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String packageType;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ItineraryDTO {
    private Integer itineraryId;
    private Integer userId;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer numberOfDays;
    private String packageType;
    private BigDecimal totalEstimatedCost;
    private Boolean isPublic;
    private LocalDateTime createdAt;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ItineraryDayDTO {
    private Integer dayNumber;
    private Integer destinationId;
    private String activities;
    private String notes;
    private BigDecimal estimatedBudget;
}

// ==================== REVIEW DTOs ====================
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ReviewRequest {
    private Double rating;
    private String title;
    private String content;
    private Integer destinationId;
    private Integer hotelId;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ReviewDTO {
    private Integer reviewId;
    private Integer userId;
    private String username;
    private Double rating;
    private String title;
    private String content;
    private Integer helpfulCount;
    private LocalDateTime createdAt;
}

// ==================== PAGINATION DTOs ====================
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class PaginatedResponse<T> {
    private java.util.List<T> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private Boolean isLast;
}

// ==================== API RESPONSE DTOs ====================
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ApiResponse<T> {
    private Boolean success;
    private String message;
    private T data;
    private String timestamp;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ErrorResponse {
    private Integer statusCode;
    private String message;
    private String error;
    private String timestamp;
    private String path;
}
