-- SMART TRAVEL PLANNER - SAMPLE DATA
-- Insert sample destinations, hotels, and reviews

USE smart_travel_db;

-- ==================== SAMPLE DESTINATIONS ====================
INSERT INTO destinations (name, country, description, temperature_avg, best_time_to_visit, currency, language, latitude, longitude, travel_type, budget_category, average_rating, image_url) VALUES
('Paris', 'France', 'The City of Light, famous for the Eiffel Tower, museums, and romantic atmosphere', 12, 'April to June, September to October', 'EUR', 'French', 48.8566, 2.3522, 'Cultural', 'Luxury', 4.7, 'https://example.com/paris.jpg'),
('Tokyo', 'Japan', 'Vibrant metropolis blending tradition and modernity with temples, gardens, and bustling streets', 16, 'March to May, September to November', 'JPY', 'Japanese', 35.6762, 139.6503, 'Cultural', 'Mid-Range', 4.8, 'https://example.com/tokyo.jpg'),
('New York', 'USA', 'The city that never sleeps with iconic skyscrapers, museums, and diverse culture', 12, 'April to June, September to October', 'USD', 'English', 40.7128, -74.0060, 'Adventure', 'Luxury', 4.5, 'https://example.com/newyork.jpg'),
('Bali', 'Indonesia', 'Tropical paradise with beautiful beaches, rice terraces, temples, and adventure activities', 28, 'April to June, September to October', 'IDR', 'Indonesian', -8.6705, 115.2126, 'Beach', 'Budget', 4.6, 'https://example.com/bali.jpg'),
('Swiss Alps', 'Switzerland', 'Stunning mountain scenery perfect for hiking, skiing, and outdoor adventures', 5, 'June to September (summer), December to March (winter)', 'CHF', 'German, French, Italian', 46.8182, 8.2275, 'Adventure', 'Luxury', 4.9, 'https://example.com/swissalps.jpg'),
('Bangkok', 'Thailand', 'Bustling capital with ancient temples, street food, vibrant nightlife, and markets', 27, 'November to February', 'THB', 'Thai', 13.7563, 100.5018, 'Cultural', 'Budget', 4.4, 'https://example.com/bangkok.jpg'),
('Barcelona', 'Spain', 'Coastal city known for Gaudí architecture, beaches, and Mediterranean charm', 14, 'April to June, September to October', 'EUR', 'Catalan, Spanish', 41.3851, 2.1734, 'Cultural', 'Mid-Range', 4.5, 'https://example.com/barcelona.jpg'),
('Maldives', 'Maldives', 'Exclusive island resort destination with crystal-clear waters and luxury accommodations', 28, 'November to April', 'MVR', 'Dhivehi', 4.1694, 73.5090, 'Beach', 'Luxury', 4.9, 'https://example.com/maldives.jpg'),
('Machu Picchu', 'Peru', 'Ancient Inca citadel high in the Andes, a wonder of the world', 12, 'May to October', 'PEN', 'Spanish, Quechua', -13.1631, -72.5450, 'Nature', 'Budget', 4.8, 'https://example.com/machupicchu.jpg'),
('Iceland', 'Iceland', 'Land of fire and ice with waterfalls, geysers, glaciers, and Northern Lights', 0, 'June to August (summer), September to March (Northern Lights)', 'ISK', 'Icelandic', 64.9631, -19.0208, 'Adventure', 'Luxury', 4.7, 'https://example.com/iceland.jpg');

