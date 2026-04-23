# Smart Travel Planner

A comprehensive full-stack travel planning application built with Angular 19 (Frontend), Spring Boot 3.2 (Backend), and MySQL (Database).

## 🎯 Project Overview

Smart Travel Planner is an intelligent travel planning platform that helps users discover destinations, plan trips, book hotels, manage itineraries, and share experiences with friends.

### ✨ Key Features
- **Destination Discovery** - Browse and filter destinations by budget, interests, ratings, and more
- **Smart Trip Planning** - Create custom trips with detailed itineraries
- **Hotel Booking** - Search and filter hotels by price, amenities, and ratings
- **Cost Management** - Calculate trip costs and split payments among participants
- **Safety Information** - Get hospitals, police stations, and travel tips
- **Local Experiences** - Discover traditional cuisine and cultural activities
- **Trip Sharing** - Share trips with friends and collaborate on planning
- **Reviews & Ratings** - Read and write reviews for destinations

---

## 📋 Project Status

| Component | Status | Details |
|-----------|--------|---------|
| **Frontend** | ✅ Complete | Angular 19 with 7 models, 6 services, 9 components |
| **Backend** | ✅ Complete | Spring Boot with 12 entities, 8 repositories, 3 services |
| **Database** | ✅ Complete | MySQL schema with 12 tables + sample data |
| **API** | ✅ Complete | 24+ REST endpoints with full CRUD operations |
| **Documentation** | ✅ Complete | Comprehensive setup guides and API docs |

---

## 🏗️ Technology Stack

### Frontend
- **Framework:** Angular 19
- **Language:** TypeScript
- **Styling:** CSS with responsive design
- **Build:** Angular CLI
- **State Management:** Services with RxJS Observables

### Backend
- **Framework:** Spring Boot 3.2
- **Language:** Java 17
- **Database ORM:** Hibernate JPA
- **Build Tool:** Maven
- **API Documentation:** Swagger/OpenAPI

### Database
- **DBMS:** MySQL 8.0
- **Schema:** 12 normalized tables
- **Relationships:** Proper FK constraints and indexing

---

## 📁 Project Structure

```
Smart-Travel-Planner/
├── Frontend/
│   └── smart-travel-planner/          # Angular 19 Application
│       ├── src/app/
│       │   ├── components/            # 9 UI Components
│       │   ├── services/              # 6 Services
│       │   ├── models/                # 7 Data Models
│       │   └── assets/                # Images & data
│       ├── angular.json
│       ├── package.json
│       └── README.md
│
├── Backend/                           # Spring Boot Application
│   ├── src/main/java/com/smarttravelplanner/
│   │   ├── entity/                    # 12 JPA Entities
│   │   ├── repository/                # 8 Repositories
│   │   ├── service/                   # 3 Services
│   │   ├── controller/                # 3 Controllers
│   │   └── dto/                       # DTOs
│   ├── src/main/resources/
│   │   └── application.properties
│   ├── pom.xml
│   └── README.md
│
├── Database/
│   └── schema.sql                     # MySQL Database Schema
│
├── README.md                          # This file
├── SETUP_SUMMARY.md                   # Initial setup
├── BACKEND_SETUP_COMPLETE.md          # Complete backend docs
└── .gitignore
```

---

## 🚀 Quick Start

### Prerequisites
- **Java:** 17 or higher
- **Node.js:** 18 or higher
- **MySQL:** 8.0 or higher
- **Maven:** 3.6 or higher
- **Git:** Latest version

### Installation Steps

#### 1. Clone Repository
```bash
git clone https://github.com/ashokrxddy1/Smart-Travel-Planner.git
cd Smart-Travel-Planner
```

#### 2. Database Setup
```bash
# Start MySQL
brew services start mysql  # macOS
# or
sudo systemctl start mysql  # Linux

# Create database and tables
mysql -u root -p < Database/schema.sql
```

#### 3. Backend Setup
```bash
cd Backend

# Build project
mvn clean install

# Run application
mvn spring-boot:run
```
Backend runs on: `http://localhost:8080`

#### 4. Frontend Setup
```bash
cd Frontend/smart-travel-planner

# Install dependencies
npm install

# Start development server
ng serve
```
Frontend runs on: `http://localhost:4200`

