package com.smarttravelplanner.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hotels")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destination;

    @Column(nullable = false)
    private String city;

    @Column
    private String address;

    @Column(nullable = false)
    private Double pricePerNight;

    @Column
    private Double rating;

    @Column
    private String amenities; // comma-separated

    @Column
    private String image;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String roomTypes; // comma-separated

    @Column
    private String contact;

    @Column
    private String checkInTime;

    @Column
    private String checkOutTime;

    @Column
    private Boolean wifi = true;

    @Column
    private Boolean parking = false;

    @Column
    private Boolean gym = false;

    @Column
    private Boolean restaurant = false;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
}
