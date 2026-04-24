# Smart Travel Planner - Quick Reference Card

## 🚀 One-Command Startup

### Mac/Linux
```bash
chmod +x start-all.sh && ./start-all.sh
```

### Windows
```bash
start-all.bat
```

---

## 🌐 Access URLs

| Service | URL |
|---------|-----|
| Frontend | http://localhost:4200 |
| API | http://localhost:8080 |
| API Docs | http://localhost:8080/swagger-ui.html |
| Database | localhost:3306 |

---

## 🔐 Login Credentials

```
Username: admin
Password: admin
```

---

## 📋 What Gets Started

1. **MySQL Database** (Port 3306)
   - Database: smart_travel_db
   - User: travel_user / travel_password

2. **Spring Boot Backend** (Port 8080)
   - REST API with 40+ endpoints
   - JWT authentication
   - Swagger documentation

3. **Angular Frontend** (Port 4200)
   - Web application
   - Responsive design
   - Bootstrap UI

---

## 🛑 Stopping Services

**Option 1:** Press `Ctrl+C` in terminal  
**Option 2:** Close all windows (Windows)

---

## 📚 Documentation

| Document | Purpose |
|----------|---------|
| README_MAIN.md | Start here! Overview and quick start |
| GETTING_STARTED.md | Detailed setup and troubleshooting |
| ARCHITECTURE.html | System design and API docs |
| DEPLOYMENT_CHECKLIST.md | Pre/post deployment checklist |

---

## 🔑 Key Endpoints

```bash
# Authentication
POST   /api/auth/register       # Create new user
POST   /api/auth/login          # Login user

# Destinations
GET    /api/destinations        # List all destinations
POST   /api/destinations/search # Search destinations

# Hotels
GET    /api/hotels              # List all hotels

# Bookings
POST   /api/bookings            # Create booking
GET    /api/bookings/{id}       # Get booking

# Admin (Admin Only!)
GET    /api/admin/users         # All users
GET    /api/admin/bookings      # ALL bookings from ALL users
GET    /api/admin/stats         # Statistics
GET    /api/admin/dashboard     # Dashboard
```

---

## ⚙️ Technology Stack

- **Backend:** Java 17, Spring Boot 3.1.5, JPA/Hibernate
- **Frontend:** Angular 17, TypeScript, Bootstrap 5
- **Database:** MySQL 8.0
- **Authentication:** JWT Tokens
- **Build:** Maven, npm/Node.js

---

## 📁 Key Files

- `pom.xml` - Maven configuration
- `src/main/java/com/smarttravel/` - Backend code (20 files)
- `src/main/resources/application.properties` - Configuration
- `frontend/` - Angular application
- `database/database_schema.sql` - Database schema
- `database/sample_data.sql` - Sample data
- `start-all.sh` / `start-all.bat` - Startup scripts

---

## 🆘 Common Issues

### Port Already in Use
```bash
lsof -i :3306   # MySQL
lsof -i :8080   # Backend
lsof -i :4200   # Frontend
kill -9 <PID>   # Kill process
```

### Java Version Wrong
```bash
java -version   # Must show 17+
```

### Maven Not Found
```bash
brew install maven        # Mac
sudo apt-get install maven # Linux
```

### MySQL Not Running
- Mac/Linux: `sudo service mysql start` or `brew services start mysql`
- Windows: Start MySQL from Services or MySQL Workbench

---

## ✅ Verification

After startup, verify:
- [ ] MySQL running: `mysqladmin ping -h 127.0.0.1`
- [ ] Backend responding: `curl http://localhost:8080/actuator/health`
- [ ] Frontend loading: Open http://localhost:4200 in browser
- [ ] Can login with admin/admin
- [ ] Sample data visible (destinations, hotels)

---

## 🧪 Quick Tests

### Test Admin Features
1. Login as admin/admin
2. Go to http://localhost:8080/swagger-ui.html
3. Try GET /api/admin/users
4. Try GET /api/admin/bookings (view ALL bookings!)
5. Try GET /api/admin/stats

### Test User Registration
1. Register new account in web app
2. Login with new credentials
3. Check database: new user has is_admin=FALSE

### Test Destination Search
1. Search by budget (e.g., 1000)
2. Filter by temperature (e.g., "mild")
3. Filter by travel type (e.g., "adventure")
4. View hotels and pricing

---

## 📊 Database Tables

- `users` - User accounts
- `destinations` - Travel destinations (10+)
- `hotels` - Accommodations (120+)
- `bookings` - Trip reservations
- `reviews` - User ratings
- `itineraries` - Trip plans
- `payment_splits` - Group costs
- `cost_breakdown` - Expense categories
- `search_history` - Search tracking

---

## 🔒 Security

- ✅ BCrypt password encryption
- ✅ JWT token authentication (24hr expiration)
- ✅ Role-based access control (Admin/User)
- ✅ Spring Security protecting endpoints
- ✅ CORS configured for localhost:4200

---

## 📞 Help

1. **Getting Started?** → Read README_MAIN.md
2. **Setup Issues?** → Check GETTING_STARTED.md
3. **How does it work?** → Read ARCHITECTURE.html
4. **API Help?** → Visit http://localhost:8080/swagger-ui.html
5. **Code Changes?** → See DEVELOPER_GUIDE.html

---

## 🎯 Features at a Glance

✅ User registration & login  
✅ Destination search & filtering  
✅ Hotel browsing & booking  
✅ Cost calculator (multi-category)  
✅ Itinerary generation  
✅ Community reviews (5-star)  
✅ Admin dashboard  
✅ View all users & bookings  
✅ Group cost splitting  
✅ Search history tracking  

---

## 🏁 Ready?

1. Ensure Java 17+, Maven, Node.js, and MySQL 8.0+ installed and running
2. Run `./start-all.sh` or `start-all.bat`
3. Wait for "✅ ALL SERVICES STARTED SUCCESSFULLY!"
4. Open http://localhost:4200
5. Login with admin/admin
6. Explore and enjoy! 🎉

---

**Questions?** See [GETTING_STARTED.md](GETTING_STARTED.md)  
**Need details?** Check [ARCHITECTURE.html](ARCHITECTURE.html)  
**Ready to code?** Review [DEVELOPER_GUIDE.html](DEVELOPER_GUIDE.html)
