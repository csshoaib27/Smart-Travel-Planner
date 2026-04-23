# Smart Travel Planner Backend

## Overview
Spring Boot + MySQL REST API backend for the Smart Travel Planner application.

## Technology Stack
- **Framework:** Spring Boot 3.2.0
- **Language:** Java 17
- **Build Tool:** Maven
- **Database:** MySQL 8.0
- **ORM:** Hibernate JPA
- **Authentication:** JWT
- **API Documentation:** Swagger/OpenAPI

## Project Structure
```
Backend/
├── src/main/java/com/smarttravelplanner/
│   ├── entity/              # JPA Entity classes
│   ├── repository/          # Spring Data JPA Repositories
│   ├── service/             # Business logic services
│   ├── controller/          # REST API endpoints
│   ├── dto/                 # Data Transfer Objects
│   └── SmartTravelPlannerApplication.java
├── src/main/resources/
│   └── application.properties
├── pom.xml                  # Maven dependencies
└── README.md
```

## Prerequisites
- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher
- Git

## Setup Instructions

### 1. Database Setup
```bash
# Start MySQL server
# macOS with Homebrew
brew services start mysql

# Linux
sudo systemctl start mysql

# Windows
net start MySQL80
```

### 2. Create Database
```bash
# Open MySQL client
mysql -u root -p

# Run schema.sql
source /path/to/Database/schema.sql
```

### 3. Configure Application
Edit `Backend/src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/smart_travel_planner
spring.datasource.username=root
spring.datasource.password=your_mysql_password
```

### 4. Build and Run
```bash
# Navigate to Backend directory
cd Backend

# Build with Maven
mvn clean install

# Run the application
mvn spring-boot:run

# Or run the JAR
java -jar target/smart-travel-planner-backend-1.0.0.jar
```

Server will start on: `http://localhost:8080`

## API Endpoints

### Destinations
- `GET /api/destinations` - Get all destinations
- `GET /api/destinations/{id}` - Get destination by ID
- `GET /api/destinations/budget/{budget}` - Filter by budget (LOW, MEDIUM, HIGH)
- `GET /api/destinations/price-range?minCost=1000&maxCost=3000` - Filter by price range
- `GET /api/destinations/rating/{rating}` - Filter by minimum rating
- `GET /api/destinations/search?query=goa` - Search destinations
- `POST /api/destinations` - Create new destination
- `PUT /api/destinations/{id}` - Update destination
- `DELETE /api/destinations/{id}` - Delete destination

### Hotels
- `GET /api/hotels` - Get all hotels
- `GET /api/hotels/{id}` - Get hotel by ID
- `GET /api/hotels/destination/{destinationId}` - Get hotels by destination
- `GET /api/hotels/price-range?minPrice=1000&maxPrice=10000` - Filter by price
- `GET /api/hotels/rating/{rating}` - Filter by rating
- `GET /api/hotels/destination/{destinationId}/filter?minPrice=1000&maxPrice=10000` - Combined filter
- `POST /api/hotels` - Create hotel
- `PUT /api/hotels/{id}` - Update hotel
- `DELETE /api/hotels/{id}` - Delete hotel

### Trips
- `GET /api/trips` - Get all trips
- `GET /api/trips/{id}` - Get trip by ID
- `GET /api/trips/user/{userId}` - Get user's trips
- `POST /api/trips` - Create trip
- `PUT /api/trips/{id}` - Update trip
- `DELETE /api/trips/{id}` - Delete trip
- `GET /api/trips/{id}/calculate-cost` - Calculate total trip cost
- `GET /api/trips/{id}/split-payment` - Split payment among participants
- `POST /api/trips/{id}/share` - Share trip with others

## Database Schema

### Tables
1. **users** - User accounts
2. **destinations** - Travel destinations
3. **hotels** - Hotel listings
4. **trips** - User trips/bookings
5. **itinerary_days** - Daily itinerary
6. **activities** - Activities for each day
7. **meals** - Meal planning
8. **reviews** - User reviews
9. **safety_alerts** - Safety information
10. **traditional_foods** - Local cuisine
11. **trip_hotels** - Trip-Hotel association
12. **trip_shares** - Trip sharing information

## Entity Relationships
```
User (1) -----> (N) Trip
         -----> (N) Review

Destination (1) -----> (N) Hotel
            -----> (N) Trip
            -----> (N) SafetyAlert
            -----> (N) TraditionalFood
            -----> (N) Review

Trip (1) -----> (N) ItineraryDay
     -----> (N) TripHotel
     -----> (N) TripShare

ItineraryDay (1) -----> (N) Activity
             -----> (N) Meal
```

## Features Implemented

### Core Features
✅ User management (basic)
✅ Destination browsing with filters (budget, price, rating)
✅ Hotel search and filtering
✅ Trip creation and management
✅ Itinerary planning
✅ Cost calculation and payment splitting
✅ Trip sharing
✅ Reviews and ratings
✅ Safety alerts and emergency information
✅ Traditional food guide

### Filters Available
- Budget (LOW, MEDIUM, HIGH)
- Price range
- Minimum rating
- Location (city, country)
- Cuisine type
- Dietary options

## Testing the API

### Using cURL
```bash
# Get all destinations
curl http://localhost:8080/api/destinations

# Get hotels by price range
curl "http://localhost:8080/api/hotels/price-range?minPrice=1000&maxPrice=10000"

# Create a trip
curl -X POST http://localhost:8080/api/trips \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "title": "Goa Beach Holiday",
    "destinationId": 1,
    "startDate": "2024-12-15",
    "endDate": "2024-12-22",
    "budget": 50000,
    "packageMode": "FAMILY"
  }'
```

### Using Postman
1. Import the API endpoints
2. Set base URL: `http://localhost:8080`
3. Configure headers: `Content-Type: application/json`
4. Test each endpoint

## Environment Variables
Create `.env` file in Backend directory:
```
MYSQL_URL=jdbc:mysql://localhost:3306/smart_travel_planner
MYSQL_USER=root
MYSQL_PASSWORD=root
JWT_SECRET=your-secret-key-here
```

## Common Issues

### MySQL Connection Error
- Ensure MySQL is running
- Check credentials in application.properties
- Verify database exists

### Port Already in Use
```bash
# Kill process on port 8080
macOS: lsof -ti:8080 | xargs kill -9
Linux: fuser -k 8080/tcp
Windows: netstat -ano | findstr :8080 | findstr LISTENING
```

### Build Fails
```bash
# Clean and rebuild
mvn clean install -DskipTests

# Check Java version
java -version
```

## Performance Optimization
- Implemented proper indexing on frequently queried fields
- Added query optimization in repositories
- Used transactional boundaries for data consistency
- Configured connection pooling

## Security Considerations
- Input validation on all endpoints
- SQL injection prevention through parameterized queries
- CORS configuration for frontend
- JWT token support for authentication
- Password hashing (to be implemented)

## Future Enhancements
- [ ] User authentication with JWT
- [ ] Payment gateway integration
- [ ] Real-time notifications
- [ ] Advanced analytics
- [ ] Mobile app support
- [ ] Caching layer (Redis)
- [ ] API rate limiting
- [ ] Unit tests
- [ ] Integration tests

## Deployment

### Docker
```dockerfile
FROM openjdk:17
COPY target/smart-travel-planner-backend-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

## Documentation
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- API Docs: `http://localhost:8080/v3/api-docs`

## Contributing
1. Create a feature branch
2. Commit changes
3. Push to GitHub
4. Create pull request

## License
MIT License

## Support
For issues or questions, contact: ashokrxddy1@gmail.com
