package com.smarttravelplanner.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "traditional_foods")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TraditionalFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destination;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String cuisineType;

    @Column
    private Double price;

    @Column
    private String dietaryOptions; // comma-separated (veg, vegan, gluten-free, etc.)

    @Column
    private String image;

    @Column
    private String bestPlace;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
}