-- ==================== PARIS HOTELS ====================
INSERT INTO hotels (destination_id, name, description, address, check_in_time, check_out_time, star_rating, price_per_night, currency, room_types, amenities, average_rating, available_rooms, image_url) VALUES
(1, 'Eiffel Tower Hotel', 'Luxury 5-star hotel near the Eiffel Tower', '123 Avenue de la Tour, Paris', '15:00', '11:00', 5, 350.00, 'EUR', 'Single,Double,Suite', 'WiFi,Pool,Gym,Restaurant,Spa,Parking', 4.8, 15, 'https://example.com/eiffel-hotel.jpg'),
(1, 'Marais Boutique', 'Charming 4-star boutique hotel in the historic Marais district', '45 Rue des Rosiers, Paris', '15:00', '11:00', 4, 200.00, 'EUR', 'Double,Suite', 'WiFi,Restaurant,AC', 4.5, 20, 'https://example.com/marais-hotel.jpg'),
(1, 'Budget Paris Inn', 'Affordable 3-star hotel with basic amenities', '789 Rue de la Paix, Paris', '14:00', '10:00', 3, 80.00, 'EUR', 'Single,Double', 'WiFi,AC', 4.0, 25, 'https://example.com/budget-paris.jpg'),
(1, 'Seine River Mansion', 'Elegant 4-star hotel overlooking the Seine River', '321 Quai de la Seine, Paris', '15:00', '11:00', 4, 220.00, 'EUR', 'Double,Suite,Deluxe', 'WiFi,Restaurant,Gym,Parking', 4.6, 18, 'https://example.com/seine-hotel.jpg'),
(1, 'Latin Quarter Lodge', '3-star hotel in the vibrant Latin Quarter', '456 Rue Mouffetard, Paris', '14:00', '10:00', 3, 95.00, 'EUR', 'Single,Double', 'WiFi,AC,TV', 4.2, 22, 'https://example.com/latin-hotel.jpg'),
(1, 'Champs-Élysées Palace', '5-star luxury hotel on famous Champs-Élysées', '789 Avenue des Champs-Élysées, Paris', '15:00', '11:00', 5, 400.00, 'EUR', 'Suite,Deluxe', 'WiFi,Pool,Gym,Restaurant,Spa,Parking,TV', 4.9, 12, 'https://example.com/champs-palace.jpg'),
(1, 'Montmartre Retreat', '4-star hotel in artistic Montmartre area', '234 Rue Lepic, Paris', '15:00', '11:00', 4, 180.00, 'EUR', 'Double,Suite', 'WiFi,Restaurant,AC', 4.4, 21, 'https://example.com/montmartre-hotel.jpg'),
(1, 'Le Marais Express', '2-star budget hotel with clean rooms', '567 Rue Saint-Antoine, Paris', '13:00', '10:00', 2, 60.00, 'EUR', 'Single,Double', 'WiFi', 3.8, 30, 'https://example.com/marais-express.jpg'),
(1, 'Versailles Grand', '5-star palace hotel near Versailles', '111 Boulevard de Versailles, Paris', '15:00', '11:00', 5, 380.00, 'EUR', 'Suite,Deluxe', 'WiFi,Pool,Gym,Restaurant,Spa,Parking', 4.7, 14, 'https://example.com/versailles-grand.jpg'),
(1, 'Notre-Dame View Hotel', '4-star hotel with views of Notre-Dame Cathedral', '654 Rue de Rivoli, Paris', '15:00', '11:00', 4, 210.00, 'EUR', 'Double,Suite', 'WiFi,Restaurant,AC,Parking', 4.5, 19, 'https://example.com/notredame-hotel.jpg'),
(1, 'Arc de Triomphe Suite', '3-star hotel near Arc de Triomphe', '876 Avenue Kleber, Paris', '14:00', '10:00', 3, 110.00, 'EUR', 'Double,Suite', 'WiFi,AC,TV,Restaurant', 4.3, 23, 'https://example.com/arc-hotel.jpg'),
(1, 'Louvre District Inn', '4-star hotel near the Louvre Museum', '345 Rue de Rivoli, Paris', '15:00', '11:00', 4, 190.00, 'EUR', 'Double,Suite', 'WiFi,Restaurant,Gym,AC', 4.4, 20, 'https://example.com/louvre-inn.jpg');

