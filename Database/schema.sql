-- Create Database
CREATE DATABASE IF NOT EXISTS smart_travel_planner;
USE smart_travel_planner;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    profile_image VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    active BOOLEAN DEFAULT TRUE,
    INDEX idx_email (email)
);

-- Destinations Table
CREATE TABLE IF NOT EXISTS destinations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description LONGTEXT,
    country VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    region VARCHAR(100),
    latitude DOUBLE,
    longitude DOUBLE,
    budget VARCHAR(20) NOT NULL,
    temperature INT,
    best_time_to_visit VARCHAR(500),
    distance INT,
    rating DOUBLE,
    review_count INT DEFAULT 0,
    interests VARCHAR(500),
    image VARCHAR(500),
    travel_time VARCHAR(255),
    cost_per_day INT,
    activities VARCHAR(1000),
    safety_rating INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_budget (budget),
    INDEX idx_city (city),
    INDEX idx_country (country)
);

-- Hotels Table
CREATE TABLE IF NOT EXISTS hotels (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    destination_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    address VARCHAR(500),
    price_per_night DOUBLE NOT NULL,
    rating DOUBLE,
    amenities VARCHAR(1000),
    image VARCHAR(500),
    description LONGTEXT,
    room_types VARCHAR(500),
    contact VARCHAR(100),
    check_in_time VARCHAR(50),
    check_out_time VARCHAR(50),
    wifi BOOLEAN DEFAULT TRUE,
    parking BOOLEAN DEFAULT FALSE,
    gym BOOLEAN DEFAULT FALSE,
    restaurant BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (destination_id) REFERENCES destinations(id) ON DELETE CASCADE,
    INDEX idx_destination (destination_id),
    INDEX idx_price (price_per_night)
);

-- Trips Table
CREATE TABLE IF NOT EXISTS trips (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    destination_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description LONGTEXT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    number_of_days INT,
    budget DOUBLE NOT NULL,
    package_mode VARCHAR(20) NOT NULL,
    total_cost DOUBLE,
    participants INT DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (destination_id) REFERENCES destinations(id) ON DELETE CASCADE,
    INDEX idx_user (user_id),
    INDEX idx_destination (destination_id)
);

-- Itinerary Days Table
CREATE TABLE IF NOT EXISTS itinerary_days (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    trip_id BIGINT NOT NULL,
    day_number INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    notes LONGTEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE,
    INDEX idx_trip (trip_id)
);

-- Activities Table
CREATE TABLE IF NOT EXISTS activities (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    itinerary_day_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description LONGTEXT,
    time VARCHAR(100),
    cost DOUBLE,
    duration VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (itinerary_day_id) REFERENCES itinerary_days(id) ON DELETE CASCADE,
    INDEX idx_itinerary (itinerary_day_id)
);

-- Meals Table
CREATE TABLE IF NOT EXISTS meals (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    itinerary_day_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(20) NOT NULL,
    cuisine_type VARCHAR(100),
    cost DOUBLE,
    traditional BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (itinerary_day_id) REFERENCES itinerary_days(id) ON DELETE CASCADE,
    INDEX idx_itinerary (itinerary_day_id)
);

-- Reviews Table
CREATE TABLE IF NOT EXISTS reviews (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    destination_id BIGINT,
    rating INT NOT NULL,
    comment LONGTEXT,
    helpful_count INT DEFAULT 0,
    trip_type VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (destination_id) REFERENCES destinations(id) ON DELETE CASCADE,
    INDEX idx_destination (destination_id),
    INDEX idx_user (user_id)
);

-- Safety Alerts Table
CREATE TABLE IF NOT EXISTS safety_alerts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    destination_id BIGINT NOT NULL,
    type VARCHAR(50) NOT NULL,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(500),
    phone VARCHAR(20),
    email VARCHAR(255),
    latitude DOUBLE,
    longitude DOUBLE,
    description LONGTEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (destination_id) REFERENCES destinations(id) ON DELETE CASCADE,
    INDEX idx_destination (destination_id),
    INDEX idx_type (type)
);

-- Traditional Foods Table
CREATE TABLE IF NOT EXISTS traditional_foods (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    destination_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description LONGTEXT,
    cuisine_type VARCHAR(100),
    price DOUBLE,
    dietary_options VARCHAR(500),
    image VARCHAR(500),
    best_place VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (destination_id) REFERENCES destinations(id) ON DELETE CASCADE,
    INDEX idx_destination (destination_id)
);

-- Trip Hotels Table
CREATE TABLE IF NOT EXISTS trip_hotels (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    trip_id BIGINT NOT NULL,
    hotel_id BIGINT NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    number_of_rooms INT,
    room_type VARCHAR(100),
    total_cost DOUBLE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE,
    FOREIGN KEY (hotel_id) REFERENCES hotels(id) ON DELETE CASCADE,
    INDEX idx_trip (trip_id),
    INDEX idx_hotel (hotel_id)
);

