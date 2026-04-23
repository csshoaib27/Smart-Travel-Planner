package com.smarttravelplanner.controller;

import com.smarttravelplanner.dto.TripDTO;
import com.smarttravelplanner.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
public class TripController {
    private final TripService tripService;

    @GetMapping
    public ResponseEntity<List<TripDTO>> getAllTrips() {
        return ResponseEntity.ok(tripService.getAllTrips());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripDTO> getTripById(@PathVariable Long id) {
        TripDTO trip = tripService.getTripById(id);
        return trip != null ? ResponseEntity.ok(trip) : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TripDTO>> getUserTrips(@PathVariable Long userId) {
        return ResponseEntity.ok(tripService.getUserTrips(userId));
    }

    @PostMapping
    public ResponseEntity<TripDTO> createTrip(@RequestBody TripDTO dto) {
        TripDTO created = tripService.createTrip(dto);
        return created != null ? ResponseEntity.ok(created) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripDTO> updateTrip(@PathVariable Long id, @RequestBody TripDTO dto) {
        TripDTO updated = tripService.updateTrip(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/calculate-cost")
    public ResponseEntity<Double> calculateTripCost(@PathVariable Long id) {
        Double cost = tripService.calculateTotalCost(id);
        return ResponseEntity.ok(cost);
    }

    @GetMapping("/{id}/split-payment")
    public ResponseEntity<Double> splitPayment(@PathVariable Long id) {
        Double costPerPerson = tripService.splitPayment(id);
        return ResponseEntity.ok(costPerPerson);
    }

    @PostMapping("/{id}/share")
    public ResponseEntity<String> shareTrip(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String email = request.get("email");
        tripService.shareTrip(id, email);
        return ResponseEntity.ok("Trip shared successfully with " + email);
    }
}
