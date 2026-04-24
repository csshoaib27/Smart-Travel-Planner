# Smart Travel Planner - Getting Started Guide

## 📋 Prerequisites

Before you can run the Smart Travel Planner application, ensure you have the following installed:

### Required Software

1. **MySQL 8.0+** (Database)
   - Download: https://dev.mysql.com/downloads/mysql/
   - Verify: `mysql -u root -p -e "SELECT 1;"`
   - Status: REQUIRED - Must be running before starting the backend

2. **Java 17+** (for Spring Boot Backend)
   - Download: https://www.oracle.com/java/technologies/downloads/
   - Verify: `java -version` (should show 17 or higher)
   - Alternative: OpenJDK 17+ from https://adoptium.net/
   - Status: REQUIRED - Spring Boot 3.1.5 requires Java 17 minimum

3. **Maven 3.8+** (for Building Backend)
   - Download: https://maven.apache.org/download.cgi
   - Verify: `mvn --version` (should show 3.8 or higher)
   - macOS: `brew install maven`
   - Linux: `sudo apt-get install maven`
   - Status: REQUIRED - Used to build and run Spring Boot application

4. **Node.js 18+** (for Angular Frontend)
   - Download: https://nodejs.org/
   - Verify: `node --version` and `npm --version`
   - Status: REQUIRED - Angular 17 requires Node 18+

---

## 🚀 Quick Start (Recommended)

### Option 1: One Command Startup (Mac/Linux)

```bash
cd Smart-Travel-Planner
chmod +x start-all.sh
./start-all.sh
```

The script will:
1. ✅ Check prerequisites (Java, Maven, Node.js, MySQL)
2. ✅ Start Spring Boot backend
3. ✅ Install frontend dependencies and start Angular app
4. ✅ Wait for all services to be healthy

### Option 2: One Command Startup (Windows)

```bash
cd Smart-Travel-Planner
start-all.bat
```

The script will open three new terminal windows:
1. Main window: MySQL setup and monitoring
2. Backend window: Spring Boot running
3. Frontend window: Angular development server

---

## 🌐 Access the Application

Once you see "✅ ALL SERVICES STARTED SUCCESSFULLY!":

| Service | URL | Purpose |
|---------|-----|---------|
| **Frontend** | http://localhost:4200 | Main web application |
| **Backend API** | http://localhost:8080 | REST API endpoints |
| **API Documentation** | http://localhost:8080/swagger-ui.html | Interactive API docs |
| **Database** | localhost:3306 | MySQL database |

---

## 🔐 Default Credentials

```
Username: admin
Password: admin
```

These are for the admin account. Regular users can self-register.

---

## 📝 Manual Setup (If Script Doesn't Work)

### Step 1: Start MySQL Database

Ensure MySQL 8.0+ is running, then import the schema and sample data:

```bash
# Import database schema
mysql -u root -p < database/database_schema.sql

# Import sample data
mysql -u root -p < database/sample_data.sql
```

### Step 2: Start Spring Boot Backend

```bash
# From Smart-Travel-Planner root directory
mvn spring-boot:run

# Backend will start on http://localhost:8080
# Press Ctrl+C to stop
```

### Step 3: Start Angular Frontend

```bash
# In a new terminal, from Smart-Travel-Planner/frontend directory
cd frontend
npm install
npm start

# Frontend will start on http://localhost:4200
# The app will automatically open in your browser
```

---

## ⏹️ Stopping Services

