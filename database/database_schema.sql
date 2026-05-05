-- ============================================================
-- SMART TRAVEL PLANNER — DATABASE SETUP SCRIPT
-- ============================================================
-- Run this once on a fresh machine:
--   mysql -u root -p < database/database_schema.sql
--
-- After running, start the Spring Boot app:
--   mvn spring-boot:run
-- The app will auto-create the admin user (admin / admin)
-- and seed hotels for every destination on first startup.
--
-- To load all destinations and sample data, use the merged seed file:
--   mysql -u root smart_travel_db < database/seed_data.sql
-- (india_destinations.sql and sample_data.sql are superseded by seed_data.sql)
-- ============================================================

CREATE DATABASE IF NOT EXISTS smart_travel_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;

USE smart_travel_db;

-- ============================================================
-- USERS
-- ============================================================
CREATE TABLE IF NOT EXISTS users (
    user_id     INT          NOT NULL AUTO_INCREMENT,
    username    VARCHAR(50)  NOT NULL,
    email       VARCHAR(100) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    full_name   VARCHAR(100),
    phone       VARCHAR(20),
    is_admin    TINYINT(1)   DEFAULT 0,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id),
    UNIQUE KEY uq_username (username),
    UNIQUE KEY uq_email    (email),
    INDEX idx_username (username),
    INDEX idx_email    (email),
    INDEX idx_is_admin (is_admin)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- DESTINATIONS