---

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api
```

### Destination Endpoints
```
GET    /destinations              - Get all destinations
GET    /destinations/{id}         - Get destination by ID
GET    /destinations/budget/{budget}  - Filter by budget
GET    /destinations/price-range  - Filter by price
GET    /destinations/rating/{rating}  - Filter by rating
GET    /destinations/search       - Search destinations
POST   /destinations              - Create destination
PUT    /destinations/{id}         - Update destination
DELETE /destinations/{id}         - Delete destination
```

### Hotel Endpoints
```
GET    /hotels                    - Get all hotels
GET    /hotels/{id}               - Get hotel by ID
GET    /hotels/destination/{id}   - Hotels by destination
GET    /hotels/price-range        - Filter by price
GET    /hotels/rating/{rating}    - Filter by rating
POST   /hotels                    - Create hotel
PUT    /hotels/{id}               - Update hotel
DELETE /hotels/{id}               - Delete hotel
```

### Trip Endpoints
```
GET    /trips                     - Get all trips
GET    /trips/{id}                - Get trip by ID
GET    /trips/user/{userId}       - Get user's trips
POST   /trips                     - Create trip
PUT    /trips/{id}                - Update trip
DELETE /trips/{id}                - Delete trip
GET    /trips/{id}/calculate-cost - Calculate cost
GET    /trips/{id}/split-payment  - Split payment
POST   /trips/{id}/share          - Share trip
```

---

## 💾 Database Schema

### Entity Relationships
```
User (1) ───→ (N) Trip
         ───→ (N) Review

Destination (1) ───→ (N) Hotel
            ───→ (N) Trip
            ───→ (N) SafetyAlert
            ───→ (N) TraditionalFood
            ───→ (N) Review

Trip (1) ───→ (N) ItineraryDay
     ───→ (N) TripHotel
     ───→ (N) TripShare

ItineraryDay (1) ───→ (N) Activity
             ───→ (N) Meal
```

### Database Tables
1. **users** - User accounts and profiles
2. **destinations** - Travel destinations
3. **hotels** - Hotel listings
4. **trips** - User trip bookings
5. **itinerary_days** - Daily itineraries
6. **activities** - Day activities
7. **meals** - Meal planning
8. **reviews** - User reviews
9. **safety_alerts** - Emergency information
10. **traditional_foods** - Local cuisine
11. **trip_hotels** - Trip-Hotel association
12. **trip_shares** - Trip sharing permissions

---

## 🔧 Configuration

### Backend Configuration (`application.properties`)
```properties
# MySQL Database
spring.datasource.url=jdbc:mysql://localhost:3306/smart_travel_planner
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Hibernate JPA
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Server
server.port=8080
server.servlet.context-path=/api

# Logging
logging.level.com.smarttravelplanner=DEBUG
```

### Frontend Configuration
Update API base URL in services:
```typescript
private apiUrl = 'http://localhost:8080/api';
```

---

## 🧪 Testing

### Test Destinations
```bash
# Get all destinations
curl http://localhost:8080/api/destinations

# Filter by budget
curl "http://localhost:8080/api/destinations/budget/low"

# Search
curl "http://localhost:8080/api/destinations/search?query=goa"
```

### Create a Trip
```bash
curl -X POST http://localhost:8080/api/trips \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "title": "Goa Beach Holiday",
    "destinationId": 1,
    "startDate": "2024-12-15",
    "endDate": "2024-12-22",
    "budget": 50000,
    "packageMode": "FAMILY",
    "participants": 4
  }'
```

---

## 📊 Sample Data

### Included Destinations
- **Goa** - Tropical beaches (LOW budget)
- **Manali** - Mountain adventure (MEDIUM budget)
- **Kerala** - Backwaters & nature (MEDIUM budget)
- **Jaipur** - Cultural heritage (LOW budget)
- **Ladakh** - High altitude desert (HIGH budget)

### Included Hotels
- 2 hotels per destination (luxury + budget options)
- Complete amenities and pricing information
- Real booking details

---

## 🔒 Security Considerations

- ✅ SQL injection prevention via parameterized queries
- ✅ CORS configuration for frontend access
- ✅ Input validation on all endpoints
- ✅ Password hashing ready (to implement)
- ✅ JWT token structure in place
- ⏳ Rate limiting (planned)

---

## 🚀 Deployment

### Docker
```bash
# Build Docker image
docker build -t smart-travel-planner-backend .

