package com.smarttravelplanner.service;

import com.smarttravelplanner.dto.HotelDTO;
import com.smarttravelplanner.entity.Hotel;
import com.smarttravelplanner.entity.Destination;
import com.smarttravelplanner.repository.HotelRepository;
import com.smarttravelplanner.repository.DestinationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HotelService {
    private final HotelRepository hotelRepository;
    private final DestinationRepository destinationRepository;

    public List<HotelDTO> getAllHotels() {
        return hotelRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public HotelDTO getHotelById(Long id) {
        return hotelRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<HotelDTO> getHotelsByDestination(Long destinationId) {
        return hotelRepository.findByDestinationId(destinationId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<HotelDTO> getHotelsByPriceRange(Double minPrice, Double maxPrice) {
        return hotelRepository.findByPriceRange(minPrice, maxPrice)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<HotelDTO> getHotelsByMinimumRating(Double minRating) {
        return hotelRepository.findByMinimumRating(minRating)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<HotelDTO> getHotelsByDestinationAndPrice(Long destinationId, Double minPrice, Double maxPrice) {
        return hotelRepository.findByDestinationIdAndPricePerNightBetween(destinationId, minPrice, maxPrice)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public HotelDTO saveHotel(HotelDTO dto) {
        Hotel hotel = convertToEntity(dto);
        Hotel saved = hotelRepository.save(hotel);
        return convertToDTO(saved);
    }

    public HotelDTO updateHotel(Long id, HotelDTO dto) {
        return hotelRepository.findById(id)
                .map(existing -> {
                    existing.setName(dto.getName());
                    existing.setPricePerNight(dto.getPricePerNight());
                    existing.setRating(dto.getRating());
                    existing.setDescription(dto.getDescription());
                    Hotel updated = hotelRepository.save(existing);
                    return convertToDTO(updated);
                })
                .orElse(null);
    }

    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }

    private HotelDTO convertToDTO(Hotel hotel) {
        return HotelDTO.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .destinationId(hotel.getDestination().getId())
                .city(hotel.getCity())
                .address(hotel.getAddress())
                .pricePerNight(hotel.getPricePerNight())
                .rating(hotel.getRating())
                .amenities(hotel.getAmenities())
                .image(hotel.getImage())
                .description(hotel.getDescription())
                .roomTypes(hotel.getRoomTypes())
                .contact(hotel.getContact())
                .checkInTime(hotel.getCheckInTime())
                .checkOutTime(hotel.getCheckOutTime())
                .wifi(hotel.getWifi())
                .parking(hotel.getParking())
                .gym(hotel.getGym())
                .restaurant(hotel.getRestaurant())
                .build();
    }

    private Hotel convertToEntity(HotelDTO dto) {
        Destination destination = destinationRepository.findById(dto.getDestinationId()).orElse(null);
        return Hotel.builder()
                .name(dto.getName())
                .destination(destination)
                .city(dto.getCity())
                .address(dto.getAddress())
                .pricePerNight(dto.getPricePerNight())
                .rating(dto.getRating())
                .amenities(dto.getAmenities())
                .image(dto.getImage())
                .description(dto.getDescription())
                .roomTypes(dto.getRoomTypes())
                .contact(dto.getContact())
                .checkInTime(dto.getCheckInTime())
                .checkOutTime(dto.getCheckOutTime())
                .wifi(dto.getWifi() != null ? dto.getWifi() : true)
                .parking(dto.getParking() != null ? dto.getParking() : false)
                .gym(dto.getGym() != null ? dto.getGym() : false)
                .restaurant(dto.getRestaurant() != null ? dto.getRestaurant() : false)
                .build();
    }
}
