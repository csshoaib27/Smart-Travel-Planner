package com.smarttravel.config;

import com.smarttravel.model.Destination;
import com.smarttravel.repository.DestinationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Order(1)
@RequiredArgsConstructor
@Slf4j
public class DestinationImageSeeder implements CommandLineRunner {

    private final DestinationRepository destinationRepository;

    private static final Map<String, String> IMAGE_MAP = Map.ofEntries(
        Map.entry("Paris",        "https://images.unsplash.com/photo-1502602898657-3e91760cbb34?w=600"),
        Map.entry("Tokyo",        "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?w=600"),
        Map.entry("New York",     "https://images.unsplash.com/photo-1496442226666-8d4d0e62e6e9?w=600"),
        Map.entry("Bali",         "https://images.unsplash.com/photo-1537996194471-e657df975ab4?w=600"),
        Map.entry("Swiss Alps",   "https://images.unsplash.com/photo-1531366936337-7c912a4589a7?w=600"),
        Map.entry("Bangkok",      "https://images.unsplash.com/photo-1563492065599-3520f775eeed?w=600"),
        Map.entry("Barcelona",    "https://images.unsplash.com/photo-1583422409516-2895a77efded?w=600"),
        Map.entry("Maldives",     "https://images.unsplash.com/photo-1514282401047-d79a71a590e8?w=600"),
        Map.entry("Machu Picchu", "https://images.unsplash.com/photo-1580502304784-8985b7eb7260?w=600"),
        Map.entry("Iceland",      "https://images.unsplash.com/photo-1504893524553-b855bce32c67?w=600")
    );

    @Override
    public void run(String... args) {
        int updated = 0;
        for (Destination dest : destinationRepository.findAll()) {
            String url = dest.getImageUrl();
            if (url != null && url.contains("example.com")) {
                String fresh = IMAGE_MAP.get(dest.getName());
                if (fresh != null) {
                    dest.setImageUrl(fresh);
                    destinationRepository.save(dest);
                    updated++;
                }
            }
        }
        if (updated > 0) log.info("DestinationImageSeeder: updated image URLs for {} destination(s)", updated);
    }
}
