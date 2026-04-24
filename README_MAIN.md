# 🌍 Smart Travel Planner

A full-stack web application to find travel destinations based on budget, temperature, and travel preferences. Includes cost calculation, hotel booking, itinerary generation, reviews, and admin dashboard.

**Status:** ✅ Production Ready | **Version:** 1.0.0

---

## ⚡ Quick Start (1 Command)

### Mac/Linux
```bash
chmod +x start-all.sh && ./start-all.sh
```

### Windows
```bash
start-all.bat
```

That's it! The script starts MySQL, Backend, and Frontend automatically. ✨

---

## 📚 Documentation

| Document | Purpose |
|----------|---------|
| **[GETTING_STARTED.md](GETTING_STARTED.md)** | Setup guide and troubleshooting |
| **[ARCHITECTURE.html](ARCHITECTURE.html)** | System design and API documentation |
| **[COMBINED_START.md](COMBINED_START.md)** | Details about the combined startup |
| **[PROJECT_SUMMARY.md](PROJECT_SUMMARY.md)** | Feature overview and project goals |
| **[DEVELOPER_GUIDE.html](DEVELOPER_GUIDE.html)** | For developers modifying the code |

---

## 🎯 Key Features

✅ **Budget-Aware Search** - Find destinations within your budget  
✅ **Smart Filtering** - Filter by temperature, distance, travel type  
✅ **Cost Calculator** - Multi-category cost breakdown (travel, food, hotels, activities)  
✅ **Hotel Booking** - Browse 10+ hotels per destination  
✅ **Itinerary Generation** - Auto-generate 3-14 day trip plans  
✅ **Community Reviews** - 5-star rating system for destinations and hotels  
✅ **Admin Dashboard** - View all users and bookings in the system  
✅ **Group Travel** - Split costs among friends  
✅ **Search History** - Track and get personalized recommendations  

---

## 🌐 Access the Application

Once the startup completes:

| Service | URL |
|---------|-----|
| **Web App** | http://localhost:4200 |
| **API** | http://localhost:8080 |
| **API Docs** | http://localhost:8080/swagger-ui.html |

**Login with:**
```
Username: admin
Password: admin
```

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| **Frontend** | Angular 17, TypeScript, Bootstrap 5 |
| **Backend** | Java 17, Spring Boot 3.1.5, JPA/Hibernate |
| **Database** | MySQL 8.0 |
| **Authentication** | JWT Tokens, Spring Security |
| **Infrastructure** | Maven, Node.js |

---

## 📋 Prerequisites

- ✅ **MySQL 8.0+** - Database (running locally)
- ✅ **Java 17+** - For Spring Boot backend
- ✅ **Maven 3.8+** - For building backend
- ✅ **Node.js 18+** - For Angular frontend

