# Smart Travel Planner - Application Verification Guide

## ✅ Quick Health Check

Run these commands in separate terminal windows to verify everything is working:

### 1. Check Backend Health
```bash
curl http://localhost:8080/actuator/health
```

**Expected Response:**
```json
{
  "status": "UP"
}
```

### 2. Check API Availability
```bash
curl http://localhost:8080/api/destinations
```

**Expected Response:**
- Should return a JSON array of destinations (may require authentication)
- OR a 401 unauthorized (which means backend is working!)

### 3. Check Frontend
```bash
curl http://localhost:4200
```

**Expected Response:**
- Should return HTML content (the Angular app)

### 4. Test Login with cURL
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@smarttravel.com",
    "password": "admin"
  }'
```

**Expected Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "email": "admin@smarttravel.com",
    "name": "Admin User",
    "isAdmin": true
  }
}
```

---

## 🌐 Browser Testing

### Step 1: Access Frontend
Open browser and go to: **http://localhost:4200**

### Step 2: Login
- **Email:** `admin@smarttravel.com`
- **Password:** `admin`

### Step 3: Verify Admin Dashboard
After login, you should see:
- List of all destinations
- Hotel listings
- Admin controls (if visible)

### Step 4: Test Admin Features
1. Open browser console: `F12` → Console
2. Use the API from Swagger UI:
   - Go to: http://localhost:8080/swagger-ui.html
   - Authorize with JWT token from login
   - Try: `GET /api/admin/users` → Should show all users
   - Try: `GET /api/admin/bookings` → Should show all bookings from all users

### Step 5: Register New User
1. Click "Register" on login page
2. Fill in details:
   - Email: `testuser@example.com`
   - Password: `password123`
   - Name: `Test User`
3. Submit registration
4. Login with new credentials
5. **Expected:** New user should have `isAdmin: false`

---

## 📊 Database Verification

### Check Database Connection
```bash
# From project root, connect to MySQL
docker exec -it smart-travel-mysql mysql -uroot -proot smart_travel_db -e "SELECT id, email, is_admin FROM users;"
```

**Expected Output:**
```
+----+------------------------+----------+
| id | email                  | is_admin |
+----+------------------------+----------+
|  1 | admin@smarttravel.com  |        1 |
|  2 | user@smarttravel.com   |        0 |
+----+------------------------+----------+
```

### Check Hotels Table
```bash
docker exec -it smart-travel-mysql mysql -uroot -proot smart_travel_db -e "SELECT id, name, city, price FROM hotels LIMIT 5;"
```

---

## 🐳 Docker Container Status

```bash
# Check all containers are running
docker-compose ps

# View backend logs
docker-compose logs backend

# View MySQL logs
docker-compose logs mysql

# View frontend logs
docker-compose logs frontend
```

---

## 🆘 Troubleshooting

### Backend Not Responding
```bash
# Check if port 8080 is in use
lsof -i :8080

# Kill if needed
kill -9 <PID>

# Restart backend
docker-compose restart backend
```

### Frontend Not Loading
```bash
# Check if port 4200 is in use
lsof -i :4200

# Restart frontend
docker-compose restart frontend
```

### Login Still Failing
1. Check backend logs: `docker-compose logs backend | tail -50`
2. Verify database has admin user: See "Database Verification" above
3. Check API directly: `curl http://localhost:8080/api/auth/login` with email/password

### Port Conflicts
```bash
# Find what's using port
lsof -i :<PORT>

# Kill process
sudo kill -9 <PID>

# Or change Docker port in docker-compose.yml
```

---

## ✨ Success Criteria

- ✅ Backend responds to health check (HTTP 200)
- ✅ Frontend loads at localhost:4200
- ✅ Can login with admin@smarttravel.com / admin
- ✅ Can see destinations and hotels after login
- ✅ Can register new user
- ✅ New users have is_admin = false
- ✅ Admin can see all users via API
- ✅ Admin can see all bookings via API

---

## 📝 Next Steps

1. **Run verification commands above** to confirm all services are up
2. **Test login in browser** - should redirect to dashboard
3. **Register a new user** - verify is_admin is false in database
4. **Access Swagger UI** - test admin endpoints
5. **Review QUICK_REFERENCE.md** - for all available endpoints

---

Last verified: 2026-04-24
