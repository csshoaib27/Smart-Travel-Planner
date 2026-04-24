package com.smarttravel.controller;

import com.smarttravel.dto.*;
import com.smarttravel.model.*;
import com.smarttravel.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

// ==================== AUTH CONTROLLER ====================
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            AuthResponse response = authService.register(request);
            return ResponseEntity.ok(new ApiResponse<>(true, "User registered successfully", response,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            java.time.LocalDateTime.now().toString()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", response,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(false, "Invalid credentials", null,
                            java.time.LocalDateTime.now().toString()));
        }
    }

    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        boolean isValid = authService.validateToken(token);
        return ResponseEntity.ok(new ApiResponse<>(true, "Token validation result", isValid,
                java.time.LocalDateTime.now().toString()));
    }
}

// ==================== DESTINATION CONTROLLER ====================
@RestController
@RequestMapping("/destinations")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
class DestinationController {

    private final DestinationService destinationService;

    @GetMapping
    public ResponseEntity<?> getAllDestinations() {
        try {
            List<DestinationDTO> destinations = destinationService.getAllDestinations();
            return ResponseEntity.ok(new ApiResponse<>(true, "Destinations retrieved successfully",
                    destinations, java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            java.time.LocalDateTime.now().toString()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDestinationById(@PathVariable Integer id) {
        try {
            DestinationDTO destination = destinationService.getDestinationById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Destination retrieved",
                    destination, java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(404)
                    .body(new ApiResponse<>(false, "Destination not found", null,
                            java.time.LocalDateTime.now().toString()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchDestinations(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String travelType,
            @RequestParam(required = false) String budgetCategory) {
        try {
            List<DestinationDTO> results;
            if (country != null && travelType != null && budgetCategory != null) {
                results = destinationService.searchDestinations(country, travelType, budgetCategory);
            } else if (country != null) {
                results = destinationService.getDestinationsByCountry(country);
            } else if (travelType != null) {
                results = destinationService.getDestinationsByTravelType(travelType);
            } else if (budgetCategory != null) {
                results = destinationService.getDestinationsByBudget(budgetCategory);
            } else {
                results = destinationService.getAllDestinations();
            }
            return ResponseEntity.ok(new ApiResponse<>(true, "Search completed", results,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            java.time.LocalDateTime.now().toString()));
        }
    }

    @GetMapping("/top-rated")
    public ResponseEntity<?> getTopRatedDestinations() {
        try {
            List<DestinationDTO> destinations = destinationService.getTopRatedDestinations();
            return ResponseEntity.ok(new ApiResponse<>(true, "Top rated destinations",
                    destinations, java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            java.time.LocalDateTime.now().toString()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createDestination(@RequestBody Destination destination) {
        try {
            DestinationDTO created = destinationService.createDestination(destination);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Destination created", created,
                            java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            java.time.LocalDateTime.now().toString()));
        }
    }
}

// ==================== HOTEL CONTROLLER ====================
@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
class HotelController {

    private final HotelService hotelService;

    @GetMapping
    public ResponseEntity<?> getAllHotels() {
        try {
            List<HotelDTO> hotels = hotelService.getAllHotels();
            return ResponseEntity.ok(new ApiResponse<>(true, "Hotels retrieved", hotels,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            java.time.LocalDateTime.now().toString()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHotelById(@PathVariable Integer id) {
        try {
            HotelDTO hotel = hotelService.getHotelById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Hotel retrieved", hotel,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(404)
                    .body(new ApiResponse<>(false, "Hotel not found", null,
                            java.time.LocalDateTime.now().toString()));
        }
    }

    @GetMapping("/destination/{destinationId}")
    public ResponseEntity<?> getHotelsByDestination(@PathVariable Integer destinationId) {
        try {
            List<HotelDTO> hotels = hotelService.getHotelsByDestination(destinationId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Hotels retrieved", hotels,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            java.time.LocalDateTime.now().toString()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchHotels(
            @RequestParam Integer destinationId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {
        try {
            if (minPrice != null && maxPrice != null) {
                List<HotelDTO> hotels = hotelService.getHotelsByDestinationAndPrice(destinationId, minPrice, maxPrice);
                return ResponseEntity.ok(new ApiResponse<>(true, "Hotels found", hotels,
                        java.time.LocalDateTime.now().toString()));
            } else {
                List<HotelDTO> hotels = hotelService.getHotelsByDestination(destinationId);
                return ResponseEntity.ok(new ApiResponse<>(true, "Hotels found", hotels,
                        java.time.LocalDateTime.now().toString()));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            java.time.LocalDateTime.now().toString()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createHotel(@RequestBody Hotel hotel) {
        try {
            HotelDTO created = hotelService.createHotel(hotel);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Hotel created", created,
                            java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            java.time.LocalDateTime.now().toString()));
        }
    }
}

// ==================== COST CALCULATOR CONTROLLER ====================
@RestController
@RequestMapping("/calculator")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
class CostCalculatorController {

    private final CostCalculatorService costCalculatorService;

    @PostMapping("/calculate")
    public ResponseEntity<?> calculateCost(@RequestBody CostCalculatorRequest request) {
        try {
            CostBreakdown breakdown = costCalculatorService.calculateTripCost(
                    request.getNumberOfDays(),
                    request.getNumberOfPeople(),
                    request.getNumberOfRooms(),
                    request.getDestinationId(),
                    request.getBudgetCategory()
            );
            return ResponseEntity.ok(new ApiResponse<>(true, "Cost calculated", breakdown,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            java.time.LocalDateTime.now().toString()));
        }
    }

    @PostMapping("/calculate-per-person")
    public ResponseEntity<?> calculatePerPerson(@RequestBody CostCalculatorRequest request) {
        try {
            CostBreakdown breakdown = costCalculatorService.calculateTripCost(
                    request.getNumberOfDays(),
                    request.getNumberOfPeople(),
                    request.getNumberOfRooms(),
                    request.getDestinationId(),
                    request.getBudgetCategory()
            );
            BigDecimal perPerson = costCalculatorService.calculateCostPerPerson(breakdown, request.getNumberOfPeople());
            return ResponseEntity.ok(new ApiResponse<>(true, "Cost per person calculated", perPerson,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            java.time.LocalDateTime.now().toString()));
        }
    }
}
