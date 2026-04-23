package com.smarttravelplanner.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "safety_alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SafetyAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destination;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AlertType type;

    @Column(nullable = false)
    private String name;

    @Column
    private String address;

    @Column
    private String phone;

    @Column
    private String email;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    public enum AlertType {
        HOSPITAL, POLICE_STATION, EMBASSY, WARNING, TIP
    }
}