-- ==================== TOKYO HOTELS ====================
INSERT INTO hotels (destination_id, name, description, address, check_in_time, check_out_time, star_rating, price_per_night, currency, room_types, amenities, average_rating, available_rooms, image_url) VALUES
(2, 'Shinjuku Palace Hotel', 'Luxury 5-star hotel in Shinjuku district', '1-1 Shinjuku, Tokyo', '15:00', '11:00', 5, 280.00, 'JPY', 'Single,Double,Suite', 'WiFi,Pool,Gym,Restaurant,Spa', 4.8, 12, 'https://example.com/shinjuku-palace.jpg'),
(2, 'Shibuya Modern', '4-star contemporary hotel in Shibuya', '2-1 Shibuya, Tokyo', '15:00', '11:00', 4, 180.00, 'JPY', 'Double,Suite', 'WiFi,Restaurant,AC', 4.5, 18, 'https://example.com/shibuya-modern.jpg'),
(2, 'Asakusa Heritage', '3-star traditional hotel near Senso-ji Temple', '3-1 Asakusa, Tokyo', '14:00', '10:00', 3, 85.00, 'JPY', 'Single,Double', 'WiFi,AC', 4.2, 24, 'https://example.com/asakusa-heritage.jpg'),
(2, 'Ginza Luxury Suites', '5-star premium hotel in Ginza shopping district', '4-1 Ginza, Tokyo', '15:00', '11:00', 5, 320.00, 'JPY', 'Suite,Deluxe', 'WiFi,Pool,Gym,Restaurant,Spa,Parking', 4.9, 10, 'https://example.com/ginza-luxury.jpg'),
(2, 'Harajuku Youth Hostel', '2-star budget accommodation for travelers', '5-1 Harajuku, Tokyo', '13:00', '10:00', 2, 50.00, 'JPY', 'Single,Double', 'WiFi', 3.9, 28, 'https://example.com/harajuku-youth.jpg'),
(2, 'Ikebukuro Comfort', '4-star mid-range hotel in Ikebukuro', '6-1 Ikebukuro, Tokyo', '15:00', '11:00', 4, 170.00, 'JPY', 'Double,Suite', 'WiFi,Restaurant,AC', 4.4, 20, 'https://example.com/ikebukuro-comfort.jpg'),
(2, 'Tokyo Tower View', '3-star hotel with views of Tokyo Tower', '7-1 Minato, Tokyo', '14:00', '10:00', 3, 95.00, 'JPY', 'Double,Suite', 'WiFi,AC,Restaurant', 4.3, 22, 'https://example.com/tokyo-tower.jpg'),
(2, 'Ueno Park Hotel', '4-star hotel near Ueno Park', '8-1 Ueno, Tokyo', '15:00', '11:00', 4, 190.00, 'JPY', 'Double,Suite', 'WiFi,Gym,Restaurant,Parking', 4.5, 19, 'https://example.com/ueno-park.jpg'),
(2, 'Roppongi Sky Hotel', '5-star luxury hotel in Roppongi', '9-1 Roppongi, Tokyo', '15:00', '11:00', 5, 300.00, 'JPY', 'Suite,Deluxe', 'WiFi,Pool,Gym,Restaurant,Spa', 4.7, 14, 'https://example.com/roppongi-sky.jpg'),
(2, 'Odaiba Marina', '4-star hotel near Odaiba waterfront', '10-1 Odaiba, Tokyo', '15:00', '11:00', 4, 175.00, 'JPY', 'Double,Suite', 'WiFi,Restaurant,AC,Gym', 4.4, 21, 'https://example.com/odaiba-marina.jpg'),
(2, 'Chiyoda Business Hotel', '3-star hotel near Imperial Palace', '11-1 Chiyoda, Tokyo', '14:00', '10:00', 3, 80.00, 'JPY', 'Single,Double', 'WiFi,AC,TV', 4.1, 25, 'https://example.com/chiyoda-business.jpg'),
(2, 'Meguro Boutique', '4-star boutique hotel in Meguro', '12-1 Meguro, Tokyo', '15:00', '11:00', 4, 185.00, 'JPY', 'Double,Suite', 'WiFi,Restaurant,Gym,AC', 4.6, 18, 'https://example.com/meguro-boutique.jpg');

