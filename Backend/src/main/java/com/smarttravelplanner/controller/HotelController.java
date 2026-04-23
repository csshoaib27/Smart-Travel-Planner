package com.smarttravelplanner.controller;

import com.smarttravelplanner.dto.HotelDTO;
import com.smarttravelplanner.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"})
public class HotelController {
    private final HotelService hotelService;

    @GetMapping
    public ResponseEntity<List<HotelDTO>> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDTO> getHotelById(@PathVariable Long id) {
        HotelDTO hotel = hotelService.getHotelById(id);
        return hotel != null ? ResponseEntity.ok(hotel) : ResponseEntity.notFound().build();
    }

    @GetMapping("/destination/{destinationId}")
    public ResponseEntity<List<HotelDTO>> getHotelsByDestination(@PathVariable Long destinationId) {
        return ResponseEntity.ok(hotelService.getHotelsByDestination(destinationId));
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<HotelDTO>> getHotelsByPriceRange(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {
        return ResponseEntity.ok(hotelService.getHotelsByPriceRange(minPrice, maxPrice));
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<List<HotelDTO>> getHotelsByRating(@PathVariable Double rating) {
        return ResponseEntity.ok(hotelService.getHotelsByMinimumRating(rating));
    }

    @GetMapping("/destination/{destinationId}/filter")
    public ResponseEntity<List<HotelDTO>> getHotelsByDestinationAndPrice(
            @PathVariable Long destinationId,
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {
        return ResponseEntity.ok(hotelService.getHotelsByDestinationAndPrice(destinationId, minPrice, maxPrice));
    }

    @PostMapping
    public ResponseEntity<HotelDTO> createHotel(@RequestBody HotelDTO dto) {
        return ResponseEntity.ok(hotelService.saveHotel(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelDTO> updateHotel(@PathVariable Long id, @RequestBody HotelDTO dto) {
        HotelDTO updated = hotelService.updateHotel(id, dto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }
}
