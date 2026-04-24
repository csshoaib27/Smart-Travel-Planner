package com.smarttravel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cost_breakdown")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CostBreakdown {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer costId;

    @Column(name = "itinerary_id", nullable = false)
    private Integer itineraryId;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency", length = 10)
    @Builder.Default
    private String currency = "USD";

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itinerary_id", insertable = false, updatable = false)
    private Itinerary itinerary;

    public enum Category {
        Travel, Accommodation, Food, Activities, Other
    }

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }
}
