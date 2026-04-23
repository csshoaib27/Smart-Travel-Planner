package com.smarttravelplanner.controller;

import com.smarttravelplanner.dto.DestinationDTO;
import com.smarttravelplanner.service.DestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/destinations")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
public class DestinationController {
    private final DestinationService destinationService;

    @GetMapping
    public ResponseEntity<List<DestinationDTO>> getAllDestinations() {
        return ResponseEntity.ok(destinationService.getAllDestinations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DestinationDTO> getDestinationById(@PathVariable Long id) {
        DestinationDTO destination = destinationService.getDestinationById(id);
        return destination != null ? ResponseEntity.ok(destination) : ResponseEntity.notFound().build();
    }

    @GetMapping("/budget/{budget}")
    public ResponseEntity<List<DestinationDTO>> getDestinationsByBudget(@PathVariable String budget) {
        return ResponseEntity.ok(destinationService.getDestinationsByBudget(budget));
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<DestinationDTO>> getDestinationsByPriceRange(
            @RequestParam Integer minCost,
            @RequestParam Integer maxCost) {
        return ResponseEntity.ok(destinationService.getDestinationsByBudgetRange(minCost, maxCost));
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<DestinationDTO>> getDestinationsByRating(@PathVariable Double rating) {
        return ResponseEntity.ok(destinationService.getDestinationsByMinimumRating(rating));
    }

    @GetMapping("/search")
    public ResponseEntity<List<DestinationDTO>> searchDestinations(@RequestParam String query) {
        return ResponseEntity.ok(destinationService.searchDestinations(query));
    }

    @PostMapping
    public ResponseEntity<DestinationDTO> createDestination(@RequestBody DestinationDTO dto) {
        return ResponseEntity.ok(destinationService.saveDestination(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DestinationDTO> updateDestination(@PathVariable Long id, @RequestBody DestinationDTO dto) {
        DestinationDTO updated = destinationService.updateDestination(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable Long id) {
        destinationService.deleteDestination(id);
        return ResponseEntity.noContent().build();
    }
}