### Using the Startup Script
Press `Ctrl+C` in the terminal running the script (or in each service's terminal window on Windows).

### Manual Cleanup
```bash
# Kill Spring Boot (if still running)
pkill -f "mvn spring-boot:run"

# Kill Angular dev server (if still running)
pkill -f "ng serve"
```

---

## 🆘 Troubleshooting

### Port 3306 Already in Use

**Problem:** Error like "port 3306 is already allocated"

**Solution:**
```bash
# Find and kill process using port 3306
lsof -i :3306
kill -9 <PID>
```

### Java Version Mismatch

**Problem:** Error "class file has wrong version"

**Solution:**
- Verify Java 17+: `java -version`
- Download Java 17 from Oracle or Adoptium
- Update JAVA_HOME environment variable

```bash
# macOS with Homebrew
brew install java

# Linux
sudo apt-get install openjdk-17-jdk

# Windows
# Download from https://www.oracle.com/java/technologies/downloads/
```

### Maven Not Found

**Problem:** Error "mvn: command not found"

**Solution:**
```bash
# macOS
brew install maven

# Linux
sudo apt-get install maven

# Windows
# Download from https://maven.apache.org/download.cgi
# Add to PATH environment variable
```

### Node Modules Issues

**Problem:** Error during `npm install`

**Solution:**
```bash
cd frontend
rm -rf node_modules package-lock.json
npm cache clean --force
npm install
```

### Backend Not Responding

**Problem:** http://localhost:8080 returns connection error

**Solution:**
```bash
# Check if backend is running
lsof -i :8080

# Check logs - the terminal should show errors
# Common issues:
# - MySQL not running
# - Port 8080 already in use
# - Java version incorrect
```

### Frontend Not Loading

**Problem:** http://localhost:4200 is blank or shows errors

**Solution:**
```bash
# Ensure you're in the frontend directory
cd frontend

# Clear Angular cache
npm cache clean --force
rm -rf .angular

# Reinstall and start fresh
npm install
npm start
```

---

## 📚 Project Structure

```
Smart-Travel-Planner/
├── src/main/java/com/smarttravel/
│   ├── SmartTravelApp.java              # Main Spring Boot application
│   ├── config/                           # Spring Security & CORS config
│   ├── controller/                       # REST API endpoints
│   ├── service/                          # Business logic
│   ├── repository/                       # Data access layer
│   ├── model/                            # JPA entities
│   ├── dto/                              # Data transfer objects
│   └── util/                             # Utility classes
│
├── src/main/resources/
│   └── application.properties            # Spring Boot configuration
│
├── frontend/
│   ├── src/app/
│   │   ├── api.service.ts               # HTTP client for backend
│   │   ├── auth.service.ts              # Authentication management
│   │   ├── app.module.ts                # Angular module config
│   │   └── components/                  # UI components
│   ├── package.json                     # npm dependencies
│   └── angular.json                     # Angular CLI config
│
├── database/
│   ├── database_schema.sql              # Table definitions
│   └── sample_data.sql                  # Initial data (destinations, hotels, etc)
│
├── pom.xml                              # Maven project config
├── package.json                         # Root npm config (legacy)
├── start-all.sh                         # Mac/Linux startup script
└── start-all.bat                        # Windows startup script
```

---

## 🧪 Testing the Application

### Test User Registration

1. Go to http://localhost:4200
2. Click "Register" or similar link
3. Fill in user details and register
4. Verify you're redirected to login
5. Login with your new credentials
6. Confirm user is marked as non-admin (is_admin=FALSE in database)

### Test Admin Features

1. Login with credentials:
   ```
   Username: admin
   Password: admin
   ```
2. Navigate to Admin Dashboard (if available in UI)
3. Should see:
   - All users registered in the system
   - All bookings from all users
   - System statistics
   - Dashboard summary

### Test Destination Search

1. Search for destinations by:
   - Budget range
   - Temperature preference
   - Travel type (adventure, family, etc)
2. View destination details:
   - Hotels and pricing
   - Reviews and ratings
   - Weather information
3. Book a trip

### Test Cost Calculator

1. Select a destination and hotel
2. Adjust travel dates
3. See cost breakdown:
   - Travel (transportation)
   - Accommodation (hotel)
   - Food (estimated)
   - Activities (estimated)
   - Miscellaneous
4. Total cost should update dynamically

---

## 🔄 API Endpoints

Key endpoints for testing:

```
# Authentication
POST   /api/auth/register      - Register new user
POST   /api/auth/login         - Login user (returns JWT token)

# Destinations
GET    /api/destinations       - List all destinations
GET    /api/destinations/:id   - Get destination details
POST   /api/destinations/search - Search with filters

# Hotels
GET    /api/hotels             - List all hotels
GET    /api/hotels/:id         - Get hotel details

# Bookings
POST   /api/bookings           - Create booking
GET    /api/bookings/:id       - Get booking details
PUT    /api/bookings/:id       - Update booking

# Admin
GET    /api/admin/users        - List all users (ADMIN ONLY)
GET    /api/admin/bookings     - List all bookings (ADMIN ONLY)
GET    /api/admin/stats        - System statistics (ADMIN ONLY)

# Full API docs available at:
http://localhost:8080/swagger-ui.html
```

---

## 📞 Support & Documentation

- **API Documentation:** http://localhost:8080/swagger-ui.html
- **Startup Guide:** COMBINED_START.md
- **Project Summary:** PROJECT_SUMMARY.md
- **Developer Guide:** DEVELOPER_GUIDE.html

---

## ✅ Verification Checklist

After startup, verify:

- [ ] MySQL is running (`mysql -u root -p -e "SELECT 1;"`)
- [ ] Backend is responding (`curl http://localhost:8080/actuator/health`)
- [ ] Frontend is loading (http://localhost:4200 shows login page)
- [ ] Can login with admin/admin
- [ ] Can register new user
- [ ] Database has sample data (destinations, hotels visible)
- [ ] Admin can see all users and bookings
- [ ] Can search destinations and view hotels
- [ ] Cost calculator is working

---

## 🎯 Next Steps

1. Explore the dashboard and destination search
2. Create test bookings
3. Test the admin features
4. Customize destinations, hotels, and pricing in the database
5. Modify the UI components to match your branding

---

**That's it! You're ready to use Smart Travel Planner!** 🎉
