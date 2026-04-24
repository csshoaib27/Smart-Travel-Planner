package com.smarttravel.service;

import com.smarttravel.dto.HotelDTO;
import com.smarttravel.model.Hotel;
import com.smarttravel.repository.HotelRepository;
import com.smarttravel.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelService {

    private final HotelRepository hotelRepository;
    private final ReviewRepository reviewRepository;

    public HotelDTO createHotel(Hotel hotel) {
        log.info("Creating new hotel: {}", hotel.getName());
        Hotel saved = hotelRepository.save(hotel);
        return mapToDTO(saved);
    }

    public HotelDTO getHotelById(Integer id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        // Update rating
        Double avgRating = reviewRepository.getAverageRatingForHotel(id);
        if (avgRating != null) {
            hotel.setAverageRating(avgRating);
            hotel.setReviewCount(
                    (int) reviewRepository.findByHotelId(id).size()
            );
            hotelRepository.save(hotel);
        }

        return mapToDTO(hotel);
    }

    public List<HotelDTO> getHotelsByDestination(Integer destinationId) {
        log.info("Fetching hotels for destination: {}", destinationId);
        return hotelRepository.findByDestinationId(destinationId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<HotelDTO> getHotelsByDestinationAndPrice(Integer destinationId,
                                                         BigDecimal minPrice,
                                                         BigDecimal maxPrice) {
        log.info("Searching hotels in destination {} between {} and {}",
                destinationId, minPrice, maxPrice);

        return hotelRepository.findByDestinationAndPriceRange(destinationId, minPrice, maxPrice)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<HotelDTO> getTopHotelsInDestination(Integer destinationId) {
        log.info("Fetching top-rated hotels for destination: {}", destinationId);
        return hotelRepository.findTopHotelsInDestination(destinationId)
                .stream()
                .map(this::mapToDTO)
                .limit(5)
                .collect(Collectors.toList());
    }

    public List<HotelDTO> getAllHotels() {
        return hotelRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public HotelDTO updateHotel(Integer id, Hotel updated) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        if (updated.getName() != null) hotel.setName(updated.getName());
        if (updated.getDescription() != null) hotel.setDescription(updated.getDescription());
        if (updated.getAddress() != null) hotel.setAddress(updated.getAddress());
        if (updated.getPricePerNight() != null) hotel.setPricePerNight(updated.getPricePerNight());
        if (updated.getStarRating() != null) hotel.setStarRating(updated.getStarRating());
        if (updated.getAmenities() != null) hotel.setAmenities(updated.getAmenities());
        if (updated.getAvailableRooms() != null) hotel.setAvailableRooms(updated.getAvailableRooms());
        if (updated.getImageUrl() != null) hotel.setImageUrl(updated.getImageUrl());

        Hotel saved = hotelRepository.save(hotel);
        return mapToDTO(saved);
    }

    public void deleteHotel(Integer id) {
        hotelRepository.deleteById(id);
        log.info("Hotel deleted: {}", id);
    }

    public boolean checkAvailability(Integer hotelId, Integer numberOfRooms) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        return hotel.getAvailableRooms() >= numberOfRooms;
    }

    public void updateAvailableRooms(Integer hotelId, Integer roomsToReduce) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        int newAvailable = hotel.getAvailableRooms() - roomsToReduce;
        if (newAvailable < 0) {
            throw new RuntimeException("Not enough available rooms");
        }

        hotel.setAvailableRooms(newAvailable);
        hotelRepository.save(hotel);
        log.info("Updated available rooms for hotel {}: {}", hotelId, newAvailable);
    }

    private HotelDTO mapToDTO(Hotel hotel) {
        return HotelDTO.builder()
                .hotelId(hotel.getHotelId())
                .destinationId(hotel.getDestinationId())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .address(hotel.getAddress())
                .starRating(hotel.getStarRating())
                .pricePerNight(hotel.getPricePerNight())
                .currency(hotel.getCurrency())
                .roomTypes(hotel.getRoomTypes())
                .amenities(hotel.getAmenities())
                .averageRating(hotel.getAverageRating())
                .reviewCount(hotel.getReviewCount())
                .availableRooms(hotel.getAvailableRooms())
                .build();
    }
}
