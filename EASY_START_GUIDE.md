# Smart Travel Planner - Easy Start Guide

## 🚀 One-Click Application Startup

### Overview
Use the startup scripts to get everything running quickly. You'll need MySQL, Java, and Node.js installed on your machine.

---

## 📋 Prerequisites

Before starting, ensure you have:

1. **Java 17+** (for Spring Boot Backend)
   - Download: https://adoptium.net/
   - Verify: `java -version`

2. **Maven 3.8+** (for building the backend)
   - Download: https://maven.apache.org/download.cgi
   - Verify: `mvn -version`

3. **Node.js 18+** (for Angular Frontend)
   - Download: https://nodejs.org/
   - Verify: `node -v`

4. **MySQL 8.0+** (running locally)
   - Download: https://dev.mysql.com/downloads/mysql/
   - Verify: `mysql -u root -p -e "SELECT 1;"`

5. **Ports available**:
   - 3306 (MySQL)
   - 8080 (Backend)
   - 4200 (Frontend)

---

## ✨ Quick Start (3 Steps)

### Step 1: Download/Navigate to Project
```bash
cd /path/to/smart-travel-planner
```

### Step 2: Run One-Click Startup

**On Windows:**
- Double-click `start.bat`
- Or run in Command Prompt:
  ```bash
  start.bat
  ```

**On Mac/Linux:**
- Open Terminal in project directory
- Make script executable (first time only):
  ```bash
  chmod +x start.sh
  ```
- Run the script:
  ```bash
  ./start.sh
  ```

### Step 3: Wait for Startup (30-60 seconds)
The script will:
- ✅ Check prerequisites (Java, Maven, Node.js, MySQL)
- ✅ Start Spring Boot backend
- ✅ Install frontend dependencies and start Angular
- ✅ Open browser to http://localhost:4200

---

## 📱 Access the Application

Once started, access:

| Service | URL |
|---------|-----|
| **Frontend** | http://localhost:4200 |
| **Backend API** | http://localhost:8080 |
| **API Documentation** | http://localhost:8080/swagger-ui.html |
| **Database** | localhost:3306 |

---

## 🔐 Default Login Credentials

```
Username: admin
Password: admin
```

---

## 👥 User Registration & Database Storage

### How User Registration Works

1. **User fills registration form** with:
   - Username
   - Email
   - Password
   - Full Name (optional)
   - Phone (optional)

2. **Frontend validates input**:
   - Password minimum 6 characters
   - Email format validation
   - Username availability check

3. **Backend processes registration**:
   - Checks if username exists
   - Checks if email exists
   - Encrypts password with BCrypt
   - Creates user record in database
   - Returns JWT token

4. **User saved in database** with:
   - `users` table entry
   - User role set to "USER" (not admin)
   - Encrypted password
   - Account creation timestamp

### Example API Call

```bash
POST /api/auth/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "password123",
  "fullName": "John Doe",
  "phone": "+1234567890"
}

Response:
{
  "success": true,
  "message": "User registered successfully",
  "data": {
    "userId": 2,
    "username": "john_doe",
    "email": "john@example.com",
    "fullName": "John Doe",
    "token": "eyJhbGc...",
    "isAdmin": false
  }
}
```

---

## 👨‍💼 Admin User Features

### Admin Capabilities

Logged-in admins can:

1. **View All Users** (`GET /admin/users`)
   - See all registered users
   - User details, email, phone
   - Booking and itinerary counts
   - Admin status

2. **View All Bookings** (`GET /admin/bookings`)
   - See ALL user bookings (not just their own)
   - Filter by booking status
   - View booking details:
     - User name and email
     - Hotel details
     - Destination
     - Check-in/Check-out dates
     - Total price
     - Booking status

3. **Manage Bookings** (`PUT /admin/bookings/{id}/status`)
   - Update booking status
   - Change from Pending → Confirmed → Completed
   - Or Cancelled