# Run container
docker run -p 8080:8080 smart-travel-planner-backend
```

### Production Build (Frontend)
```bash
cd Frontend/smart-travel-planner
ng build --configuration production
```

### Cloud Deployment
- AWS EC2 / Elastic Beanstalk
- Google Cloud Platform (GCP)
- Microsoft Azure
- Heroku

---

## 📝 Features Implemented

### Destination Management
- ✅ Browse all destinations
- ✅ Search by location
- ✅ Filter by budget (LOW/MEDIUM/HIGH)
- ✅ Filter by price range
- ✅ Filter by minimum rating
- ✅ View destination details

### Trip Planning
- ✅ Create custom trips
- ✅ Select package mode (Family/Solo/Couple/Adventure)
- ✅ Multiple participants support
- ✅ Flexible dates
- ✅ Budget management
- ✅ Itinerary with daily breakdown
- ✅ Activity scheduling
- ✅ Meal planning

### Hotel Management
- ✅ Browse hotels by destination
- ✅ Price-based filtering
- ✅ Rating-based filtering
- ✅ Amenities information
- ✅ Room type availability
- ✅ Check-in/check-out times

### Trip Management
- ✅ Create trips
- ✅ View user trips
- ✅ Edit trip details
- ✅ Delete trips
- ✅ Share trips with others
- ✅ Calculate total cost
- ✅ Split payments

### Safety & Information
- ✅ Hospital information
- ✅ Police station locations
- ✅ Embassy details
- ✅ Travel tips
- ✅ Emergency contacts

### Local Experiences
- ✅ Traditional food guide
- ✅ Cuisine type filtering
- ✅ Dietary preference support
- ✅ Best places to eat
- ✅ Price information

---

## 🔄 User Workflow

1. **User Discovers** - Browse destinations with filters
2. **User Plans** - Create trip with itinerary
3. **User Books** - Select hotels and dates
4. **User Plans Activities** - Add daily activities and meals
5. **User Shares** - Share trip with friends
6. **User Pays** - Split payments if group trip
7. **User Reviews** - Write reviews after trip

---

## 📚 Documentation Files

- **[BACKEND_SETUP_COMPLETE.md](BACKEND_SETUP_COMPLETE.md)** - Complete backend documentation
- **[Backend/README.md](Backend/README.md)** - Backend-specific guide
- **[Frontend/smart-travel-planner/README.md](Frontend/smart-travel-planner/README.md)** - Frontend guide
- **[SETUP_SUMMARY.md](SETUP_SUMMARY.md)** - Initial project setup

---

## 🤝 Contributing

1. Create feature branch: `git checkout -b feature/name`
2. Commit changes: `git commit -m 'Add feature'`
3. Push to GitHub: `git push origin feature/name`
4. Create Pull Request

---

## 📧 Support & Contact

- **Email:** ashokrxddy1@gmail.com
- **GitHub:** https://github.com/ashokrxddy1
- **Repository:** Smart-Travel-Planner

---

## 📄 License

This project is licensed under the MIT License.

---

## 👥 Team

- **Developer:** Ashok Reddy (ashokrxddy1)
- **Git User:** csshoiab27 (setup phase)
- **Project:** Smart Travel Planner

---

## 🎓 Learning Resources

- [Angular Documentation](https://angular.io)
- [Spring Boot Guide](https://spring.io/projects/spring-boot)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [REST API Best Practices](https://restfulapi.net/)

---

## 📈 Project Timeline

- **Phase 1:** Frontend Development (COMPLETE)
- **Phase 2:** Backend Development (COMPLETE)
- **Phase 3:** Database Design (COMPLETE)
- **Phase 4:** Integration (IN PROGRESS)
- **Phase 5:** Testing (PLANNED)
- **Phase 6:** Deployment (PLANNED)

---

**Last Updated:** April 23, 2026  
**Status:** ✅ Development Phase Complete - Ready for Integration Testing