-- ============================================================
CREATE TABLE IF NOT EXISTS destinations (
    destination_id     INT          NOT NULL AUTO_INCREMENT,
    name               VARCHAR(100) NOT NULL,
    country            VARCHAR(100) NOT NULL,
    description        TEXT,
    temperature_avg    DOUBLE,
    best_time_to_visit VARCHAR(100),
    currency           VARCHAR(10),
    language           VARCHAR(100),
    latitude           DOUBLE,
    longitude          DOUBLE,
    travel_type        ENUM('Nature','Adventure','Budget','Luxury','Cultural','Beach','Mountain') NOT NULL,
    budget_category    VARCHAR(255),
    average_rating     DOUBLE,
    review_count       INT DEFAULT 0,
    image_url          VARCHAR(255),
    created_at         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (destination_id),
    INDEX idx_country     (country),
    INDEX idx_travel_type (travel_type),
    INDEX idx_budget      (budget_category),
    INDEX idx_rating      (average_rating),
    FULLTEXT KEY ft_search (name, country, description)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- HOTELS
-- ============================================================
CREATE TABLE IF NOT EXISTS hotels (
    hotel_id         INT            NOT NULL AUTO_INCREMENT,
    destination_id   INT            NOT NULL,
    name             VARCHAR(150)   NOT NULL,
    description      TEXT,
    address          VARCHAR(255),
    check_in_time    VARCHAR(10),
    check_out_time   VARCHAR(10),
    star_rating      DOUBLE,
    price_per_night  DECIMAL(10,2)  NOT NULL,
    currency         VARCHAR(10)    DEFAULT 'USD',
    room_types       VARCHAR(100),
    amenities        TEXT,
    average_rating   DOUBLE,
    review_count     INT            DEFAULT 0,
    image_url        VARCHAR(255),
    available_rooms  INT            DEFAULT 10,
    created_at       TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (hotel_id),
    INDEX idx_destination (destination_id),
    INDEX idx_price       (price_per_night),
    INDEX idx_rating      (average_rating),
    FULLTEXT KEY ft_hotel_search (name, description),
    CONSTRAINT fk_hotel_destination
        FOREIGN KEY (destination_id) REFERENCES destinations(destination_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- ITINERARIES
-- ============================================================
CREATE TABLE IF NOT EXISTS itineraries (
    itinerary_id         INT           NOT NULL AUTO_INCREMENT,
    user_id              INT           NOT NULL,
    title                VARCHAR(150)  NOT NULL,
    description          TEXT,
    start_date           DATE          NOT NULL,
    end_date             DATE          NOT NULL,
    number_of_days       INT           NOT NULL,
    package_type         ENUM('Family','Solo','Couple','Adventure') DEFAULT 'Solo',
    budget_tier          VARCHAR(20),
    total_estimated_cost DECIMAL(12,2),
    currency             VARCHAR(10)   DEFAULT 'INR',
    is_public            TINYINT(1)    DEFAULT 0,
    created_at           TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    updated_at           TIMESTAMP     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (itinerary_id),
    INDEX idx_user  (user_id),
    INDEX idx_dates (start_date, end_date),
    FULLTEXT KEY ft_itinerary (title, description),
    CONSTRAINT fk_itinerary_user
        FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- ITINERARY DAYS
-- ============================================================
CREATE TABLE IF NOT EXISTS itinerary_days (
    itinerary_day_id INT            NOT NULL AUTO_INCREMENT,
    itinerary_id     INT            NOT NULL,
    day_number       INT            NOT NULL,
    destination_id   INT            NOT NULL,
    activities       TEXT,
    accommodation_id INT,
    notes            TEXT,
    estimated_budget DECIMAL(10,2),
    created_at       TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (itinerary_day_id),
    UNIQUE KEY uq_itinerary_day (itinerary_id, day_number),
    INDEX idx_destination (destination_id),
    CONSTRAINT fk_day_itinerary
        FOREIGN KEY (itinerary_id) REFERENCES itineraries(itinerary_id) ON DELETE CASCADE,
    CONSTRAINT fk_day_destination
        FOREIGN KEY (destination_id) REFERENCES destinations(destination_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- BOOKINGS
-- ============================================================
CREATE TABLE IF NOT EXISTS bookings (
    booking_id       INT           NOT NULL AUTO_INCREMENT,
    user_id          INT           NOT NULL,
    hotel_id         INT           NOT NULL,
    destination_id   INT           NOT NULL,
    check_in_date    DATE          NOT NULL,
    check_out_date   DATE          NOT NULL,
    number_of_nights INT           NOT NULL,
    number_of_guests INT           NOT NULL,
    number_of_rooms  INT           NOT NULL,
    room_type        VARCHAR(50),
    total_price      DECIMAL(10,2) NOT NULL,
    status           ENUM('Pending','Confirmed','Cancelled','Completed') DEFAULT 'Pending',
    booking_date     TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (booking_id),
    INDEX idx_user         (user_id),
    INDEX idx_hotel        (hotel_id),
    INDEX idx_destination  (destination_id),
    INDEX idx_status       (status),
    INDEX idx_booking_date (booking_date),
    CONSTRAINT fk_booking_user        FOREIGN KEY (user_id)        REFERENCES users(user_id)             ON DELETE CASCADE,
    CONSTRAINT fk_booking_hotel       FOREIGN KEY (hotel_id)       REFERENCES hotels(hotel_id)           ON DELETE CASCADE,
    CONSTRAINT fk_booking_destination FOREIGN KEY (destination_id) REFERENCES destinations(destination_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- REVIEWS
-- ============================================================
CREATE TABLE IF NOT EXISTS reviews (
    review_id      INT        NOT NULL AUTO_INCREMENT,
    user_id        INT        NOT NULL,
    destination_id INT,
    hotel_id       INT,
    rating         DOUBLE     NOT NULL,
    title          VARCHAR(150),
    content        TEXT       NOT NULL,
    review_type    ENUM('Destination','Hotel') NOT NULL,
    helpful_count  INT        DEFAULT 0,
    created_at     TIMESTAMP  DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (review_id),
    INDEX idx_destination (destination_id),
    INDEX idx_hotel       (hotel_id),
    INDEX idx_user        (user_id),
    INDEX idx_rating      (rating),
    FULLTEXT KEY ft_review (title, content),
    CONSTRAINT fk_review_user        FOREIGN KEY (user_id)        REFERENCES users(user_id)             ON DELETE CASCADE,
    CONSTRAINT fk_review_destination FOREIGN KEY (destination_id) REFERENCES destinations(destination_id) ON DELETE CASCADE,
    CONSTRAINT fk_review_hotel       FOREIGN KEY (hotel_id)       REFERENCES hotels(hotel_id)           ON DELETE CASCADE,
    CONSTRAINT chk_rating CHECK (rating >= 1 AND rating <= 5)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- COST BREAKDOWN
-- ============================================================
CREATE TABLE IF NOT EXISTS cost_breakdown (
    cost_id      INT            NOT NULL AUTO_INCREMENT,
    itinerary_id INT            NOT NULL,
    category     ENUM('Travel','Accommodation','Food','Activities','Other') NOT NULL,
    description  VARCHAR(255),
    amount       DECIMAL(10,2)  NOT NULL,
    currency     VARCHAR(10)    DEFAULT 'USD',
    created_at   TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (cost_id),
    INDEX idx_itinerary (itinerary_id),
    INDEX idx_category  (category),
    CONSTRAINT fk_cost_itinerary
        FOREIGN KEY (itinerary_id) REFERENCES itineraries(itinerary_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- PAYMENT SPLITS
-- ============================================================
CREATE TABLE IF NOT EXISTS payment_splits (
    split_id    INT            NOT NULL AUTO_INCREMENT,
    booking_id  INT            NOT NULL,
    user_id     INT            NOT NULL,
    amount_owed DECIMAL(10,2)  NOT NULL,
    amount_paid DECIMAL(10,2)  DEFAULT 0.00,
    status      ENUM('Pending','Paid') DEFAULT 'Pending',
    created_at  TIMESTAMP      DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (split_id),
    INDEX idx_booking (booking_id),
    INDEX idx_user    (user_id),
    CONSTRAINT fk_split_booking FOREIGN KEY (booking_id) REFERENCES bookings(booking_id) ON DELETE CASCADE,
    CONSTRAINT fk_split_user    FOREIGN KEY (user_id)    REFERENCES users(user_id)       ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- SEARCH HISTORY
-- ============================================================
CREATE TABLE IF NOT EXISTS search_history (
    search_id     INT          NOT NULL AUTO_INCREMENT,
    user_id       INT,
    search_query  VARCHAR(255),
    filters       JSON,
    results_count INT,
    search_date   TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (search_id),
    INDEX idx_user        (user_id),
    INDEX idx_search_date (search_date),
    CONSTRAINT fk_search_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- Admin user is auto-created on first app startup.
-- Default login: username=admin  password=admin
-- Hotels are auto-seeded per destination on first startup.
-- ============================================================
