package com.smarttravelplanner.dto;

import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripDTO {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private Long destinationId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer numberOfDays;
    private Double budget;
    private String packageMode;
    private Double totalCost;
    private Integer participants;
    private List<Long> hotelIds;
}
