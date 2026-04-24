package com.smarttravel.controller;

import com.smarttravel.dto.*;
import com.smarttravel.model.*;
import com.smarttravel.service.*;
import com.smarttravel.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
public class AdminController {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final DestinationRepository destinationRepository;
    private final HotelRepository hotelRepository;
    private final ReviewRepository reviewRepository;
    private final ItineraryRepository itineraryRepository;

    // ==================== USER MANAGEMENT ====================

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        try {
            log.info("Fetching all users");
            List<User> users = userRepository.findAll();
            List<Map<String, Object>> userList = users.stream()
                    .map(user -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("userId", user.getUserId());
                        map.put("username", user.getUsername());
                        map.put("email", user.getEmail());
                        map.put("fullName", user.getFullName());
                        map.put("phone", user.getPhone());
                        map.put("isAdmin", user.getIsAdmin());
                        map.put("createdAt", user.getCreatedAt());
                        map.put("bookingCount", user.getBookings().size());
                        map.put("itineraryCount", user.getItineraries().size());
                        return map;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new ApiResponse<>(true, "All users retrieved successfully",
                    userList, LocalDateTime.now().toString()));
        } catch (Exception e) {
            log.error("Error fetching users: {}", e.getMessage());
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            LocalDateTime.now().toString()));
        }
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        try {
            log.info("Fetching user: {}", id);
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Map<String, Object> userData = new HashMap<>();
            userData.put("userId", user.getUserId());
            userData.put("username", user.getUsername());
            userData.put("email", user.getEmail());
            userData.put("fullName", user.getFullName());
            userData.put("phone", user.getPhone());
            userData.put("isAdmin", user.getIsAdmin());
            userData.put("createdAt", user.getCreatedAt());
            userData.put("updatedAt", user.getUpdatedAt());
            userData.put("bookingCount", user.getBookings().size());
            userData.put("itineraryCount", user.getItineraries().size());
            userData.put("reviewCount", user.getReviews().size());

            return ResponseEntity.ok(new ApiResponse<>(true, "User retrieved successfully",
                    userData, LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(404)
                    .body(new ApiResponse<>(false, "User not found", null,
                            LocalDateTime.now().toString()));
        }
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        try {
            if (id == 1) { // Protect admin user
                return ResponseEntity.badRequest()
                        .body(new ApiResponse<>(false, "Cannot delete admin user", null,
                                LocalDateTime.now().toString()));
            }

            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            userRepository.delete(user);
            log.info("User deleted: {}", id);

            return ResponseEntity.ok(new ApiResponse<>(true, "User deleted successfully", null,
                    LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            LocalDateTime.now().toString()));
        }
    }

    @PutMapping("/users/{id}/make-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> makeUserAdmin(@PathVariable Integer id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            user.setIsAdmin(true);
            userRepository.save(user);
            log.info("User promoted to admin: {}", id);

            return ResponseEntity.ok(new ApiResponse<>(true, "User promoted to admin", null,
                    LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            LocalDateTime.now().toString()));
        }
    }

    // ==================== BOOKING MANAGEMENT ====================

    @GetMapping("/bookings")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllBookings(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(required = false) String status) {
        try {
            log.info("Fetching all bookings");
            List<Booking> bookings;

            if (status != null && !status.isEmpty()) {
                try {
                    Booking.BookingStatus bookingStatus = Booking.BookingStatus.valueOf(status);
                    bookings = bookingRepository.findByStatus(bookingStatus);
                } catch (IllegalArgumentException e) {
                    bookings = bookingRepository.findAll();
                }
            } else {
                bookings = bookingRepository.findAll();
            }

            List<Map<String, Object>> bookingList = bookings.stream()
                    .map(booking -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("bookingId", booking.getBookingId());
                        map.put("userId", booking.getUserId());
                        map.put("userName", booking.getUser().getUsername());
                        map.put("userEmail", booking.getUser().getEmail());
                        map.put("hotelId", booking.getHotelId());
                        map.put("hotelName", booking.getHotel().getName());
                        map.put("destinationId", booking.getDestinationId());
                        map.put("destinationName", booking.getDestination().getName());
                        map.put("checkInDate", booking.getCheckInDate());
                        map.put("checkOutDate", booking.getCheckOutDate());
                        map.put("numberOfNights", booking.getNumberOfNights());
                        map.put("numberOfGuests", booking.getNumberOfGuests());
                        map.put("numberOfRooms", booking.getNumberOfRooms());
                        map.put("totalPrice", booking.getTotalPrice());
                        map.put("status", booking.getStatus());
                        map.put("bookingDate", booking.getBookingDate());
                        return map;
                    })
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("bookings", bookingList);
            response.put("totalBookings", bookings.size());
            response.put("pageNumber", pageNumber);
            response.put("pageSize", pageSize);

            return ResponseEntity.ok(new ApiResponse<>(true, "All bookings retrieved",
                    response, LocalDateTime.now().toString()));
        } catch (Exception e) {
            log.error("Error fetching bookings: {}", e.getMessage());
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            LocalDateTime.now().toString()));
        }
    }

    @GetMapping("/bookings/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getBookingById(@PathVariable Integer id) {
        try {
            Booking booking = bookingRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Booking not found"));

            Map<String, Object> bookingData = new HashMap<>();
            bookingData.put("bookingId", booking.getBookingId());
            bookingData.put("userId", booking.getUserId());
            bookingData.put("userName", booking.getUser().getUsername());
            bookingData.put("userEmail", booking.getUser().getEmail());
            bookingData.put("userPhone", booking.getUser().getPhone());
            bookingData.put("hotelId", booking.getHotelId());
            bookingData.put("hotelName", booking.getHotel().getName());
            bookingData.put("destinationId", booking.getDestinationId());
            bookingData.put("destinationName", booking.getDestination().getName());
            bookingData.put("checkInDate", booking.getCheckInDate());
            bookingData.put("checkOutDate", booking.getCheckOutDate());
            bookingData.put("numberOfNights", booking.getNumberOfNights());
            bookingData.put("numberOfGuests", booking.getNumberOfGuests());
            bookingData.put("numberOfRooms", booking.getNumberOfRooms());
            bookingData.put("roomType", booking.getRoomType());
            bookingData.put("totalPrice", booking.getTotalPrice());
            bookingData.put("status", booking.getStatus());
            bookingData.put("bookingDate", booking.getBookingDate());

            return ResponseEntity.ok(new ApiResponse<>(true, "Booking retrieved successfully",
                    bookingData, LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(404)
                    .body(new ApiResponse<>(false, "Booking not found", null,
                            LocalDateTime.now().toString()));
        }
    }

    @GetMapping("/bookings/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserBookings(@PathVariable Integer userId) {
        try {
            log.info("Fetching bookings for user: {}", userId);
            List<Booking> bookings = bookingRepository.findByUserId(userId);

            List<Map<String, Object>> bookingList = bookings.stream()
                    .map(booking -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("bookingId", booking.getBookingId());
                        map.put("hotelName", booking.getHotel().getName());
                        map.put("destinationName", booking.getDestination().getName());
                        map.put("checkInDate", booking.getCheckInDate());
                        map.put("checkOutDate", booking.getCheckOutDate());
                        map.put("totalPrice", booking.getTotalPrice());
                        map.put("status", booking.getStatus());
                        map.put("bookingDate", booking.getBookingDate());
                        return map;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new ApiResponse<>(true, "User bookings retrieved",
                    bookingList, LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            LocalDateTime.now().toString()));
        }
    }

    @PutMapping("/bookings/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateBookingStatus(@PathVariable Integer id,
                                                  @RequestParam String status) {
        try {
            Booking booking = bookingRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Booking not found"));

            booking.setStatus(Booking.BookingStatus.valueOf(status));
            bookingRepository.save(booking);
            log.info("Booking status updated: {} to {}", id, status);

            return ResponseEntity.ok(new ApiResponse<>(true, "Booking status updated", null,
                    LocalDateTime.now().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            LocalDateTime.now().toString()));
        }
    }

    // ==================== STATISTICS ====================

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getSystemStatistics() {
        try {
            log.info("Fetching system statistics");

            Map<String, Object> stats = new HashMap<>();

            // User stats
            long totalUsers = userRepository.count();
            long adminUsers = userRepository.findByIsAdmin(true).size();
            stats.put("totalUsers", totalUsers);
            stats.put("adminUsers", adminUsers);
            stats.put("normalUsers", totalUsers - adminUsers);

            // Booking stats
            long totalBookings = bookingRepository.count();
            long confirmedBookings = bookingRepository.findByStatus(Booking.BookingStatus.Confirmed).size();
            long pendingBookings = bookingRepository.findByStatus(Booking.BookingStatus.Pending).size();
            long cancelledBookings = bookingRepository.findByStatus(Booking.BookingStatus.Cancelled).size();
            stats.put("totalBookings", totalBookings);
            stats.put("confirmedBookings", confirmedBookings);
            stats.put("pendingBookings", pendingBookings);
            stats.put("cancelledBookings", cancelledBookings);

            // Destination and Hotel stats
            stats.put("totalDestinations", destinationRepository.count());
            stats.put("totalHotels", hotelRepository.count());
            stats.put("totalReviews", reviewRepository.count());
            stats.put("totalItineraries", itineraryRepository.count());

            // Revenue stats
            java.math.BigDecimal totalRevenue = bookingRepository.findByStatus(Booking.BookingStatus.Completed)
                    .stream()
                    .map(Booking::getTotalPrice)
                    .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
            stats.put("totalRevenue", totalRevenue);

            stats.put("timestamp", LocalDateTime.now());

            return ResponseEntity.ok(new ApiResponse<>(true, "System statistics retrieved",
                    stats, LocalDateTime.now().toString()));
        } catch (Exception e) {
            log.error("Error fetching statistics: {}", e.getMessage());
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            LocalDateTime.now().toString()));
        }
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getDashboardData() {
        try {
            log.info("Fetching admin dashboard data");

            Map<String, Object> dashboard = new HashMap<>();

            // Recent bookings
            List<Booking> recentBookings = bookingRepository.findAll().stream()
                    .sorted((b1, b2) -> b2.getBookingDate().compareTo(b1.getBookingDate()))
                    .limit(5)
                    .collect(Collectors.toList());

            List<Map<String, Object>> recentBookingsList = recentBookings.stream()
                    .map(b -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("bookingId", b.getBookingId());
                        map.put("userName", b.getUser().getUsername());
                        map.put("hotelName", b.getHotel().getName());
                        map.put("totalPrice", b.getTotalPrice());
                        map.put("status", b.getStatus());
                        map.put("bookingDate", b.getBookingDate());
                        return map;
                    })
                    .collect(Collectors.toList());

            dashboard.put("recentBookings", recentBookingsList);

            // Get statistics
            Map<String, Object> stats = new HashMap<>();
            stats.put("totalUsers", userRepository.count());
            stats.put("totalBookings", bookingRepository.count());
            stats.put("totalDestinations", destinationRepository.count());
            stats.put("totalHotels", hotelRepository.count());
            dashboard.put("statistics", stats);

            return ResponseEntity.ok(new ApiResponse<>(true, "Dashboard data retrieved",
                    dashboard, LocalDateTime.now().toString()));
        } catch (Exception e) {
            log.error("Error fetching dashboard: {}", e.getMessage());
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>(false, e.getMessage(), null,
                            LocalDateTime.now().toString()));
        }
    }
}
