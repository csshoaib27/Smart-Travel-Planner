package com.smarttravel.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class GeminiService {

    @Value("${gemini.api.key:}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String GEMINI_URL =
        "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=";

    public GeminiService() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5_000);
        factory.setReadTimeout(30_000);
        this.restTemplate = new RestTemplate(factory);
    }

    public boolean isConfigured() {
        return apiKey != null && !apiKey.isBlank() && !apiKey.startsWith("YOUR_");
    }

    public List<Map<String, Object>> generateItineraryDays(
            String destinationName, String country, String travelType,
            String packageType, String budgetTier, int numDays,
            String startDate, String currency, String description) {

        if (!isConfigured()) {
            log.info("Gemini API key not configured — using local itinerary generator");
            return List.of();
        }

        String prompt = buildPrompt(destinationName, country, travelType,
                                    packageType, budgetTier, numDays,
                                    startDate, currency, description);
        try {
            String requestBody = objectMapper.writeValueAsString(Map.of(
                "contents", List.of(Map.of("parts", List.of(Map.of("text", prompt)))),
                "generationConfig", Map.of(
                    "temperature", 0.8,
                    "maxOutputTokens", 8192
                )
            ));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            ResponseEntity<String> response = restTemplate.exchange(
                GEMINI_URL + apiKey,
                HttpMethod.POST,
                new HttpEntity<>(requestBody, headers),
                String.class
            );

            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                log.warn("Gemini returned status {}", response.getStatusCode());
                return List.of();
            }

            return parseResponse(response.getBody(), budgetTier);

        } catch (Exception e) {
            log.warn("Gemini API call failed: {} — falling back to local generator", e.getMessage());
            return List.of();
        }
    }

    public List<String> getCitiesForCountry(String country) {
        if (!isConfigured()) return List.of();

        String prompt = """
            List exactly 10 popular tourist cities and destinations in %s.
            Return ONLY a raw JSON array of strings, no markdown, no explanation:
            ["City1", "City2", "City3"]
            """.formatted(country);

        try {
            String requestBody = objectMapper.writeValueAsString(Map.of(
                "contents", List.of(Map.of("parts", List.of(Map.of("text", prompt)))),
                "generationConfig", Map.of("temperature", 0.3, "maxOutputTokens", 300)
            ));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<String> response = restTemplate.exchange(
                GEMINI_URL + apiKey,
                HttpMethod.POST,
                new HttpEntity<>(requestBody, headers),
                String.class
            );
            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) return List.of();

            JsonNode root = objectMapper.readTree(response.getBody());
            String text = root.path("candidates").path(0)
                              .path("content").path("parts").path(0)
                              .path("text").asText("");
            text = text.replaceAll("(?s)```json\\s*", "").replaceAll("(?s)```\\s*", "").trim();
            int start = text.indexOf('[');
            int end   = text.lastIndexOf(']');
            if (start == -1 || end == -1) return List.of();
            JsonNode arr = objectMapper.readTree(text.substring(start, end + 1));
            if (!arr.isArray()) return List.of();

            List<String> cities = new ArrayList<>();
            for (JsonNode c : arr) cities.add(c.asText());
            log.info("Gemini suggested {} cities for {}", cities.size(), country);
            return cities;
        } catch (Exception e) {
            log.warn("Gemini cities request failed for {}: {}", country, e.getMessage());
            return List.of();
        }
    }

    private String buildPrompt(String destination, String country, String travelType,
                                String packageType, String budgetTier, int numDays,
                                String startDate, String currency, String description) {

        int dailyBudget = switch (budgetTier) {
            case "Premium" -> 5000;
            case "Luxury"  -> 15000;
            default        -> 1500;
        };

        String packageContext = switch (packageType) {
            case "Couple"    -> "romantic couple getaway — focus on intimate experiences, scenic dinners, and shared adventures";
            case "Family"    -> "family trip with children — prioritise kid-friendly activities, family dining, and safe attractions";
            case "Adventure" -> "adventure trip — include thrilling outdoor activities like trekking, water sports, or extreme experiences";
            default          -> "solo traveller — focus on flexibility, budget-friendly options, and meeting locals";
        };

        String budgetContext = switch (budgetTier) {
            case "Premium" -> "mid-range (3–4 star hotels, sit-down restaurants, comfortable transport)";
            case "Luxury"  -> "luxury (5-star hotels, fine dining, private transfers, exclusive experiences)";
            default        -> "budget (hostels or budget guesthouses, street food and local eateries, public transport)";
        };

        String travelContext = switch (travelType) {
            case "Beach"     -> "beach and coastal experiences";
            case "Mountain"  -> "mountain and trekking experiences";
            case "Nature"    -> "nature, wildlife and eco-tourism";
            case "Adventure" -> "adventure and outdoor activities";
            case "Luxury"    -> "luxury and premium experiences";
            default          -> "cultural sightseeing and local experiences";
        };

        String descNote = (description != null && !description.isBlank())
            ? "\nTraveller's note: " + description : "";

        String dateNote = (startDate != null && !startDate.isBlank())
            ? "\nTravel starts: " + startDate + " — factor in the season, local weather, and any festivals or events around that time." : "";

        return """
            You are an expert travel planner with deep knowledge of %s, %s.

            Create a highly detailed, day-by-day %d-day travel itinerary with the following specifics:
            - Destination: %s, %s
            - Travel style: %s
            - Trip focus: %s
            - Budget level: %s (%s %d/day)%s%s

            OUTPUT FORMAT — return ONLY a raw JSON array, no markdown, no code fences:
            [
              {
                "dayNumber": 1,
                "activities": "Morning: <specific activity with real place name and why it suits this traveller> | Afternoon: <specific activity> | Evening: <specific activity with dining suggestion>",
                "notes": "<one highly practical, specific tip for this traveller type at this destination — could be about transport, bargaining, timing, what to wear, local customs, money-saving hack, or safety>",
                "estimatedBudget": %d
              }
            ]

            Rules:
            - Return exactly %d JSON objects
            - Use REAL, specific place names, restaurant names, markets, and attractions in %s
            - Each day should feel distinct — no repeated activities or venues
            - Morning/Afternoon/Evening in activities joined by " | "
            - Tailor every activity to the %s traveller with %s budget
            - notes must be genuinely useful and specific to %s, not generic travel advice
            - estimatedBudget: %d for every day
            - Raw JSON array only — no extra text before or after
            """.formatted(
                destination, country,
                numDays,
                destination, country,
                packageContext,
                travelContext,
                budgetTier, currency, dailyBudget,
                dateNote, descNote,
                dailyBudget,
                numDays,
                destination,
                packageType, budgetTier,
                destination,
                dailyBudget
            );
    }

    private List<Map<String, Object>> parseResponse(String body, String budgetTier) {
        try {
            JsonNode root = objectMapper.readTree(body);
            String text = root.path("candidates").path(0)
                              .path("content").path("parts").path(0)
                              .path("text").asText("");

            text = text.replaceAll("(?s)```json\\s*", "").replaceAll("(?s)```\\s*", "").trim();

            int start = text.indexOf('[');
            int end   = text.lastIndexOf(']');
            if (start == -1 || end == -1 || end <= start) {
                log.warn("Gemini response did not contain a JSON array");
                return List.of();
            }
            text = text.substring(start, end + 1);

            JsonNode days = objectMapper.readTree(text);
            if (!days.isArray()) return List.of();

            BigDecimal budget = switch (budgetTier) {
                case "Premium" -> BigDecimal.valueOf(5000);
                case "Luxury"  -> BigDecimal.valueOf(15000);
                default        -> BigDecimal.valueOf(1500);
            };

            List<Map<String, Object>> result = new ArrayList<>();
            for (JsonNode day : days) {
                Map<String, Object> m = new LinkedHashMap<>();
                m.put("dayNumber",       day.path("dayNumber").asInt(result.size() + 1));
                m.put("activities",      day.path("activities").asText("Explore the destination"));
                m.put("notes",           day.path("notes").asText(""));
                m.put("estimatedBudget", BigDecimal.valueOf(day.path("estimatedBudget").asDouble(budget.doubleValue())));
                result.add(m);
            }

            log.info("Gemini (gemini-2.5-flash) generated {} day plan(s) for itinerary", result.size());
            return result;

        } catch (Exception e) {
            log.warn("Failed to parse Gemini response: {}", e.getMessage());
            return List.of();
        }
    }
}