-- Trip Shares Table
CREATE TABLE IF NOT EXISTS trip_shares (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    trip_id BIGINT NOT NULL,
    shared_with_email VARCHAR(255) NOT NULL,
    shared_with_name VARCHAR(255),
    permission VARCHAR(20) NOT NULL,
    shared_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE,
    INDEX idx_trip (trip_id),
    INDEX idx_email (shared_with_email)
);

-- Sample Data - Destinations
INSERT INTO destinations (name, description, country, city, region, latitude, longitude, budget, temperature, best_time_to_visit, distance, rating, review_count, interests, travel_time, cost_per_day, activities, safety_rating) VALUES
('Goa', 'Tropical beaches and vibrant nightlife in South India', 'India', 'Panaji', 'South India', 15.3, 73.85, 'LOW', 28, 'November,December,January,February', 1200, 4.5, 1250, 'beach,adventure,nightlife,budget', '2-3 hours flight from Delhi', 1500, 'Beach hopping,Water sports,Nightclubs,Beach shacks,Trek to Dudhsagar Waterfall', 8),
('Manali', 'Mountain paradise with adventure sports and scenic beauty', 'India', 'Manali', 'Himachal Pradesh', 32.24, 77.19, 'MEDIUM', 15, 'March,April,September,October', 1500, 4.8, 980, 'adventure,nature,hiking,budget', '1.5 hours flight from Delhi', 2000, 'Paragliding,Rock climbing,Trekking,River rafting,Mountain biking', 9),
('Kerala', 'Serene backwaters and tropical beaches in South India', 'India', 'Kochi', 'South India', 9.97, 76.29, 'MEDIUM', 26, 'June,July,August,September', 2000, 4.7, 1500, 'nature,relaxation,culture,budget', '2.5 hours flight from Delhi', 2200, 'Houseboat backwaters,Beach walks,Spice plantation tours,Ayurveda,Fishing', 9),
('Jaipur', 'The Pink City with historic forts and cultural heritage', 'India', 'Jaipur', 'Rajasthan', 26.91, 75.78, 'LOW', 35, 'November,December,January,February', 260, 4.4, 2100, 'culture,history,budget,photography', '4 hours drive from Delhi', 1800, 'City Palace visit,Hawa Mahal tour,Jantar Mantar,Market shopping,Local cuisine', 8),
('Ladakh', 'High altitude desert with stunning landscapes and adventure', 'India', 'Leh', 'North India', 34.16, 77.58, 'HIGH', 5, 'June,July,August,September', 1200, 4.9, 650, 'adventure,nature,photography,spiritual', '1 hour flight from Delhi', 3500, 'Trekking,Motorbike tours,Monasteries,Lakes,High altitude camping', 7);

-- Sample Data - Hotels
INSERT INTO hotels (destination_id, name, city, address, price_per_night, rating, amenities, description, room_types, contact, check_in_time, check_out_time, wifi, restaurant) VALUES
(1, 'Taj Exotica Resort & Spa', 'Panaji', 'Goa', 8000, 4.8, 'Wifi,Pool,Spa,Restaurant', 'Luxury beach resort with stunning views', 'Deluxe,Suite', '0832-6645858', '14:00', '11:00', TRUE, TRUE),
(1, 'Budget Beach Hotel Goa', 'Panaji', 'Goa', 1500, 4.0, 'Wifi,Restaurant', 'Budget friendly beach hotel', 'Standard,Double', '0832-2345678', '14:00', '11:00', TRUE, TRUE),
(2, 'The Himalayan Resort', 'Manali', 'Himachal Pradesh', 5000, 4.7, 'Wifi,Gym,Restaurant', 'Mountain view resort perfect for adventure', 'Deluxe,Adventure', '01902-220055', '14:00', '11:00', TRUE, TRUE),
(2, 'Manali Budget Inn', 'Manali', 'Himachal Pradesh', 1200, 3.9, 'Wifi', 'Budget accommodation in the hills', 'Standard,Triple', '01902-240404', '14:00', '11:00', TRUE, FALSE),
(3, 'Waterfront Palace Kerala', 'Kochi', 'South India', 6000, 4.6, 'Wifi,Pool,Backwater Access,Restaurant', 'Houseboat and resort combination', 'Houseboat,Deluxe', '0484-2217171', '14:00', '11:00', TRUE, TRUE),
(3, 'Kerala Budget Homestay', 'Kochi', 'South India', 1800, 4.1, 'Wifi,Kitchen', 'Authentic homestay experience', 'Standard,Family', '0484-2567890', '14:00', '11:00', TRUE, FALSE);