4. **View Dashboard** (`GET /admin/dashboard`)
   - Recent bookings
   - System statistics
   - User count
   - Revenue data

5. **Manage Users** (`DELETE /admin/users/{id}`)
   - Delete user accounts
   - Promote users to admin

6. **View Statistics** (`GET /admin/stats`)
   - Total users/bookings/hotels/destinations
   - Booking breakdown by status
   - Total revenue
   - Admin vs Normal users

---

## 📊 Database Schema

### Users Table
```sql
CREATE TABLE users (
    user_id INT PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255),          -- Encrypted with BCrypt
    full_name VARCHAR(100),
    phone VARCHAR(20),
    is_admin BOOLEAN DEFAULT FALSE, -- TRUE = Admin, FALSE = Normal User
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
```

**Example Records:**
```
user_id=1: username="admin", is_admin=TRUE
user_id=2: username="john_doe", is_admin=FALSE (newly registered)
user_id=3: username="jane_smith", is_admin=FALSE (newly registered)
```

### Bookings Table
```sql
CREATE TABLE bookings (
    booking_id INT PRIMARY KEY,
    user_id INT,              -- References users table
    hotel_id INT,
    destination_id INT,
    check_in_date DATE,
    check_out_date DATE,
    number_of_nights INT,
    number_of_guests INT,
    number_of_rooms INT,
    total_price DECIMAL(10,2),
    status ENUM('Pending','Confirmed','Cancelled','Completed'),
    booking_date TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
```

**Admin can see all rows regardless of user_id**

---

## 🔧 Admin API Endpoints

### View All Users
```bash
GET /api/admin/users
Authorization: Bearer <admin-token>

Response:
{
  "success": true,
  "data": [
    {
      "userId": 1,
      "username": "admin",
      "email": "admin@smarttravel.com",
      "isAdmin": true,
      "bookingCount": 5,
      "itineraryCount": 3
    },
    {
      "userId": 2,
      "username": "john_doe",
      "email": "john@example.com",
      "isAdmin": false,
      "bookingCount": 2,
      "itineraryCount": 1
    }
  ]
}
```

### View All Bookings
```bash
GET /api/admin/bookings
Authorization: Bearer <admin-token>

Response:
{
  "success": true,
  "data": {
    "bookings": [
      {
        "bookingId": 1,
        "userId": 2,
        "userName": "john_doe",
        "userEmail": "john@example.com",
        "hotelName": "Eiffel Tower Hotel",
        "destinationName": "Paris",
        "checkInDate": "2026-05-01",
        "checkOutDate": "2026-05-05",
        "numberOfNights": 4,
        "totalPrice": 1400.00,
        "status": "Confirmed",
        "bookingDate": "2026-04-24T10:30:00"
      }
    ],
    "totalBookings": 15
  }
}
```

### Get User's Bookings (Admin Only)
```bash
GET /api/admin/bookings/user/2
Authorization: Bearer <admin-token>

Response shows all bookings for user ID 2
```

### Update Booking Status
```bash
PUT /api/admin/bookings/1/status?status=Completed
Authorization: Bearer <admin-token>

Response:
{
  "success": true,
  "message": "Booking status updated"
}
```

### View System Statistics
```bash
GET /api/admin/stats
Authorization: Bearer <admin-token>

Response:
{
  "totalUsers": 15,
  "adminUsers": 1,
  "normalUsers": 14,
  "totalBookings": 25,
  "confirmedBookings": 18,
  "pendingBookings": 5,
  "cancelledBookings": 2,
  "totalDestinations": 10,
  "totalHotels": 120,
  "totalRevenue": 25000.00
}
```

### View Admin Dashboard
```bash
GET /api/admin/dashboard
Authorization: Bearer <admin-token>

Response:
{
  "recentBookings": [...],
  "statistics": {
    "totalUsers": 15,
    "totalBookings": 25,
    "totalDestinations": 10,
    "totalHotels": 120
  }
}
```

