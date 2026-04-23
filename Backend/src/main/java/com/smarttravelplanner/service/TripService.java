package com.smarttravelplanner.service;

import com.smarttravelplanner.dto.TripDTO;
import com.smarttravelplanner.entity.*;
import com.smarttravelplanner.repository.TripRepository;
import com.smarttravelplanner.repository.UserRepository;
import com.smarttravelplanner.repository.DestinationRepository;
import com.smarttravelplanner.repository.TripShareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TripService {
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final DestinationRepository destinationRepository;
    private final TripShareRepository tripShareRepository;

    public List<TripDTO> getAllTrips() {
        return tripRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TripDTO getTripById(Long id) {
        return tripRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public List<TripDTO> getUserTrips(Long userId) {
        return tripRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TripDTO createTrip(TripDTO dto) {
        User user = userRepository.findById(dto.getUserId()).orElse(null);
        Destination destination = destinationRepository.findById(dto.getDestinationId()).orElse(null);

        if (user == null || destination == null) {
            return null;
        }

        Trip trip = Trip.builder()
                .user(user)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .destination(destination)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .numberOfDays(calculateDays(dto.getStartDate(), dto.getEndDate()))
                .budget(dto.getBudget())
                .packageMode(Trip.PackageMode.valueOf(dto.getPackageMode().toUpperCase()))
                .participants(dto.getParticipants() != null ? dto.getParticipants() : 1)
                .build();

        Trip saved = tripRepository.save(trip);
        return convertToDTO(saved);
    }

    public TripDTO updateTrip(Long id, TripDTO dto) {
        return tripRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(dto.getTitle());
                    existing.setDescription(dto.getDescription());
                    existing.setBudget(dto.getBudget());
                    existing.setParticipants(dto.getParticipants());
                    Trip updated = tripRepository.save(existing);
                    return convertToDTO(updated);
                })
                .orElse(null);
    }

    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }

    public Double calculateTotalCost(Long tripId) {
        return tripRepository.findById(tripId)
                .map(trip -> {
                    double totalCost = 0;
                    for (TripHotel hotel : trip.getHotels()) {
                        totalCost += hotel.getTotalCost() != null ? hotel.getTotalCost() : 0;
                    }
                    trip.setTotalCost(totalCost);
                    tripRepository.save(trip);
                    return totalCost;
                })
                .orElse(0.0);
    }

    public Double splitPayment(Long tripId) {
        return tripRepository.findById(tripId)
                .map(trip -> {
                    if (trip.getTotalCost() != null && trip.getParticipants() > 0) {
                        return trip.getTotalCost() / trip.getParticipants();
                    }
                    return 0.0;
                })
                .orElse(0.0);
    }

    public void shareTrip(Long tripId, String email) {
        tripRepository.findById(tripId)
                .ifPresent(trip -> {
                    TripShare share = TripShare.builder()
                            .trip(trip)
                            .sharedWithEmail(email)
                            .permission(TripShare.SharePermission.VIEW)
                            .build();
                    tripShareRepository.save(share);
                });
    }

    private int calculateDays(LocalDate startDate, LocalDate endDate) {
        return (int) (java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1);
    }

    private TripDTO convertToDTO(Trip trip) {
        return TripDTO.builder()
                .id(trip.getId())
                .userId(trip.getUser().getId())
                .title(trip.getTitle())
                .description(trip.getDescription())
                .destinationId(trip.getDestination().getId())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .numberOfDays(trip.getNumberOfDays())
                .budget(trip.getBudget())
                .packageMode(trip.getPackageMode().toString())
                .totalCost(trip.getTotalCost())
                .participants(trip.getParticipants())
                .build();
    }
}
