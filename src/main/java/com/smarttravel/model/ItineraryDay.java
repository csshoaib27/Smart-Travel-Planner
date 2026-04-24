package com.smarttravel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "itinerary_days")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItineraryDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itineraryDayId;

    @Column(name = "itinerary_id", nullable = false)
    private Integer itineraryId;

    @Column(name = "day_number", nullable = false)
    private Integer dayNumber;

    @Column(name = "destination_id", nullable = false)
    private Integer destinationId;

    @Column(columnDefinition = "TEXT")
    private String activities;

    @Column(name = "accommodation_id")
    private Integer accommodationId;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "estimated_budget", precision = 10, scale = 2)
    private BigDecimal estimatedBudget;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itinerary_id", insertable = false, updatable = false)
    private Itinerary itinerary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_id", insertable = false, updatable = false)
    private Destination destination;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
}