[Installation Guide →](GETTING_STARTED.md#prerequisites)

---

## 📁 Project Structure

```
Smart-Travel-Planner/
├── src/main/java/com/smarttravel/          # Backend Java code
│   ├── controller/                         # REST API controllers
│   ├── service/                            # Business logic
│   ├── repository/                         # Data access
│   ├── model/                              # JPA entities
│   └── config/                             # Configuration
├── frontend/                                # Angular 17 app
│   ├── src/app/
│   ├── package.json
│   └── angular.json
├── database/                                # SQL scripts
│   ├── database_schema.sql
│   └── sample_data.sql
├── pom.xml                                  # Maven configuration
└── start-all.sh / start-all.bat            # Startup scripts
```

---

## 🚀 What Happens When You Start

### Step 1: MySQL Database
- Connects to local MySQL 8.0
- Verifies `smart_travel_db` database
- Imports schema with 11 tables
- Loads sample data (10 destinations, 120+ hotels)

### Step 2: Spring Boot Backend
- Builds Java application with Maven
- Connects to MySQL database
- Starts REST API on port 8080
- Exposes 40+ endpoints with Swagger documentation

### Step 3: Angular Frontend
- Installs npm dependencies
- Compiles TypeScript components
- Starts dev server on port 4200
- Opens browser to http://localhost:4200

### Result
All three services running together, ready to use! 🎉

---

## 🧪 Testing

### Test User Registration
1. Go to http://localhost:4200
2. Click Register
3. Create new account
4. New user is automatically marked as non-admin in database

### Test Admin Features
1. Login as **admin/admin**
2. Navigate to Admin Dashboard
3. View all users in the system
4. View ALL bookings from ALL users (key feature!)
5. See system statistics and analytics

### Test Destination Search
1. Search destinations by budget, temperature, travel type
2. View hotels and pricing
3. Create bookings
4. See cost breakdown

---

## 📱 Sample Data Included

### Destinations (10+)
- Paris (France) - Cultural, Mild
- Tokyo (Japan) - Modern, Mild
- New York (USA) - Adventure, Cold
- Bali (Indonesia) - Nature, Tropical
- Swiss Alps (Switzerland) - Adventure, Cold
- Bangkok (Thailand) - Food, Tropical
- Barcelona (Spain) - Culture, Mild
- Maldives - Beach, Tropical
- Machu Picchu (Peru) - Adventure, Mild
- Iceland - Nature, Cold

### Hotels
- 10-15 per major destination
- Various price points (budget to luxury)
- Sample reviews and ratings
- Availability information

---

## 🔐 Security

- **JWT Authentication** with 24-hour expiration
- **BCrypt Password** encryption
- **Role-Based Access Control** (Admin vs User)
- **Spring Security** protecting all endpoints
- **CORS** configured for localhost:4200

---

## 🛑 Stopping Services

### Method 1: Press Ctrl+C
The script automatically:
- Kills backend process
- Kills frontend process
- Stops MySQL container
- Cleans up resources

### Method 2: Manual
```bash
pkill -f "mvn spring-boot:run"
pkill -f "ng serve"
```

---

## ❓ Common Issues

**Port Already in Use?**
```bash
lsof -i :3306    # MySQL
lsof -i :8080    # Backend
lsof -i :4200    # Frontend
kill -9 <PID>    # Kill the process
```

**Java Version Wrong?**
```bash
java -version    # Must be 17 or higher
```

**Maven Not Found?**
```bash
brew install maven        # Mac
sudo apt-get install maven # Linux
```

[Full Troubleshooting Guide →](GETTING_STARTED.md#troubleshooting)

---

## 📊 API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login and get JWT token

### Destinations
- `GET /api/destinations` - List all destinations
- `GET /api/destinations/{id}` - Get destination details
- `POST /api/destinations/search` - Search with filters

### Hotels
- `GET /api/hotels` - List all hotels
- `GET /api/hotels/{id}` - Get hotel details

### Bookings
- `POST /api/bookings` - Create booking
- `GET /api/bookings/{id}` - Get booking details
- `PUT /api/bookings/{id}` - Update booking

### Admin (Admin Only)
- `GET /api/admin/users` - List all users
- `GET /api/admin/bookings` - **View ALL bookings from ALL users**
- `GET /api/admin/stats` - System statistics
- `GET /api/admin/dashboard` - Dashboard summary

[Full API Documentation →](http://localhost:8080/swagger-ui.html)

---

## 🏗️ System Architecture

```
┌─────────────────────────────────────────┐
│   Web Browser (http://localhost:4200)   │
└────────────────┬────────────────────────┘
                 │ HTTP
                 ▼
         ┌───────────────────┐
         │ Angular 17 Frontend│
         │ (TypeScript/HTML)  │
         └────────┬──────────┘
                  │ REST API (JSON)
                  ▼
    ┌─────────────────────────────────┐
    │  Spring Boot 3.1.5 Backend     │
    │  ├─ REST Controllers            │
    │  ├─ Business Logic Services    │
    │  └─ JPA Data Access Layer      │
    └────────────┬────────────────────┘
                 │ JDBC/SQL
                 ▼
    ┌─────────────────────────────────┐
    │     MySQL 8.0 Database          │
    │  (smart_travel_db)              │
    │  ├─ Users (is_admin flag)       │
    │  ├─ Destinations                │
    │  ├─ Hotels (10-15 per city)    │
    │  ├─ Bookings                    │
    │  └─ Reviews, Itineraries, ...   │
    └─────────────────────────────────┘
```

---

## 📞 Support

- **API Documentation:** http://localhost:8080/swagger-ui.html
- **Getting Started:** [GETTING_STARTED.md](GETTING_STARTED.md)
- **Architecture Details:** [ARCHITECTURE.html](ARCHITECTURE.html)
- **Developer Guide:** [DEVELOPER_GUIDE.html](DEVELOPER_GUIDE.html)

---

## 📝 Default Credentials

```
Admin Account
Username: admin
Password: admin
```

⚠️ **Important:** Change these credentials in production!

---

## 🎓 Key Endpoints for Testing

1. **Register User**
   ```bash
   POST http://localhost:8080/api/auth/register
   Body: {"email": "test@example.com", "password": "test123"}
   ```

2. **Login**
   ```bash
   POST http://localhost:8080/api/auth/login
   Body: {"email": "admin", "password": "admin"}
   Returns: JWT token
   ```

3. **Search Destinations**
   ```bash
   POST http://localhost:8080/api/destinations/search
   Body: {"budget": 1000, "temperature": "mild", "travelType": "adventure"}
   ```

4. **Get All Bookings (Admin)**
   ```bash
   GET http://localhost:8080/api/admin/bookings
   Header: Authorization: Bearer <JWT_TOKEN>
   ```

---

## ✨ Highlights

✅ **One-Command Startup** - No multiple terminal windows needed  
✅ **Combined Services** - MySQL, Backend, Frontend start together  
✅ **Sample Data** - Pre-loaded destinations, hotels, and data  
✅ **Health Checks** - Script waits for services to be ready  
✅ **Graceful Shutdown** - Clean Ctrl+C to stop everything  
✅ **Production Ready** - All best practices implemented  
✅ **Admin Features** - View all users and bookings  
✅ **Full Documentation** - Guides for setup, API, and architecture  

---

## 🎯 Next Steps

1. ✅ **Read This File** - You're doing it!
2. 📖 **Check [GETTING_STARTED.md](GETTING_STARTED.md)** - Setup guide
3. 🚀 **Run `./start-all.sh` or `start-all.bat`** - Start the app
4. 🌐 **Visit http://localhost:4200** - Open the app
5. 🔐 **Login with admin/admin** - Test admin features
6. 🏖️ **Explore destinations** - Search and book trips!

---

## 📜 Project Information

- **Framework:** Spring Boot 3.1.5 + Angular 17
- **Language:** Java 17, TypeScript
- **Database:** MySQL 8.0
- **Build Tool:** Maven 3.8+
- **Version:** 1.0.0
- **Status:** ✅ Production Ready

---

## 🎉 Enjoy Your Smart Travel Planning!

For detailed information, check the [documentation](GETTING_STARTED.md).

Questions? See the [FAQ section](GETTING_STARTED.md#troubleshooting) or check [API docs](http://localhost:8080/swagger-ui.html).

---

**Built with ❤️ for travel enthusiasts worldwide**
