package com.smarttravelplanner.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "trip_shares")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripShare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @Column(nullable = false)
    private String sharedWithEmail;

    @Column
    private String sharedWithName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SharePermission permission;

    @Column(nullable = false)
    private LocalDateTime sharedAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    public enum SharePermission {
        VIEW, EDIT, ADMIN
    }
}
