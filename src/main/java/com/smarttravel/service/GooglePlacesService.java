package com.smarttravel.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

@Service
@Slf4j
public class GooglePlacesService {

    @Value("${google.places.api.key:}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String TEXT_SEARCH_URL =
            "https://maps.googleapis.com/maps/api/place/textsearch/json";

    public boolean isConfigured() {
        return apiKey != null && !apiKey.isBlank()
                && !apiKey.equals("YOUR_GOOGLE_PLACES_API_KEY");
    }

    public List<Map<String, Object>> searchHotels(String destination, String budgetTier) {
        if (!isConfigured()) {
            log.info("Google Places API key not configured — using DB fallback");
            return List.of();
        }
        try {
            String url = UriComponentsBuilder.fromHttpUrl(TEXT_SEARCH_URL)
                    .queryParam("query", "hotels in " + destination)
                    .queryParam("type", "lodging")
                    .queryParam("key", apiKey)
                    .build().toUriString();

            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response == null) return List.of();

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
            if (results == null || results.isEmpty()) return List.of();

            List<Map<String, Object>> hotels = new ArrayList<>();
            for (Map<String, Object> place : results) {
                Map<String, Object> hotel = new LinkedHashMap<>();
                hotel.put("name",        place.get("name"));
                hotel.put("address",     place.get("formatted_address"));
                hotel.put("rating",      place.get("rating"));
                hotel.put("reviewCount", place.get("user_ratings_total"));
                hotel.put("priceLevel",  place.get("price_level"));
                hotel.put("placeId",     place.get("place_id"));
                hotel.put("priceRange",  priceLevelToRange(toInt(place.get("price_level"))));
                hotel.put("priceIcons",  priceLevelToIcons(toInt(place.get("price_level"))));
                hotel.put("mapsUrl",     mapsUrl(place.get("place_id")));
                hotels.add(hotel);
            }

            return filterByBudget(hotels, budgetTier);

        } catch (Exception e) {
            log.error("Google Places API call failed: {}", e.getMessage());
            return List.of();
        }
    }

    private List<Map<String, Object>> filterByBudget(List<Map<String, Object>> hotels, String budgetTier) {
        // Try budget-matched filter first
        List<Map<String, Object>> matched = hotels.stream()
                .filter(h -> matchesBudget(h.get("priceLevel"), budgetTier))
                .limit(4)
                .toList();

        // Fallback: top 4 by rating
        if (matched.isEmpty()) {
            return hotels.stream()
                    .sorted(Comparator.comparingDouble(
                            h -> -toDouble(((Map<?, ?>) h).get("rating"))))
                    .limit(4)
                    .toList();
        }
        return matched;
    }

    private boolean matchesBudget(Object priceLevelObj, String budgetTier) {
        if (priceLevelObj == null) return true; // include if unknown
        int level = toInt(priceLevelObj);
        return switch (budgetTier) {
            case "Economic" -> level <= 1;
            case "Luxury"   -> level >= 3;
            default         -> level == 2 || level == 3; // Premium
        };
    }

    private String priceLevelToRange(int level) {
        return switch (level) {
            case 0  -> "Free";
            case 1  -> "Under ₹2,000/night";
            case 2  -> "₹2,000 – ₹5,000/night";
            case 3  -> "₹5,000 – ₹15,000/night";
            case 4  -> "Above ₹15,000/night";
            default -> "Price varies";
        };
    }

    private String priceLevelToIcons(int level) {
        if (level <= 0) return "";
        return "₹".repeat(Math.min(level, 4));
    }

    private String mapsUrl(Object placeId) {
        if (placeId == null) return "#";
        return "https://www.google.com/maps/place/?q=place_id:" + placeId;
    }

    private int toInt(Object v) {
        if (v instanceof Number n) return n.intValue();
        return 0;
    }

    private double toDouble(Object v) {
        if (v instanceof Number n) return n.doubleValue();
        return 0.0;
    }
}