-- ==================== BALI HOTELS ====================
INSERT INTO hotels (destination_id, name, description, address, check_in_time, check_out_time, star_rating, price_per_night, currency, room_types, amenities, average_rating, available_rooms, image_url) VALUES
(4, 'Ubud Luxury Resort', 'Luxury 5-star resort in Ubud with rice terraces', 'Ubud, Bali', '15:00', '11:00', 5, 150.00, 'IDR', 'Suite,Deluxe', 'WiFi,Pool,Gym,Restaurant,Spa', 4.9, 12, 'https://example.com/ubud-luxury.jpg'),
(4, 'Seminyak Beach Club', '4-star beachfront hotel in Seminyak', 'Seminyak, Bali', '15:00', '11:00', 4, 90.00, 'IDR', 'Double,Suite', 'WiFi,Pool,Restaurant,Beach Access', 4.6, 20, 'https://example.com/seminyak-beach.jpg'),
(4, 'Kuta Surfside', '3-star budget hotel in Kuta Beach', 'Kuta, Bali', '14:00', '10:00', 3, 45.00, 'IDR', 'Single,Double', 'WiFi,AC', 4.2, 26, 'https://example.com/kuta-surfside.jpg'),
(4, 'Canggu Terrace', '4-star hotel in trendy Canggu area', 'Canggu, Bali', '15:00', '11:00', 4, 85.00, 'IDR', 'Double,Suite', 'WiFi,Restaurant,AC,Gym', 4.5, 22, 'https://example.com/canggu-terrace.jpg'),
(4, 'Sanur Marina', '3-star hotel near Sanur Harbor', 'Sanur, Bali', '14:00', '10:00', 3, 55.00, 'IDR', 'Double,Suite', 'WiFi,AC,Restaurant', 4.3, 24, 'https://example.com/sanur-marina.jpg'),
(4, 'Denpasar Urban', '2-star budget hotel in Denpasar', 'Denpasar, Bali', '13:00', '10:00', 2, 35.00, 'IDR', 'Single,Double', 'WiFi', 3.8, 30, 'https://example.com/denpasar-urban.jpg'),
(4, 'Jimbaran Bay Resort', '5-star luxury resort overlooking Jimbaran Bay', 'Jimbaran, Bali', '15:00', '11:00', 5, 180.00, 'IDR', 'Suite,Deluxe', 'WiFi,Pool,Gym,Restaurant,Spa,Beach', 4.8, 14, 'https://example.com/jimbaran-bay.jpg'),
(4, 'Nusa Dua Palace', '5-star resort in exclusive Nusa Dua', 'Nusa Dua, Bali', '15:00', '11:00', 5, 160.00, 'IDR', 'Suite,Deluxe', 'WiFi,Pool,Gym,Restaurant,Beach', 4.7, 16, 'https://example.com/nusa-dua.jpg'),
(4, 'Uluwatu Cliffside', '4-star hotel perched on Uluwatu cliffs', 'Uluwatu, Bali', '15:00', '11:00', 4, 95.00, 'IDR', 'Double,Suite', 'WiFi,Pool,Restaurant,Gym', 4.6, 19, 'https://example.com/uluwatu-cliff.jpg'),
(4, 'Petitela Hideaway', '3-star private villa hotel in Petite', 'Petitenget, Bali', '14:00', '10:00', 3, 65.00, 'IDR', 'Double,Suite', 'WiFi,AC,Restaurant', 4.4, 23, 'https://example.com/petitela.jpg'),
(4, 'Seminyak Budget Hostel', '2-star backpacker hostel in Seminyak', 'Seminyak, Bali', '13:00', '10:00', 2, 30.00, 'IDR', 'Single,Double', 'WiFi', 3.7, 32, 'https://example.com/seminyak-hostel.jpg'),
(4, 'Tegallalang Green Resort', '4-star eco-resort in rice terrace area', 'Tegallalang, Bali', '15:00', '11:00', 4, 100.00, 'IDR', 'Double,Suite', 'WiFi,Pool,Restaurant,Eco-Friendly', 4.5, 20, 'https://example.com/tegallalang.jpg');

-- ==================== SAMPLE REVIEWS ====================
INSERT INTO reviews (user_id, destination_id, hotel_id, rating, title, content, review_type, helpful_count) VALUES
(1, 1, 1, 5, 'Amazing Stay at Eiffel Tower Hotel', 'The hotel exceeded all my expectations. The location is perfect, the service is impeccable, and the rooms are luxurious. Highly recommended!', 'Hotel', 23),
(1, 1, 2, 4.5, 'Charming Boutique Hotel in Marais', 'Great location in the historic Marais district. The staff is very friendly and the room is clean and cozy. Would stay again!', 'Hotel', 18),
(1, 1, NULL, 4.8, 'Paris is Magical', 'Paris lived up to all the hype. The Eiffel Tower, the museums, the food - everything is incredible. A must-visit destination!', 'Destination', 45),
(1, 2, 4, 5, 'Tokyo is Unforgettable', 'This city is a perfect blend of tradition and modernity. The food is amazing, the culture is rich, and the people are friendly. Loved it!', 'Destination', 52),
(1, 4, 7, 4.7, 'Paradise in Bali', 'Bali is everything you dream of - beautiful beaches, friendly people, and affordable prices. Perfect vacation destination!', 'Destination', 38);

-- ==================== UPDATE RATINGS ====================
-- Update destination ratings
UPDATE destinations SET average_rating = 4.8, review_count = 1 WHERE destination_id = 1;
UPDATE destinations SET average_rating = 4.8, review_count = 1 WHERE destination_id = 2;
UPDATE destinations SET average_rating = 4.7, review_count = 1 WHERE destination_id = 4;

-- Update hotel ratings
UPDATE hotels SET average_rating = 5, review_count = 1 WHERE hotel_id = 1;
UPDATE hotels SET average_rating = 4.5, review_count = 1 WHERE hotel_id = 2;
UPDATE hotels SET average_rating = 5, review_count = 1 WHERE hotel_id = 4;

COMMIT;
