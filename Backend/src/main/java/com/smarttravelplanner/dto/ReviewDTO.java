package com.smarttravelplanner.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private Long id;
    private Long userId;
    private Long destinationId;
    private Integer rating;
    private String comment;
    private Integer helpfulCount;
    private String tripType;
}
