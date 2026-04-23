package com.smarttravelplanner.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "destinations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column
    private String region;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BudgetType budget;

    @Column
    private Integer temperature;

    @Column
    private String bestTimeToVisit;

    @Column
    private Integer distance; // in km

    @Column
    private Double rating;

    @Column
    private Integer reviewCount = 0;

    @Column
    private String interests; // comma-separated

    @Column
    private String image;

    @Column
    private String travelTime;

    @Column
    private Integer costPerDay;

    @Column
    private String activities; // comma-separated

    @Column
    private Integer safetyRating;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    public enum BudgetType {
        LOW, MEDIUM, HIGH
    }
}
