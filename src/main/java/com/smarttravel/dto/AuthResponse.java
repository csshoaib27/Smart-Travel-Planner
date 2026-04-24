package com.smarttravel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AuthResponse {
    private Integer userId;
    private String username;
    private String email;
    private String fullName;
    private String token;
    private Boolean isAdmin;
    private LocalDateTime createdAt;
}
