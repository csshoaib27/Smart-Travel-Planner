package com.smarttravel.controller;

import com.smarttravel.dto.*;
import com.smarttravel.model.*;
import com.smarttravel.repository.*;
import com.smarttravel.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        if (token.startsWith("Bearer ")) token = token.substring(7);
        boolean isValid = authService.validateToken(token);
        return ResponseEntity.ok(new ApiResponse<>(true, "Token validation result", isValid,
                java.time.LocalDateTime.now().toString()));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String email    = payload.get("email");
        String newPass  = payload.get("newPassword");
        if (username == null || email == null || newPass == null || newPass.length() < 6) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Invalid request. Password must be at least 6 characters.", null,
                            java.time.LocalDateTime.now().toString()));
        }
        try {
            boolean updated = authService.resetPassword(username, email, newPass);
            if (!updated) {
                return ResponseEntity.badRequest()
                        .body(new ApiResponse<>(false, "No account found with that username and email.", null,
                                java.time.LocalDateTime.now().toString()));
            }
            return ResponseEntity.ok(new ApiResponse<>(true, "Password reset successfully.", null,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            java.time.LocalDateTime.now().toString()));
        }
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
            return ResponseEntity.ok(new ApiResponse<>(true, "Destinations retrieved", destinations,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, e.getMessage(), null,
                    java.time.LocalDateTime.now().toString()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDestinationById(@PathVariable Integer id) {
        try {
            DestinationDTO destination = destinationService.getDestinationById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Destination retrieved", destination,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "Destination not found", null,
                    java.time.LocalDateTime.now().toString()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchDestinations(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String travelType,
            @RequestParam(required = false) String budgetCategory,
            @RequestParam(required = false) String city) {
        try {
            List<DestinationDTO> results = destinationService.filterDestinations(country, travelType, budgetCategory, city);
            return ResponseEntity.ok(new ApiResponse<>(true, "Search completed", results,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null,
                    java.time.LocalDateTime.now().toString()));
        }
    }

    @GetMapping("/countries")
    public ResponseEntity<?> getDistinctCountries() {
        try {
            List<String> countries = destinationService.getDistinctCountries();
            return ResponseEntity.ok(new ApiResponse<>(true, "Countries retrieved", countries,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, e.getMessage(), null,
                    java.time.LocalDateTime.now().toString()));
        }
    }

    @GetMapping("/cities")
    public ResponseEntity<?> getCitiesForCountry(@RequestParam String country) {
        try {
            List<String> cities = destinationService.getCitiesForCountry(country);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cities retrieved", cities,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, e.getMessage(), null,
                    java.time.LocalDateTime.now().toString()));
        }
    }

    @GetMapping("/top-rated")
    public ResponseEntity<?> getTopRatedDestinations() {
        try {
            List<DestinationDTO> destinations = destinationService.getTopRatedDestinations();
            return ResponseEntity.ok(new ApiResponse<>(true, "Top rated destinations", destinations,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, e.getMessage(), null,
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
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null,
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
    private final GooglePlacesService googlePlacesService;

    @GetMapping
    public ResponseEntity<?> getAllHotels() {
        try {
            List<HotelDTO> hotels = hotelService.getAllHotels();
            return ResponseEntity.ok(new ApiResponse<>(true, "Hotels retrieved", hotels,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, e.getMessage(), null,
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
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "Hotel not found", null,
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
            return ResponseEntity.status(500).body(new ApiResponse<>(false, e.getMessage(), null,
                    java.time.LocalDateTime.now().toString()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchHotels(
            @RequestParam Integer destinationId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {
        try {
            List<HotelDTO> hotels = (minPrice != null && maxPrice != null)
                    ? hotelService.getHotelsByDestinationAndPrice(destinationId, minPrice, maxPrice)
                    : hotelService.getHotelsByDestination(destinationId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Hotels found", hotels,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null,
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
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null,
                    java.time.LocalDateTime.now().toString()));
        }
    }

    @GetMapping("/google-hotels")
    public ResponseEntity<?> searchGoogleHotels(
            @RequestParam String destination,
            @RequestParam(required = false) Integer destinationId,
            @RequestParam(defaultValue = "Economic") String budgetTier) {
        try {
            List<?> hotels = googlePlacesService.searchHotels(destination, budgetTier);

            // Fall back to DB when Google Places is not configured or returned nothing
            if (hotels.isEmpty() && destinationId != null) {
                log.info("Falling back to DB hotels for destinationId={}", destinationId);
                hotels = hotelService.getHotelsForBudget(destinationId, budgetTier, destination);
            }

            return ResponseEntity.ok(new ApiResponse<>(true, "Hotels fetched", hotels,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            log.error("Hotel search failed", e);
            return ResponseEntity.status(500).body(new ApiResponse<>(false, e.getMessage(), null,
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
    private final PdfService pdfService;

    @PostMapping("/calculate")
    public ResponseEntity<?> calculateCost(@RequestBody CostCalculatorRequest request) {
        try {
            TripCostDTO result = costCalculatorService.calculate(request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cost calculated", result,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null,
                    java.time.LocalDateTime.now().toString()));
        }
    }

    @PostMapping("/calculate-per-person")
    public ResponseEntity<?> calculatePerPerson(@RequestBody CostCalculatorRequest request) {
        try {
            TripCostDTO result = costCalculatorService.calculate(request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cost per person calculated", result,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null,
                    java.time.LocalDateTime.now().toString()));
        }
    }

    @PostMapping("/download-pdf")
    public ResponseEntity<byte[]> downloadPdf(@RequestBody ItineraryPdfRequest request) {
        try {
            byte[] pdf = pdfService.generateItineraryPdf(request);
            String filename = (request.getTripTitle() != null && !request.getTripTitle().isBlank()
                    ? request.getTripTitle().replaceAll("[^a-zA-Z0-9]", "_") : "itinerary") + ".pdf";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", filename);
            headers.setContentLength(pdf.length);
            return ResponseEntity.ok().headers(headers).body(pdf);
        } catch (Exception e) {
            log.error("PDF generation failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

// ==================== ITINERARY CONTROLLER ====================
@RestController
@RequestMapping("/itineraries")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
class ItineraryController {

    private final ItineraryService itineraryService;
    private final PdfService pdfService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> payload) {
        try {
            Itinerary itinerary = itineraryService.createItinerary(payload);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Itinerary created", itinerary,
                            java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            log.error("Failed to create itinerary", e);
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            java.time.LocalDateTime.now().toString()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return itineraryService.getById(id)
                .<ResponseEntity<?>>map(it -> ResponseEntity.ok(
                        new ApiResponse<>(true, "Itinerary retrieved", it,
                                java.time.LocalDateTime.now().toString())))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Itinerary not found", null,
                                java.time.LocalDateTime.now().toString())));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getByUser(@PathVariable Integer userId) {
        try {
            List<Itinerary> list = itineraryService.getByUser(userId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Itineraries retrieved", list,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            java.time.LocalDateTime.now().toString()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            itineraryService.delete(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Itinerary deleted", null,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            java.time.LocalDateTime.now().toString()));
        }
    }

    @PostMapping("/plan-pdf")
    public ResponseEntity<byte[]> downloadPlanPdf(@RequestBody ItineraryPlanPdfRequest request) {
        try {
            byte[] pdf = pdfService.generateItineraryPlanPdf(request);
            String filename = (request.getTitle() != null && !request.getTitle().isBlank()
                    ? request.getTitle().replaceAll("[^a-zA-Z0-9]", "_") : "itinerary_plan") + ".pdf";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", filename);
            headers.setContentLength(pdf.length);
            return ResponseEntity.ok().headers(headers).body(pdf);
        } catch (Exception e) {
            log.error("Itinerary plan PDF generation failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

// ==================== BOOKING CONTROLLER ====================
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
class BookingController {

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Map<String, Object> payload) {
        try {
            Booking booking = Booking.builder()
                    .userId(((Number) payload.get("userId")).intValue())
                    .hotelId(((Number) payload.get("hotelId")).intValue())
                    .destinationId(((Number) payload.get("destinationId")).intValue())
                    .checkInDate(LocalDate.parse((String) payload.get("checkInDate")))
                    .checkOutDate(LocalDate.parse((String) payload.get("checkOutDate")))
                    .numberOfNights(((Number) payload.get("numberOfNights")).intValue())
                    .numberOfGuests(((Number) payload.get("numberOfGuests")).intValue())
                    .numberOfRooms(((Number) payload.get("numberOfRooms")).intValue())
                    .roomType((String) payload.get("roomType"))
                    .totalPrice(new BigDecimal(payload.get("totalPrice").toString()))
                    .status(Booking.BookingStatus.Confirmed)
                    .build();
            Booking saved = bookingRepository.save(booking);
            log.info("Booking created: {}", saved.getBookingId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Booking confirmed", saved.getBookingId(),
                            java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            log.error("Booking creation failed: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            java.time.LocalDateTime.now().toString()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserBookings(@PathVariable Integer userId) {
        try {
            List<Map<String, Object>> result = bookingRepository.findByUserId(userId).stream()
                    .map(b -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("bookingId", b.getBookingId());
                        map.put("hotelId", b.getHotelId());
                        map.put("destinationId", b.getDestinationId());
                        map.put("checkInDate", b.getCheckInDate());
                        map.put("checkOutDate", b.getCheckOutDate());
                        map.put("numberOfNights", b.getNumberOfNights());
                        map.put("numberOfGuests", b.getNumberOfGuests());
                        map.put("numberOfRooms", b.getNumberOfRooms());
                        map.put("roomType", b.getRoomType());
                        map.put("totalPrice", b.getTotalPrice());
                        map.put("status", b.getStatus());
                        map.put("bookingDate", b.getBookingDate());
                        hotelRepository.findById(b.getHotelId()).ifPresent(h -> {
                            map.put("hotelName", h.getName());
                            map.put("hotelStarRating", h.getStarRating());
                        });
                        return map;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>(true, "Bookings retrieved", result,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            java.time.LocalDateTime.now().toString()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Integer id) {
        return bookingRepository.findById(id)
                .<ResponseEntity<?>>map(b -> ResponseEntity.ok(new ApiResponse<>(true, "Booking retrieved", b,
                        java.time.LocalDateTime.now().toString())))
                .orElse(ResponseEntity.status(404)
                        .body(new ApiResponse<>(false, "Booking not found", null,
                                java.time.LocalDateTime.now().toString())));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable Integer id) {
        try {
            Booking booking = bookingRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Booking not found"));
            booking.setStatus(Booking.BookingStatus.Cancelled);
            bookingRepository.save(booking);
            return ResponseEntity.ok(new ApiResponse<>(true, "Booking cancelled", null,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            java.time.LocalDateTime.now().toString()));
        }
    }
}

// ==================== REVIEW CONTROLLER ====================
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
class ReviewController {

    private final ReviewRepository reviewRepository;

    @GetMapping("/destination/{destinationId}")
    public ResponseEntity<?> getByDestination(@PathVariable Integer destinationId) {
        try {
            List<Map<String, Object>> result = reviewRepository.findByDestinationId(destinationId).stream()
                    .map(this::toMap).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>(true, "Reviews retrieved", result,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, e.getMessage(), null,
                    java.time.LocalDateTime.now().toString()));
        }
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<?> getByHotel(@PathVariable Integer hotelId) {
        try {
            List<Map<String, Object>> result = reviewRepository.findByHotelId(hotelId).stream()
                    .map(this::toMap).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse<>(true, "Reviews retrieved", result,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, e.getMessage(), null,
                    java.time.LocalDateTime.now().toString()));
        }
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody Map<String, Object> payload) {
        try {
            Review review = new Review();
            review.setUserId(((Number) payload.get("userId")).intValue());
            review.setRating(((Number) payload.get("rating")).doubleValue());
            review.setTitle((String) payload.get("title"));
            review.setContent((String) payload.get("content"));
            if (payload.get("destinationId") != null)
                review.setDestinationId(((Number) payload.get("destinationId")).intValue());
            if (payload.get("hotelId") != null)
                review.setHotelId(((Number) payload.get("hotelId")).intValue());
            if (payload.get("reviewType") != null)
                review.setReviewType(Review.ReviewType.valueOf((String) payload.get("reviewType")));
            Review saved = reviewRepository.save(review);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Review created", saved.getReviewId(),
                            java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(false, e.getMessage(), null,
                    java.time.LocalDateTime.now().toString()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer id) {
        try {
            reviewRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Review deleted", null,
                    java.time.LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse<>(false, e.getMessage(), null,
                    java.time.LocalDateTime.now().toString()));
        }
    }

    private Map<String, Object> toMap(Review r) {
        Map<String, Object> m = new HashMap<>();
        m.put("reviewId", r.getReviewId());
        m.put("userId", r.getUserId());
        m.put("rating", r.getRating());
        m.put("title", r.getTitle());
        m.put("content", r.getContent());
        m.put("reviewType", r.getReviewType());
        m.put("helpfulCount", r.getHelpfulCount());
        m.put("createdAt", r.getCreatedAt());
        return m;
    }
}
