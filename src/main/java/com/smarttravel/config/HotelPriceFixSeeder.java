package com.smarttravel.config;

import com.smarttravel.model.Hotel;
import com.smarttravel.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Fixes two categories of wrong prices that exist in the DB:
 *
 * 1. sample_data.sql stored prices as USD-scale numbers (e.g. 350 for a Paris
 *    5-star) with a local currency label (EUR/JPY/IDR…). After we switched the
 *    display symbol to ₹ those show as ₹350 instead of ~₹29,050.
 *    Fix: multiply by 83 (≈ 1 USD → INR) and set currency to INR.
 *
 * 2. HotelDataSeeder used INR prices that were too low for Indian destinations
 *    (₹1,200 budget, ₹4,500 4-star, etc.).
 *    Fix: apply a corrected price by matching the seeder's name suffix pattern.
 */
@Component
@Order(2)
@RequiredArgsConstructor
@Slf4j
public class HotelPriceFixSeeder implements CommandLineRunner {

    private final HotelRepository hotelRepository;

    // Realistic INR replacements keyed on the name suffix HotelDataSeeder uses
    private static final double USD_TO_INR = 83.0;

    @Override
    public void run(String... args) {
        List<Hotel> hotels = hotelRepository.findAll();
        int fixed = 0;

        for (Hotel h : hotels) {
            boolean changed = false;

            // ── Fix 1: non-INR prices from sample_data.sql ──────────────────
            if (!"INR".equalsIgnoreCase(h.getCurrency())) {
                BigDecimal inr = h.getPricePerNight()
                        .multiply(BigDecimal.valueOf(USD_TO_INR))
                        .setScale(0, java.math.RoundingMode.HALF_UP);
                h.setPricePerNight(inr);
                h.setCurrency("INR");
                changed = true;
            }

            // ── Fix 2: INR prices from HotelDataSeeder that are too low ─────
            // Identify by name suffix; only correct if price is still the old value.
            else {
                String name = h.getName() == null ? "" : h.getName();
                long corrected = -1;

                if (name.endsWith("Budget Inn")   && price(h) <= 1200) corrected = 3_200;
                else if (name.contains("Guesthouse") && price(h) <= 1900) corrected = 5_500;
                else if (name.endsWith("Grand Hotel") && price(h) <= 4500) corrected = 9_500;
                else if (name.contains("Regency")  && price(h) <= 6500) corrected = 14_500;
                else if (name.endsWith("Palace")   && price(h) <= 16500) corrected = 25_000;
                else if (name.endsWith("Luxury Suites") && price(h) <= 23000) corrected = 38_000;

                if (corrected > 0) {
                    h.setPricePerNight(BigDecimal.valueOf(corrected));
                    changed = true;
                }
            }

            if (changed) {
                hotelRepository.save(h);
                fixed++;
            }
        }

        if (fixed > 0) log.info("HotelPriceFixSeeder: corrected prices for {} hotel(s)", fixed);
    }

    private long price(Hotel h) {
        return h.getPricePerNight() == null ? 0 : h.getPricePerNight().longValue();
    }
}
