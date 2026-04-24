package com.smarttravel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.databind.JsonNode;
import java.time.LocalDateTime;

@Entity
@Table(name = "search_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer searchId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "search_query", length = 255)
    private String searchQuery;

    @Column(name = "filters", columnDefinition = "JSON")
    private String filters;

    @Column(name = "results_count")
    private Integer resultsCount;

    @Column(name = "search_date", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime searchDate = LocalDateTime.now();

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @PrePersist
    protected void onCreate() {
        if (searchDate == null) searchDate = LocalDateTime.now();
    }
}