---

## 📈 Test the Features

### Test 1: Create New User
1. Go to http://localhost:4200
2. Click "Register"
3. Fill in details:
   - Username: testuser
   - Email: test@example.com
   - Password: test123
   - Full Name: Test User
4. Click Register
5. User is saved to database with `is_admin = FALSE`

### Test 2: Login as Admin
1. Use credentials: admin / admin
2. You'll see admin dashboard
3. Navigate to "Admin" section

### Test 3: View All Users
1. In admin section, click "Users"
2. See all registered users including the new testuser
3. View their booking/itinerary counts

### Test 4: View All Bookings
1. In admin section, click "Bookings"
2. See ALL bookings from ALL users
3. Normal users can only see their own
4. Admins see everything

### Test 5: Update Booking Status
1. In Bookings list, select a booking
2. Click "Update Status"
3. Change from Pending → Confirmed
4. Status updates immediately

---

## 🐛 Troubleshooting

### Services won't start
```bash
# Check if MySQL is running
mysqladmin ping -h 127.0.0.1

# Check Java version
java -version  # Should be 17+

# Check Node version
node -v  # Should be 18+
```

### Can't access frontend
```bash
# Check if port 4200 is in use
# Windows:
netstat -ano | findstr :4200

# Mac/Linux:
lsof -i :4200

# Clear Angular cache and restart
cd frontend
rm -rf .angular
npm start
```

### Database connection issues
```bash
# Check MySQL is running
mysql -u root -p -e "SELECT 1;"

# Check database exists
mysql -u root -p -e "SHOW DATABASES;"

# Check tables created
mysql -u root -p -e "USE smart_travel_db; SHOW TABLES;"

# Reimport schema if needed
mysql -u root -p < database/database_schema.sql
mysql -u root -p < database/sample_data.sql
```

### Need to reset database
```bash
mysql -u root -p -e "DROP DATABASE smart_travel_db;"
mysql -u root -p < database/database_schema.sql
mysql -u root -p < database/sample_data.sql
```

---

## 📝 Useful Commands

```bash
# Stop backend (in backend terminal)
Ctrl+C

# Stop frontend (in frontend terminal)
Ctrl+C

# Access MySQL directly
mysql -u root -p smart_travel_db

# Check backend health
curl http://localhost:8080/actuator/health

# Rebuild backend
cd backend && mvn clean package

# Reinstall frontend dependencies
cd frontend && rm -rf node_modules && npm install
```

---

## 🔒 Security Note

### Default Admin Credentials
These are for **DEVELOPMENT ONLY**:
- Username: `admin`
- Password: `admin`

### Before Production:
1. **Change admin password** in database
2. **Change JWT secret** in application.properties
3. **Update database credentials**
4. **Enable HTTPS/SSL**
5. **Set up firewall rules**
6. **Enable authentication** on database

---

## ✅ Checklist After Starting

- [ ] Frontend loads at http://localhost:4200
- [ ] Can login with admin/admin
- [ ] Can see 10 destinations on home page
- [ ] Can register new user
- [ ] New user is saved to database
- [ ] Can login with new user
- [ ] Can make a booking
- [ ] Can login as admin
- [ ] Admin can see all users
- [ ] Admin can see all bookings
- [ ] Can update booking status as admin
- [ ] Can view system statistics

---

## 📚 More Documentation

- **README.md** - Complete project documentation
- **DEVELOPER_GUIDE.html** - Technical details and architecture
- **QUICK_START.md** - 5-minute quick reference
- **PROJECT_SUMMARY.md** - What was built

---

## 🎉 You're All Set!

The application is now running with:
- ✅ All users registered saved to database
- ✅ Admin can view ALL user bookings
- ✅ Easy startup scripts for Windows and Mac/Linux

**Enjoy Smart Travel Planner! 🌍✈️🏖️**
