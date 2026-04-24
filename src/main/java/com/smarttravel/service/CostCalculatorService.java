package com.smarttravel.service;

import com.smarttravel.dto.CostCalculatorRequest;
import com.smarttravel.dto.TripCostDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Slf4j
public class CostCalculatorService {

    public TripCostDTO calculate(CostCalculatorRequest request) {
        int people = request.getNumberOfPeople() != null && request.getNumberOfPeople() > 0 ? request.getNumberOfPeople() : 1;
        int days   = request.getNumberOfDays()   != null && request.getNumberOfDays()   > 0 ? request.getNumberOfDays()   : 1;
        int rooms  = request.getNumberOfRooms()  != null && request.getNumberOfRooms()  > 0 ? request.getNumberOfRooms()  : 1;

        BigDecimal hotelRate  = orZero(request.getHotelPricePerNight());
        BigDecimal transport  = orZero(request.getTransportationCost());
        BigDecimal foodDay    = orZero(request.getFoodCostPerDay());
        BigDecimal activities = orZero(request.getActivitiesCost());
        BigDecimal other      = orZero(request.getOtherCosts());

        BigDecimal accommodation = hotelRate.multiply(bd(days)).multiply(bd(rooms));
        BigDecimal food          = foodDay.multiply(bd(days)).multiply(bd(people));
        BigDecimal total         = accommodation.add(transport).add(food).add(activities).add(other);
        BigDecimal perPerson     = total.divide(bd(people), 2, RoundingMode.HALF_UP);

        log.info("Trip cost calculated: total={}, perPerson={}", total, perPerson);

        return TripCostDTO.builder()
                .accommodationCost(accommodation)
                .transportationCost(transport)
                .foodCost(food)
                .activitiesCost(activities)
                .otherCosts(other)
                .totalCost(total)
                .costPerPerson(perPerson)
                .build();
    }

    private BigDecimal orZero(BigDecimal v) {
        return v != null ? v : BigDecimal.ZERO;
    }

    private BigDecimal bd(int v) {
        return BigDecimal.valueOf(v);
    }
}
