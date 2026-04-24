-- SMART TRAVEL PLANNER DATABASE SCHEMA
-- MySQL Database
-- Run this script to set up the complete database structure

CREATE DATABASE IF NOT EXISTS smart_travel_db;
USE smart_travel_db;

-- ============================================
-- USER TABLE
-- ============================================
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100),
    phone VARCHAR(20),
    is_admin BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- DESTINATIONS TABLE
-- ============================================
CREATE TABLE destinations (
    destination_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    country VARCHAR(100) NOT NULL,
    description TEXT,
    temperature_avg DECIMAL(5,2),
    best_time_to_visit VARCHAR(100),
    currency VARCHAR(10),
    language VARCHAR(100),
    latitude DECIMAL(10,8),
    longitude DECIMAL(11,8),
    travel_type ENUM('Nature', 'Adventure', 'Budget', 'Luxury', 'Cultural', 'Beach', 'Mountain') NOT NULL,
    budget_category ENUM('Budget', 'Mid-Range', 'Luxury') NOT NULL,
    average_rating DECIMAL(3,2) DEFAULT 0,
    review_count INT DEFAULT 0,
    image_url VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_country (country),
    INDEX idx_travel_type (travel_type),
    INDEX idx_budget (budget_category),
    FULLTEXT INDEX ft_search (name, country, description)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- HOTELS TABLE (10-15 per city)
-- ============================================
CREATE TABLE hotels (
    hotel_id INT PRIMARY KEY AUTO_INCREMENT,
    destination_id INT NOT NULL,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    address VARCHAR(255),
    check_in_time VARCHAR(10),
    check_out_time VARCHAR(10),
    star_rating DECIMAL(3,2),
    price_per_night DECIMAL(10,2) NOT NULL,
    currency VARCHAR(10) DEFAULT 'USD',
    room_types SET('Single', 'Double', 'Suite', 'Deluxe') DEFAULT 'Double',
    amenities SET('WiFi', 'Pool', 'Gym', 'Restaurant', 'Spa', 'Parking', 'AC', 'TV') DEFAULT 'WiFi',
    average_rating DECIMAL(3,2) DEFAULT 0,
    review_count INT DEFAULT 0,
    image_url VARCHAR(255),
    available_rooms INT DEFAULT 10,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (destination_id) REFERENCES destinations(destination_id) ON DELETE CASCADE,
    INDEX idx_destination (destination_id),
    INDEX idx_price (price_per_night),
    FULLTEXT INDEX ft_hotel_search (name, description)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- BOOKINGS TABLE
-- ============================================
CREATE TABLE bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    hotel_id INT NOT NULL,
    destination_id INT NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL,
    number_of_nights INT NOT NULL,
    number_of_guests INT NOT NULL,
    number_of_rooms INT NOT NULL,
    room_type VARCHAR(50),
    total_price DECIMAL(10,2) NOT NULL,
    status ENUM('Pending', 'Confirmed', 'Cancelled', 'Completed') DEFAULT 'Pending',
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (hotel_id) REFERENCES hotels(hotel_id) ON DELETE CASCADE,
    FOREIGN KEY (destination_id) REFERENCES destinations(destination_id) ON DELETE CASCADE,
    INDEX idx_user (user_id),
    INDEX idx_hotel (hotel_id),
    INDEX idx_status (status),
    INDEX idx_booking_date (booking_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- ITINERARIES TABLE
-- ============================================
CREATE TABLE itineraries (
    itinerary_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    number_of_days INT NOT NULL,
    package_type ENUM('Family', 'Solo', 'Couple', 'Adventure') DEFAULT 'Solo',
    total_estimated_cost DECIMAL(12,2),
    currency VARCHAR(10) DEFAULT 'USD',
    is_public BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_user (user_id),
    INDEX idx_dates (start_date, end_date),
    FULLTEXT INDEX ft_itinerary (title, description)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- ITINERARY DAYS TABLE
-- ============================================
CREATE TABLE itinerary_days (
    itinerary_day_id INT PRIMARY KEY AUTO_INCREMENT,
    itinerary_id INT NOT NULL,
    day_number INT NOT NULL,
    destination_id INT NOT NULL,
    activities TEXT,
    accommodation_id INT,
    notes TEXT,
    estimated_budget DECIMAL(10,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (itinerary_id) REFERENCES itineraries(itinerary_id) ON DELETE CASCADE,
    FOREIGN KEY (destination_id) REFERENCES destinations(destination_id),
    UNIQUE KEY unique_itinerary_day (itinerary_id, day_number),
    INDEX idx_destination (destination_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- REVIEWS & RATINGS TABLE
-- ============================================
CREATE TABLE reviews (
    review_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    destination_id INT,
    hotel_id INT,
    rating DECIMAL(3,2) NOT NULL CHECK (rating >= 1 AND rating <= 5),
    title VARCHAR(150),
    content TEXT NOT NULL,
    review_type ENUM('Destination', 'Hotel') NOT NULL,
    helpful_count INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (destination_id) REFERENCES destinations(destination_id) ON DELETE CASCADE,
    FOREIGN KEY (hotel_id) REFERENCES hotels(hotel_id) ON DELETE CASCADE,
    INDEX idx_destination (destination_id),
    INDEX idx_hotel (hotel_id),
    INDEX idx_rating (rating),
    INDEX idx_user (user_id),
    FULLTEXT INDEX ft_review (title, content)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- COST BREAKDOWN TABLE
-- ============================================
CREATE TABLE cost_breakdown (
    cost_id INT PRIMARY KEY AUTO_INCREMENT,
    itinerary_id INT NOT NULL,
    category ENUM('Travel', 'Accommodation', 'Food', 'Activities', 'Other') NOT NULL,
    description VARCHAR(255),
    amount DECIMAL(10,2) NOT NULL,
    currency VARCHAR(10) DEFAULT 'USD',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (itinerary_id) REFERENCES itineraries(itinerary_id) ON DELETE CASCADE,
    INDEX idx_itinerary (itinerary_id),
    INDEX idx_category (category)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- SEARCH HISTORY TABLE
-- ============================================
CREATE TABLE search_history (
    search_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    search_query VARCHAR(255),
    filters JSON,
    results_count INT,
    search_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_user (user_id),
    INDEX idx_search_date (search_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- PAYMENT SPLITS TABLE (for group travel)
-- ============================================
CREATE TABLE payment_splits (
    split_id INT PRIMARY KEY AUTO_INCREMENT,
    booking_id INT NOT NULL,
    user_id INT NOT NULL,
    amount_owed DECIMAL(10,2) NOT NULL,
    amount_paid DECIMAL(10,2) DEFAULT 0,
    status ENUM('Pending', 'Paid') DEFAULT 'Pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_booking (booking_id),
    INDEX idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================
-- CREATE INDEXES FOR BETTER PERFORMANCE
-- ============================================
CREATE INDEX idx_hotel_rating ON hotels(average_rating);
CREATE INDEX idx_destination_rating ON destinations(average_rating);
CREATE INDEX idx_users_admin ON users(is_admin);

-- ============================================
-- INSERT SAMPLE ADMIN USER
-- ============================================
INSERT INTO users (username, email, password, full_name, is_admin)
VALUES ('admin', 'admin@smarttravel.com', 'admin', 'Administrator', TRUE);

COMMIT;
