package com.smarttravel.config;

import com.smarttravel.model.Destination;
import com.smarttravel.model.Hotel;
import com.smarttravel.repository.DestinationRepository;
import com.smarttravel.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class HotelDataSeeder implements CommandLineRunner {

    private final DestinationRepository destinationRepository;
    private final HotelRepository hotelRepository;

    @Override
    public void run(String... args) {
        List<Destination> destinations = destinationRepository.findAll();
        int seeded = 0;
        for (Destination dest : destinations) {
            if (hotelRepository.findByDestinationId(dest.getDestinationId()).isEmpty()) {
                hotelRepository.saveAll(buildHotels(dest));
                seeded++;
            }
        }
        if (seeded > 0) log.info("Hotel seeder: added hotels for {} destination(s)", seeded);
    }

    private List<Hotel> buildHotels(Destination dest) {
        String n = dest.getName();
        int id = dest.getDestinationId();
        List<Hotel> list = new ArrayList<>();

        // ── Economic ─────────────────────────────────────────────────────────
        list.add(Hotel.builder()
                .destinationId(id).name(n + " Budget Inn")
                .description("Comfortable budget stay in the heart of " + n + ". Great value for money.")
                .address("Market Road, " + n)
                .starRating(3.0).pricePerNight(bd(3200)).currency("INR")
                .roomTypes("Single Room, Double Room")
                .amenities("Free WiFi, AC, 24hr Front Desk, Hot Water")
                .averageRating(3.8).reviewCount(312).availableRooms(18)
                .checkInTime("14:00").checkOutTime("11:00").build());

        list.add(Hotel.builder()
                .destinationId(id).name("The " + n + " Guesthouse")
                .description("Friendly guesthouse ideal for solo travellers and backpackers exploring " + n + ".")
                .address("Backpacker Street, " + n)
                .starRating(3.0).pricePerNight(bd(5500)).currency("INR")
                .roomTypes("Dormitory, Private Room")
                .amenities("Free WiFi, Common Kitchen, Lounge, Tours Desk, Lockers")
                .averageRating(4.1).reviewCount(489).availableRooms(22)
                .checkInTime("13:00").checkOutTime("11:00").build());

        // ── Premium ───────────────────────────────────────────────────────────
        list.add(Hotel.builder()
                .destinationId(id).name(n + " Grand Hotel")
                .description("A premium hotel offering spacious rooms, a rooftop pool, and prime access to " + n + "'s attractions.")
                .address("Central Avenue, " + n)
                .starRating(4.0).pricePerNight(bd(9500)).currency("INR")
                .roomTypes("Deluxe Room, Superior Room, Family Suite")
                .amenities("Free WiFi, Swimming Pool, Multi-cuisine Restaurant, Gym, AC, Room Service, Spa")
                .averageRating(4.3).reviewCount(1342).availableRooms(12)
                .checkInTime("14:00").checkOutTime("12:00").build());

        list.add(Hotel.builder()
                .destinationId(id).name("Hotel " + n + " Regency")
                .description("Elegant four-star property blending modern comfort with local charm in " + n + ".")
                .address("Heritage Road, " + n)
                .starRating(4.0).pricePerNight(bd(14500)).currency("INR")
                .roomTypes("Superior Room, Junior Suite, Deluxe Suite")
                .amenities("Free WiFi, Rooftop Pool, Fine Dining, Business Centre, Concierge, Airport Transfer")
                .averageRating(4.4).reviewCount(978).availableRooms(9)
                .checkInTime("15:00").checkOutTime("12:00").build());

        // ── Luxury ────────────────────────────────────────────────────────────
        list.add(Hotel.builder()
                .destinationId(id).name("The " + n + " Palace")
                .description("Award-winning 5-star luxury hotel with panoramic views and world-class service in " + n + ".")
                .address("Palace Drive, " + n)
                .starRating(5.0).pricePerNight(bd(25000)).currency("INR")
                .roomTypes("Luxury Room, Grand Suite, Presidential Suite")
                .amenities("Free WiFi, Infinity Pool, Luxury Spa, Fine Dining, Valet, Concierge, Butler Service, Helipad")
                .averageRating(4.8).reviewCount(2241).availableRooms(6)
                .checkInTime("15:00").checkOutTime("13:00").build());

        list.add(Hotel.builder()
                .destinationId(id).name(n + " Luxury Suites")
                .description("Ultra-premium all-suite property in " + n + " — an exclusive sanctuary for discerning travellers.")
                .address("Luxury Boulevard, " + n)
                .starRating(5.0).pricePerNight(bd(38000)).currency("INR")
                .roomTypes("Executive Suite, Sky Villa, Royal Penthouse")
                .amenities("Free WiFi, Private Pool, Award-winning Restaurant, Full-service Spa, Personal Butler, Private Dining")
                .averageRating(4.7).reviewCount(1634).availableRooms(4)
                .checkInTime("14:00").checkOutTime("13:00").build());

        return list;
    }

    private BigDecimal bd(long v) { return BigDecimal.valueOf(v); }
}
