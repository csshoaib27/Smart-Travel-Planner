# Smart Travel Planner

A comprehensive full-stack web application that helps users find, plan, and book travel destinations based on budget, temperature, interests, and other preferences. Features include cost calculators, itinerary generation, hotel management, and admin capabilities.

**Live Demo**: Coming Soon  
**GitHub Repository**: Coming Soon

---

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Installation & Setup](#installation--setup)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Testing](#testing)
- [Deployment](#deployment)
- [Contributing](#contributing)
- [License](#license)

---

## Features

### 1. **Destination Search & Discovery**
- Filter destinations by budget, temperature, travel type, and location
- View detailed information about each destination
- See best time to visit recommendations
- Rate and review destinations
- Top-rated destinations showcase

### 2. **Smart Cost Calculator**
- Calculate comprehensive trip costs (travel, accommodation, food, activities)
- Break down expenses by category
- Adjust costs based on budget category (Budget, Mid-Range, Luxury)
- Calculate per-person costs
- Split costs among group members

### 3. **Hotel Management**
- Browse 10-15 hotels per destination
- Filter hotels by price range and ratings
- View detailed hotel information and amenities
- Real-time availability checking
- Hotel reviews and ratings
- Room booking system

### 4. **Itinerary Generator**
- Create custom itineraries for selected number of days
- Plan activities per day
- Estimate daily budgets
- Select package types (Family, Solo, Couple, Adventure)
- Save and share itineraries
- Track total estimated costs

### 5. **User Authentication & Authorization**
- User registration and login
- JWT-based authentication
- Admin dashboard for managing users and bookings
- Role-based access control (Admin, User)
- User profile management

### 6. **Booking System**
- Book hotels with check-in/check-out dates
- Track booking status (Pending, Confirmed, Cancelled, Completed)
- Split payments among group members
- Booking history and management

### 7. **Review & Rating System**
- Rate destinations and hotels (1-5 stars)
- Write detailed reviews
- View average ratings
- Mark helpful reviews
- See review count for credibility

### 8. **Additional Features**
- Search history tracking
- Payment split options for group travel
- Traditional food recommendations by destination
- Safety information (hospitals, police stations)
- Travel tips based on destination

---

## Tech Stack

### Frontend
- **Framework**: Angular 17
- **UI Framework**: Bootstrap 5, ng-bootstrap
- **Forms**: Reactive Forms
- **HTTP Client**: Angular HttpClient
- **State Management**: BehaviorSubject (RxJS)
- **Notifications**: ngx-toastr
- **Language**: TypeScript

### Backend
- **Framework**: Spring Boot 3.1.5
- **Language**: Java 17
- **ORM**: Spring Data JPA, Hibernate
- **Database**: MySQL 8.0
- **Security**: Spring Security, JWT (JSON Web Tokens)
- **API Documentation**: SpringDoc OpenAPI (Swagger)
- **Build Tool**: Maven 3.8+
- **Validation**: Bean Validation

### Database
- **DBMS**: MySQL 8.0
- **Connection Pool**: HikariCP
- **Migrations**: Flyway (Optional)

### DevOps & Deployment
- **Version Control**: Git
- **CI/CD**: GitHub Actions (Optional)
- **Containerization**: Docker (Optional)
- **Hosting**: AWS/GCP/Azure (Optional)

---

## Project Structure

```
smart-travel-planner/
│
├── backend/                          # Spring Boot Backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/smarttravel/
│   │   │   │   ├── SmartTravelApp.java          # Main Application
│   │   │   │   ├── config/                      # Security & CORS Config
│   │   │   │   ├── controller/                  # REST API Controllers
│   │   │   │   ├── model/                       # Entity Classes
│   │   │   │   ├── service/                     # Business Logic
│   │   │   │   ├── repository/                  # Data Access Layer
│   │   │   │   ├── util/                        # JWT & Utilities
│   │   │   │   └── exception/                   # Exception Handlers
│   │   │   └── resources/
│   │   │       ├── application.properties       # Configuration
│   │   │       └── schema.sql                   # Database Schema
│   │   └── test/
│   ├── pom.xml                                  # Maven Dependencies
│   └── README.md
│
├── frontend/                         # Angular Frontend
│   ├── src/
│   │   ├── app/
│   │   │   ├── auth/                           # Login & Register Components
│   │   │   ├── destinations/                   # Destination Search & Detail
│   │   │   ├── hotels/                         # Hotel Listings & Details
│   │   │   ├── calculator/                     # Cost Calculator
│   │   │   ├── itinerary/                      # Itinerary Generator
│   │   │   ├── admin/                          # Admin Dashboard
│   │   │   ├── shared/                         # Shared Components & Interceptors
│   │   │   ├── services/                       # API & Auth Services
│   │   │   ├── app.module.ts                   # Root Module
│   │   │   └── app-routing.module.ts           # Routing
│   │   ├── assets/                             # Images & Static Files
│   │   ├── styles.css                          # Global Styles
│   │   └── index.html
│   ├── package.json
│   ├── angular.json
│   ├── tsconfig.json
│   └── README.md
│
├── database/
│   ├── database_schema.sql                     # Complete Database Schema
│   ├── sample_data.sql                         # Sample Data
│   └── README.md
│
├── docs/
│   ├── API_DOCUMENTATION.html                  # API Reference
│   ├── DEVELOPER_GUIDE.html                    # Developer Guide
│   └── DEPLOYMENT.md                           # Deployment Instructions
│
└── README.md (this file)
```

---

## Installation & Setup

### Prerequisites
- Java 17 or higher
- Node.js 18+ and npm 9+
- MySQL 8.0+
- Git
- Angular CLI 17+

### Step 1: Clone the Repository
```bash
git clone https://github.com/yourusername/smart-travel-planner.git
cd smart-travel-planner
```

### Step 2: Set Up Database

1. **Create MySQL Database**:
```bash
mysql -u root -p < database/database_schema.sql
mysql -u root -p < database/sample_data.sql
```

2. **Verify Connection**:
```bash
mysql -u root -p smart_travel_db -e "SELECT COUNT(*) FROM users;"
```

### Step 3: Set Up Backend

1. **Navigate to backend directory**:
```bash
cd backend
```

2. **Update application.properties**:
```properties
# Edit src/main/resources/application.properties
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
```

3. **Build the project**:
```bash
mvn clean install
```

4. **Run Spring Boot application**:
```bash
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

### Step 4: Set Up Frontend

1. **Navigate to frontend directory**:
```bash
cd ../frontend
```

2. **Install dependencies**:
```bash
npm install
```

3. **Start Angular development server**:
```bash
npm start
```

The frontend will start on `http://localhost:4200`

---

## Running the Application

### Development Mode

**Terminal 1 - Database**:
```bash
# Ensure MySQL is running
mysql -u root -p
```

**Terminal 2 - Backend**:
```bash
cd backend
mvn spring-boot:run
```

**Terminal 3 - Frontend**:
```bash
cd frontend
npm start
```

**Access the application**:
- Frontend: http://localhost:4200
- Backend API: http://localhost:8080/api
- API Docs: http://localhost:8080/swagger-ui.html

### Default Credentials

- **Admin**:
  - Username: `admin`
  - Password: `admin`

- **Test User** (create via registration):
  - Can book hotels, create itineraries, review destinations

---

## API Documentation

### Base URL
```
http://localhost:8080/api
```

### Authentication
All protected endpoints require JWT token in header:
```
Authorization: Bearer <your_jwt_token>
```

### Key Endpoints

#### Authentication
- `POST /auth/register` - Register new user
- `POST /auth/login` - User login
- `GET /auth/validate-token` - Validate JWT token

#### Destinations
- `GET /destinations` - Get all destinations
- `GET /destinations/{id}` - Get destination by ID
- `GET /destinations/search?country=&travelType=&budgetCategory=` - Search destinations
- `GET /destinations/top-rated` - Get top-rated destinations
- `POST /destinations` - Create destination (Admin only)

#### Hotels
- `GET /hotels` - Get all hotels
- `GET /hotels/{id}` - Get hotel by ID
- `GET /hotels/destination/{destinationId}` - Get hotels in destination
- `GET /hotels/search?destinationId=&minPrice=&maxPrice=` - Search hotels
- `POST /hotels` - Create hotel (Admin only)

#### Cost Calculator
- `POST /calculator/calculate` - Calculate trip cost
- `POST /calculator/calculate-per-person` - Calculate per-person cost

#### Itineraries
- `POST /itineraries` - Create itinerary
- `GET /itineraries/{id}` - Get itinerary details
- `GET /itineraries/user/{userId}` - Get user's itineraries
- `PUT /itineraries/{id}` - Update itinerary
- `DELETE /itineraries/{id}` - Delete itinerary

#### Bookings
- `POST /bookings` - Create booking
- `GET /bookings/{id}` - Get booking details
- `GET /bookings/user/{userId}` - Get user's bookings
- `PUT /bookings/{id}` - Update booking
- `PUT /bookings/{id}/cancel` - Cancel booking

#### Reviews
- `POST /reviews` - Create review
- `GET /reviews/destination/{destinationId}` - Get destination reviews
- `GET /reviews/hotel/{hotelId}` - Get hotel reviews
- `PUT /reviews/{id}` - Update review
- `DELETE /reviews/{id}` - Delete review

#### Admin
- `GET /admin/users` - Get all users (Admin only)
- `GET /admin/users/{id}` - Get user by ID (Admin only)
- `GET /admin/bookings` - Get all bookings (Admin only)
- `GET /admin/stats` - Get system statistics (Admin only)

### Example Requests

**Login Request**:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin"}'
```

**Search Destinations**:
```bash
curl -X GET "http://localhost:8080/api/destinations/search?country=France&budgetCategory=Luxury" \
  -H "Authorization: Bearer <token>"
```

**Calculate Trip Cost**:
```bash
curl -X POST http://localhost:8080/api/calculator/calculate \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "numberOfDays": 5,
    "numberOfPeople": 2,
    "numberOfRooms": 1,
    "destinationId": 1,
    "budgetCategory": "Mid-Range"
  }'
```

---

## Database Schema

The database includes the following main tables:

1. **users** - User accounts and authentication
2. **destinations** - Travel destinations with details
3. **hotels** - Hotel accommodations with pricing
4. **bookings** - Hotel reservations
5. **itineraries** - User-created trip plans
6. **itinerary_days** - Daily breakdown of itineraries
7. **reviews** - User ratings and reviews
8. **search_history** - Track user searches
9. **payment_splits** - Group payment tracking
10. **cost_breakdown** - Detailed cost tracking

See `database/database_schema.sql` for complete schema details.

---

## Testing

### Backend Tests
```bash
cd backend
mvn test
```

### Frontend Tests
```bash
cd frontend
npm test
```

### Manual Testing Checklist
- [ ] User registration and login
- [ ] Destination search with filters
- [ ] Hotel filtering by price and rating
- [ ] Cost calculator for multiple budgets
- [ ] Itinerary creation and management
- [ ] Booking system with date validation
- [ ] Review and rating system
- [ ] Admin dashboard functionality
- [ ] Payment split calculation
- [ ] Search history tracking

---

## Deployment

### Docker Deployment

1. **Build Docker images**:
```bash
# Backend
cd backend
docker build -t smart-travel-backend .

# Frontend
cd ../frontend
docker build -t smart-travel-frontend .
```

2. **Run with Docker Compose**:
```bash
docker-compose up
```

### Cloud Deployment (AWS Example)

1. **Deploy Backend to Elastic Beanstalk**:
```bash
eb init -p java-17 smart-travel-backend
eb create smart-travel-prod
eb deploy
```

2. **Deploy Frontend to S3 + CloudFront**:
```bash
cd frontend
ng build --prod
aws s3 sync dist/smart-travel-planner s3://your-bucket-name
```

3. **Set up RDS MySQL instance** and update connection strings

---

## Configuration

### Backend Configuration (application.properties)
```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/smart_travel_db
spring.datasource.username=root
spring.datasource.password=your_password

# JWT
jwt.secret=your-secret-key
jwt.expiration=86400000

# CORS
cors.allowed-origins=http://localhost:4200
```

### Frontend Configuration (environment.ts)
```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api'
};
```

---

## Troubleshooting

### Common Issues

**Port Already in Use**:
```bash
# Find and kill process on port 8080 (Backend)
lsof -ti:8080 | xargs kill -9

# Find and kill process on port 4200 (Frontend)
lsof -ti:4200 | xargs kill -9
```

**MySQL Connection Failed**:
- Verify MySQL is running: `mysql -u root -p`
- Check credentials in application.properties
- Ensure database exists: `mysql -u root -p -e "SHOW DATABASES;"`

**CORS Errors**:
- Verify CORS configuration in Spring Boot
- Check allowed origins in application.properties
- Ensure frontend URL is whitelisted

**JWT Token Expired**:
- Re-login to get new token
- Check token expiration in application.properties

---

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## Support & Contact

For issues, feature requests, or questions:
- **Email**: support@smarttravel.com
- **GitHub Issues**: [Open an Issue](https://github.com/yourusername/smart-travel-planner/issues)
- **Discord Community**: [Join Our Server](https://discord.gg/smarttravel)

---

## Acknowledgments

- Bootstrap 5 for responsive UI
- Spring Boot for robust backend framework
- Angular for modern frontend framework
- MySQL for reliable database
- All contributors and users

---

**Happy Travels! 🌍✈️🏖️**

Last Updated: April 2026
