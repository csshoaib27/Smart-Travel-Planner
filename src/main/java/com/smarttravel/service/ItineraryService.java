package com.smarttravel.service;

import com.smarttravel.model.Itinerary;
import com.smarttravel.model.ItineraryDay;
import com.smarttravel.repository.DestinationRepository;
import com.smarttravel.repository.ItineraryDayRepository;
import com.smarttravel.repository.ItineraryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItineraryService {

    private final ItineraryRepository itineraryRepository;
    private final ItineraryDayRepository itineraryDayRepository;
    private final DestinationRepository destinationRepository;
    private final GeminiService geminiService;

    @PersistenceContext
    private EntityManager em;

    // ── Activities by destination travel type ────────────────────────────────

    private static final Map<String, List<String>> ACTIVITIES = new LinkedHashMap<>();
    static {
        ACTIVITIES.put("Beach", List.of(
            "🌅 Morning: Sunrise walk on the beach | ☀️ Afternoon: Swimming & sunbathing | 🌊 Evening: Water sports & sunset viewing",
            "🤿 Morning: Snorkeling / scuba diving | 🍤 Afternoon: Beachside seafood lunch | 🎉 Evening: Beach shack & live music",
            "⛵ Morning: Boat trip to nearby islands | 🐟 Afternoon: Fishing experience | 🔥 Evening: Beach bonfire & BBQ",
            "🛒 Morning: Local fish market tour | 🚣 Afternoon: Kayaking along coast | 🍹 Evening: Sunset cocktails",
            "🏄 Morning: Surfing lessons | 🌮 Afternoon: Seafood street food | 💃 Evening: Beach party / cultural night",
            "🐚 Morning: Shell collecting & nature walk | 🛥️ Afternoon: Speed boat ride | 🌙 Evening: Moonlit beach dinner"
        ));
        ACTIVITIES.put("Mountain", List.of(
            "🥾 Morning: Sunrise trek to viewpoint | 🌿 Afternoon: Forest nature walk | 🏕️ Evening: Campfire & stargazing",
            "🏞️ Morning: Scenic waterfall hike | 🍽️ Afternoon: Local mountain cuisine | 🧘 Evening: Yoga & meditation session",
            "🦅 Morning: Bird watching walk | 🌲 Afternoon: Forest exploration & picnic | 🍵 Evening: Local tea house visit",
            "🚵 Morning: Mountain biking trail | 🏔️ Afternoon: Valley viewpoint photography | 🎭 Evening: Folk music performance",
            "🧗 Morning: Rock climbing / rappelling | 🌺 Afternoon: Alpine meadow walk | ⛺ Evening: Open-air camping setup",
            "🌁 Morning: Misty morning trek | 🎋 Afternoon: Bamboo grove walk | 🔭 Evening: Telescope stargazing"
        ));
        ACTIVITIES.put("Cultural", List.of(
            "🏛️ Morning: Heritage monument tour | 🛕 Afternoon: Temple / mosque visit | 🎭 Evening: Classical cultural show",
            "🏪 Morning: Local bazaar & handicrafts | 🍛 Afternoon: Traditional thali experience | 🎵 Evening: Folk music & dance",
            "🖼️ Morning: Museum & art gallery | 🚶 Afternoon: Heritage walk with local guide | 🌃 Evening: Light & sound show",
            "📸 Morning: Photography walk at monuments | 🛍️ Afternoon: Souvenir shopping | 🍝 Evening: Authentic local dining",
            "🎪 Morning: Craft village visit | 🧵 Afternoon: Textile weaving workshop | 🪔 Evening: Lantern festival / Aarti",
            "🏰 Morning: Fort / palace exploration | 🍱 Afternoon: Royal cuisine tasting | 💎 Evening: Jewellery market tour"
        ));
        ACTIVITIES.put("Nature", List.of(
            "🦁 Morning: Wildlife jeep safari | 🌳 Afternoon: Jungle nature walk | 🌿 Evening: Bonfire at eco-resort",
            "🦜 Morning: Bird watching with guide | 🌺 Afternoon: Botanical garden tour | 🦋 Evening: Nature photography session",
            "🐘 Morning: Elephant sanctuary visit | 🚣 Afternoon: River coracle ride | 🌅 Evening: Sunset at forest viewpoint",
            "🌲 Morning: Canopy walk / tree-top trail | 🍃 Afternoon: Herbal garden & spice walk | 🔭 Evening: Stargazing",
            "🐆 Morning: Early morning safari | 🏞️ Afternoon: Waterfall picnic | 🦎 Evening: Reptile & amphibian night walk",
            "🌊 Morning: River rafting | 🌾 Afternoon: Village farm tour | 🌙 Evening: Tribal campfire storytelling"
        ));
        ACTIVITIES.put("Adventure", List.of(
            "🪂 Morning: Paragliding / skydiving | 🧗 Afternoon: Rock climbing | 🏄 Evening: Surfing practice & bonfire",
            "🤸 Morning: Bungee jumping | 🚵 Afternoon: Mountain biking | 🏕️ Evening: Adventure camp dinner",
            "🛶 Morning: White water rafting | 🎯 Afternoon: Archery & camping skills | 🔥 Evening: Campfire cookout",
            "🏋️ Morning: Obstacle course challenge | 💦 Afternoon: Cliff jumping / waterfall rappelling | ⛺ Evening: Tent setup & stargazing",
            "🚁 Morning: Helicopter / microlight joyride | 🏹 Afternoon: Target shooting & ATV ride | 🎆 Evening: Sky lanterns",
            "🌊 Morning: Scuba diving certification dive | 🤿 Afternoon: Underwater photography | 🐠 Evening: Marine biology talk"
        ));
        ACTIVITIES.put("Luxury", List.of(
            "💆 Morning: Signature spa & wellness session | 🍽️ Afternoon: Fine dining at award-winning restaurant | 🍸 Evening: Rooftop bar",
            "🛥️ Morning: Private yacht cruise | 🥂 Afternoon: Gourmet lunch at 5-star | 🎭 Evening: Exclusive cultural gala",
            "🏌️ Morning: Golf at exclusive club | 💅 Afternoon: Luxury beauty & spa treatment | 🌹 Evening: Candlelit private dinner",
            "🛍️ Morning: Personal shopping with stylist | 🍣 Afternoon: Japanese / French fine dining | 🎬 Evening: Private cinema",
            "🚁 Morning: Helicopter sightseeing | 🍷 Afternoon: Wine & cheese experience | 🌌 Evening: Starlit pool dinner",
            "🎨 Morning: Private art studio tour | 🎻 Afternoon: Classical concert lunch | 🛁 Evening: Jacuzzi suite & room service"
        ));
        ACTIVITIES.put("Budget", List.of(
            "🚶 Morning: Self-guided city heritage walk | 🌮 Afternoon: Street food tour | 🌇 Evening: Free city viewpoint",
            "🚌 Morning: Local bus city tour | 🛒 Afternoon: Local market & budget lunch | 🎪 Evening: Free public events",
            "🏛️ Morning: Free museum / public park | 🍜 Afternoon: Dhaba / local canteen lunch | 🎶 Evening: Street performance",
            "📸 Morning: Neighbourhood photography walk | 🥘 Afternoon: Budget restaurant lunch | 🌃 Evening: City lights stroll",
            "🚲 Morning: Cycle tour of city | 🫖 Afternoon: Tea stall & local snacks | 🎡 Evening: Local fair or exhibition",
            "🌳 Morning: Public garden & lake walk | 🍲 Afternoon: Home-cook / tiffin meal | 🌠 Evening: Rooftop free stargazing"
        ));
    }

    // ── Daily budget estimates (INR) ─────────────────────────────────────────

    private static final Map<String, BigDecimal> DAILY_BUDGET = Map.of(
        "Economic", BigDecimal.valueOf(1500),
        "Premium",  BigDecimal.valueOf(5000),
        "Luxury",   BigDecimal.valueOf(15000)
    );

    // ── Package-type day notes ────────────────────────────────────────────────

    private static final Map<String, String[]> PACKAGE_NOTES = Map.of(
        "Solo",      new String[]{"Solo travel tip: use public transport to save money.",
                                   "Connect with other solo travellers at hostels.",
                                   "Take a free walking tour — great for solo explorers."},
        "Couple",    new String[]{"Romantic tip: book a sunset dinner for two tonight.",
                                   "Couples' spa package available at most resorts.",
                                   "Hire a private guide for a personalised day tour."},
        "Family",    new String[]{"Family tip: check kids-eat-free deals at restaurants.",
                                   "Look for family rooms or connecting hotel rooms.",
                                   "Most attractions offer discounted family tickets."},
        "Adventure", new String[]{"Book adventure activities in advance — slots fill fast.",
                                   "Carry a first-aid kit and stay hydrated on trails.",
                                   "Check weather forecast before outdoor adventure today."}
    );

    // ── Public API ────────────────────────────────────────────────────────────

    @Transactional
    public Itinerary createItinerary(Map<String, Object> payload) {
        String title       = str(payload, "title", "My Trip");
        String description = str(payload, "description", "");
        String startDate   = str(payload, "startDate", LocalDate.now().toString());
        String endDate     = str(payload, "endDate", LocalDate.now().plusDays(2).toString());
        String packageType = str(payload, "packageType", "Solo");
        String currency    = str(payload, "currency", "INR");
        String budgetTier  = str(payload, "budgetTier", "Economic");
        boolean isPublic   = Boolean.TRUE.equals(payload.get("isPublic"));
        Integer userId     = toInt(payload.get("userId"), 1);
        Integer destId     = toInt(payload.get("destinationId"), null);

        int days = calculateDays(startDate, endDate);

        Itinerary itinerary = Itinerary.builder()
                .userId(userId)
                .title(title)
                .description(description)
                .startDate(LocalDate.parse(startDate))
                .endDate(LocalDate.parse(endDate))
                .numberOfDays(days)
                .packageType(Itinerary.PackageType.valueOf(packageType))
                .currency(currency)
                .budgetTier(budgetTier)
                .isPublic(isPublic)
                .totalEstimatedCost(DAILY_BUDGET.getOrDefault(budgetTier, BigDecimal.valueOf(1500))
                        .multiply(BigDecimal.valueOf(days)))
                .build();

        itinerary = itineraryRepository.save(itinerary);

        if (destId != null) {
            generateDays(itinerary, destId, budgetTier, packageType, days,
                         startDate, currency, description);
            em.flush();
            em.refresh(itinerary);
        }

        return itinerary;
    }

    public Optional<Itinerary> getById(Integer id) {
        return itineraryRepository.findById(id);
    }

    public List<Itinerary> getByUser(Integer userId) {
        return itineraryRepository.findUserItinerariesByDate(userId);
    }

    @Transactional
    public void delete(Integer id) {
        itineraryRepository.deleteById(id);
    }

    // ── Day plan generation ───────────────────────────────────────────────────

    private void generateDays(Itinerary itinerary, Integer destId, String budgetTier,
                              String packageType, int numDays,
                              String startDate, String currency, String description) {

        var dest = destinationRepository.findById(destId).orElse(null);
        String destName   = dest != null ? dest.getName()    : "the destination";
        String country    = dest != null ? dest.getCountry() : "";
        String travelType = (dest != null && dest.getTravelType() != null)
                            ? dest.getTravelType().name() : "Cultural";

        // Try Gemini first, then local fallback
        List<Map<String, Object>> aiDays = geminiService.generateItineraryDays(
                destName, country, travelType, packageType, budgetTier, numDays,
                startDate, currency, description);
        String aiSource = "Gemini";

        List<ItineraryDay> dayList = new ArrayList<>();

        if (!aiDays.isEmpty()) {
            for (Map<String, Object> ai : aiDays) {
                ItineraryDay day = ItineraryDay.builder()
                        .itineraryId(itinerary.getItineraryId())
                        .dayNumber((Integer) ai.get("dayNumber"))
                        .destinationId(destId)
                        .activities((String) ai.get("activities"))
                        .notes((String) ai.get("notes"))
                        .estimatedBudget((BigDecimal) ai.get("estimatedBudget"))
                        .build();
                dayList.add(day);
            }
            log.info("Itinerary days generated by {} for '{}'", aiSource, destName);
        } else {
            // Local fallback
            log.info("Using local template for itinerary days (destination: '{}')", destName);
            List<String> pool = ACTIVITIES.getOrDefault(travelType,
                                ACTIVITIES.getOrDefault("Cultural", List.of("Explore the destination")));
            String[] notes   = PACKAGE_NOTES.getOrDefault(packageType, new String[]{"Enjoy your trip!"});
            BigDecimal dayBudget = DAILY_BUDGET.getOrDefault(budgetTier, BigDecimal.valueOf(1500));

            for (int d = 1; d <= numDays; d++) {
                ItineraryDay day = ItineraryDay.builder()
                        .itineraryId(itinerary.getItineraryId())
                        .dayNumber(d)
                        .destinationId(destId)
                        .activities(pool.get((d - 1) % pool.size()))
                        .notes(notes[(d - 1) % notes.length])
                        .estimatedBudget(dayBudget)
                        .build();
                dayList.add(day);
            }
        }

        itineraryDayRepository.saveAll(dayList);
    }

    // ── Utility ───────────────────────────────────────────────────────────────

    private int calculateDays(String start, String end) {
        try {
            long d = ChronoUnit.DAYS.between(LocalDate.parse(start), LocalDate.parse(end)) + 1;
            return (int) Math.max(1, d);
        } catch (Exception e) { return 1; }
    }

    private String str(Map<String, Object> m, String key, String def) {
        Object v = m.get(key);
        return (v instanceof String s && !s.isBlank()) ? s : def;
    }

    private Integer toInt(Object v, Integer def) {
        if (v instanceof Integer i) return i;
        if (v instanceof Number n) return n.intValue();
        if (v instanceof String s) { try { return Integer.parseInt(s); } catch (Exception e) { return def; } }
        return def;
    }
}
