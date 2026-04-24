# Smart Travel Planner - Quick Start Guide

## 🚀 Get Started in 5 Minutes

### Prerequisites
- Java 17+
- Node.js 18+
- MySQL 8.0+
- Git

---

## Step 1: Database Setup (2 minutes)

```bash
# Login to MySQL
mysql -u root -p

# Create and setup database
SOURCE database/database_schema.sql;
SOURCE database/sample_data.sql;

# Verify
SELECT COUNT(*) FROM users;  -- Should return 1 (admin user)
SELECT COUNT(*) FROM destinations;  -- Should return 10
SELECT COUNT(*) FROM hotels;  -- Should return 120+
```

---

## Step 2: Start Backend (1 minute)

```bash
# Navigate to backend
cd backend

# Update database credentials in application.properties if needed
# spring.datasource.username=root
# spring.datasource.password=your_password

# Run Spring Boot application
mvn spring-boot:run

# Verify at http://localhost:8080/swagger-ui.html
```

---

## Step 3: Start Frontend (1 minute)

```bash
# In new terminal, navigate to frontend
cd frontend

# Install dependencies
npm install

# Start Angular dev server
npm start

# Opens automatically at http://localhost:4200
```

---

## Step 4: Login & Test (1 minute)

### Default Credentials:
- **Username**: admin
- **Password**: admin

### Test Features:
1. ✅ Search destinations (home page)
2. ✅ Filter by budget/temperature
3. ✅ View hotels in each destination
4. ✅ Calculate trip cost
5. ✅ Create itinerary
6. ✅ Book hotel
7. ✅ Write review

---

## 📂 Project Structure Overview

```
smart-travel-planner/
├── 📄 README.md                    ← Complete documentation
├── 📄 DEVELOPER_GUIDE.html         ← Detailed technical guide
├── 📄 PROJECT_SUMMARY.md           ← Project overview
├── 📄 QUICK_START.md              ← This file
│
├── database/
│   ├── database_schema.sql        ← Database structure
│   └── sample_data.sql            ← Test data
│
├── backend/
│   ├── pom.xml                    ← Maven dependencies
│   ├── application.properties      ← Config
│   └── src/main/java/...          ← Java source
│       ├── *Controller.java       ← REST endpoints
│       ├── *Service.java          ← Business logic
│       ├── *Repository.java       ← Database access
│       └── model/                 ← Entity classes
│
└── frontend/
    ├── package.json               ← NPM dependencies
    ├── angular.json               ← Build config
    └── src/
        └── app/
            ├── auth/              ← Login/Register
            ├── destinations/      ← Search & browse
            ├── hotels/            ← Listings
            ├── calculator/        ← Cost calculator
            ├── itinerary/         ← Trip planning
            └── services/          ← API calls
```

---

## 🔑 Key API Endpoints

### Authentication
```
POST   /auth/login         - Login
POST   /auth/register      - Register
GET    /auth/validate-token - Check token
```

### Destinations
```
GET    /destinations              - Get all
GET    /destinations/{id}         - Get one
GET    /destinations/search       - Search with filters
GET    /destinations/top-rated    - Top 10 rated
```

### Hotels
```
GET    /hotels                               - Get all
GET    /hotels/{id}                         - Get one
GET    /hotels/destination/{destId}         - By destination
GET    /hotels/search?destId=&minPrice=...  - Search
```

### Cost Calculator
```
POST   /calculator/calculate           - Full trip cost
POST   /calculator/calculate-per-person - Per person
```

### Bookings
```
POST   /bookings                   - Create booking
GET    /bookings/user/{userId}     - User's bookings
PUT    /bookings/{id}/cancel       - Cancel booking
```

### Reviews
```
POST   /reviews                        - Create review
GET    /reviews/destination/{id}       - Destination reviews
GET    /reviews/hotel/{id}             - Hotel reviews
```

---

## 🧪 Test the Application

### Using API (curl examples)

**Login:**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin"}'

# Copy the token from response
```

**Search Destinations:**
```bash
curl -X GET "http://localhost:8080/api/destinations" \
  -H "Authorization: Bearer <token>"
```

**Calculate Cost:**
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

### Using Frontend UI
1. Go to http://localhost:4200
2. Login with admin/admin
3. Explore all features

---

## 🐛 Troubleshooting

### Backend won't start
```bash
# Check if port 8080 is in use
lsof -ti:8080 | xargs kill -9

# Check MySQL connection
mysql -u root -p -e "SELECT 1;"

# Check Java version
java -version  # Should be 17+
```

### Frontend won't start
```bash
# Clear cache and reinstall
rm -rf node_modules package-lock.json
npm install

# Check port 4200
lsof -ti:4200 | xargs kill -9

# Check Node version
node -v  # Should be 18+
```

### Can't connect to database
```bash
# Verify MySQL is running
mysql -u root -p

# Check credentials in application.properties
# Verify database exists
SHOW DATABASES;

# Verify tables
USE smart_travel_db;
SHOW TABLES;
```

### JWT token errors
```bash
# Token expired? Re-login
# Invalid token? Clear localStorage and login again
```

---

## 📚 Documentation Files

| File | Purpose | Read If... |
|------|---------|-----------|
| README.md | Complete guide | You want full documentation |
| DEVELOPER_GUIDE.html | Technical deep-dive | You're developing/extending |
| PROJECT_SUMMARY.md | What was built | You want overview |
| QUICK_START.md | This file | You want quick setup |
| database_schema.sql | DB structure | You need SQL details |

---

## 🔗 Important URLs

| Service | URL |
|---------|-----|
| Frontend | http://localhost:4200 |
| Backend | http://localhost:8080 |
| API Docs (Swagger) | http://localhost:8080/swagger-ui.html |
| Database | localhost:3306 |

---

## 📋 Feature Checklist

After starting the app, verify:

- [ ] Can login with admin/admin
- [ ] Can see 10 destinations
- [ ] Can search destinations
- [ ] Can filter by budget/temperature
- [ ] Can see hotels in each destination
- [ ] Can calculate trip costs
- [ ] Can create itinerary
- [ ] Can make hotel booking
- [ ] Can write reviews
- [ ] Can access admin features (if admin)

---

## 🚨 Important Notes

### Before Production:
1. **Change admin password** in database
2. **Update JWT secret** in application.properties
3. **Set database credentials** properly
4. **Enable HTTPS/SSL**
5. **Configure firewall** rules
6. **Set up logging** 
7. **Enable monitoring**
8. **Backup strategy** in place

### Development Only:
- Default credentials are for testing
- CORS is set to localhost
- Swagger UI is enabled
- Debug logging is on

---

## 📞 Getting Help

1. **Setup Issues?** → Check README.md troubleshooting
2. **API Questions?** → Check DEVELOPER_GUIDE.html API section
3. **Database Questions?** → Check database_schema.sql
4. **Code Questions?** → Check inline comments in source files

---

## ✅ You're Ready!

You should now have:
- ✅ Database running with sample data
- ✅ Backend API on localhost:8080
- ✅ Frontend on localhost:4200
- ✅ All features accessible

**Next Steps:**
- Explore the application
- Review the code structure
- Try creating a new destination/hotel
- Extend with your own features

---

**Happy Coding! 🎉**

For detailed information, see README.md or DEVELOPER_GUIDE.html
