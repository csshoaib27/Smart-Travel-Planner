# 🌍 Smart Travel Planner - START HERE

Welcome! This is your entry point to the Smart Travel Planner application.

---

## ⚡ Get Running in 3 Steps

### Step 1: Open Terminal/Command Prompt in Project Folder
```
Smart-travel-planner (1)/
```

### Step 2: Run Startup Script

**Windows:**
```bash
start.bat
```

**Mac/Linux:**
```bash
chmod +x start.sh
./start.sh
```

### Step 3: Wait & Enjoy
- Script checks prerequisites and starts all services
- Opens application in browser
- You're done! 🎉

---

## 📍 After Startup

Once running, access:

| Service | URL |
|---------|-----|
| **Application** | http://localhost:4200 |
| **Backend API** | http://localhost:8080 |
| **API Docs** | http://localhost:8080/swagger-ui.html |

**Default Login:**
```
Username: admin
Password: admin
```

---

## 📚 Documentation (Choose Your Path)

### 👨‍💼 I'm an Admin/Manager
→ Read: **FINAL_SUMMARY.md** (2 min read)
- Overview of what's new
- Feature comparison
- Key capabilities

### 👨‍💻 I'm a Developer
→ Read: **DEVELOPER_GUIDE.html** (20 min read)
- System architecture
- API reference  
- Code patterns
- Technical details

### 🚀 I Want Quick Start
→ Read: **QUICK_START.md** (5 min read)
- Fast setup
- Key endpoints
- Basic testing

### 📖 I Want Complete Documentation
→ Read: **README.md** (30 min read)
- Full project guide
- All features explained
- Deployment instructions
- Troubleshooting

### ✨ I Want to Understand Enhancements
→ Read: **ENHANCEMENTS.md** (10 min read)
- What changed
- Why it changed
- How to use new features

### 🎯 I Want a Detailed Guide
→ Read: **EASY_START_GUIDE.md** (15 min read)
- Complete one-click startup guide
- User registration explained
- Admin booking view detailed
- Testing instructions

---

## 🎯 What You Can Do

### Regular Users Can:
✅ Search destinations by budget/temperature  
✅ View hotels and book rooms  
✅ Create multi-day itineraries  
✅ Calculate trip costs  
✅ Write reviews  
✅ See their own bookings  

### Admins Can:
✅ Do everything above, plus:  
✅ View ALL users in system  
✅ View ALL bookings from ALL users  
✅ Update booking status  
✅ View system statistics  
✅ Manage user accounts  
✅ Access admin dashboard  

---

## 🧪 Test It Out

### Test 1: Register New User
1. Click "Register" on login page
2. Fill in details
3. User saved to database automatically
4. User appears in admin/users endpoint

### Test 2: Admin View All Bookings
1. Login as admin (admin / admin)
2. Go to Admin Dashboard
3. Click "View All Bookings"
4. See bookings from ALL users (not just own)

### Test 3: Update Booking Status
1. In bookings list, select a booking
2. Click "Update Status"
3. Change Pending → Confirmed
4. Status updates immediately

---

## 📂 File Structure

```
Project Root/
│
├── 🚀 START_HERE.md              ← You are here
├── ⚡ EASY_START_GUIDE.md         ← Complete beginner guide
├── 📖 README.md                   ← Full documentation
├── 🔧 DEVELOPER_GUIDE.html        ← Technical deep-dive
├── ✨ ENHANCEMENTS.md             ← What's new
├── 📊 FINAL_SUMMARY.md            ← High-level overview
│
├── 🚀 Startup Scripts
│   ├── start.sh (Mac/Linux)
│   └── start.bat (Windows)
│
├── 💾 Database
│   ├── database_schema.sql
│   └── sample_data.sql
│
├── 📱 Frontend (Angular)
│   ├── package.json
│   ├── angular.json
│   └── src/app/...
│
├── 🔙 Backend (Spring Boot)
│   ├── pom.xml
│   ├── application.properties
│   └── src/main/java/...
│
└── 📝 Other
    ├── PROJECT_SUMMARY.md
    ├── QUICK_START.md
    └── project-structure.txt
```

---

## ❓ Quick Questions?

### Q: How do I start the app?
**A:** Run `./start.sh` (Mac/Linux) or `start.bat` (Windows)

### Q: What's the default password?
**A:** Username: admin, Password: admin (for development only!)

### Q: How do I register a new user?
**A:** Click Register on login page. User is automatically saved to database.

### Q: Can admin see all bookings?
**A:** Yes! Go to Admin Dashboard → View All Bookings

### Q: How do I access the API?
**A:** Backend runs on http://localhost:8080, API docs at /swagger-ui.html

### Q: What if something breaks?
**A:** Check EASY_START_GUIDE.md "Troubleshooting" section

### Q: How do I stop services?
**A:** Press `Ctrl+C` in the backend and frontend terminals, then stop MySQL via your system's service manager

### Q: Can I access from another computer?
**A:** Change CORS origins in application.properties

---

## 🎯 Your Next Steps

1. **Right Now**: Run `./start.sh` or `start.bat` ⏱️ (30 seconds)
2. **In 1 minute**: Application opens at http://localhost:4200
3. **In 2 minutes**: Login with admin/admin
4. **In 5 minutes**: Try registering a new user
5. **In 10 minutes**: View all bookings as admin
6. **Later**: Read documentation based on your role

---

## 📊 Key Features

| Feature | Status |
|---------|--------|
| One-Click Startup | ✅ Ready |
| User Registration | ✅ Works + Saves to DB |
| Admin Booking View | ✅ 8 Endpoints |
| Destination Search | ✅ With Filters |
| Hotel Booking | ✅ Complete System |
| Cost Calculator | ✅ Multi-category |
| Itinerary Planner | ✅ Multi-day support |
| Review System | ✅ Ratings + Comments |
| Admin Dashboard | ✅ Full Stats |
| Documentation | ✅ 6 Guides |

---

## 🎉 You're All Set!

Everything is ready to go. Just run the startup script and start using the application!

**Questions?** See the documentation files above.  
**Issues?** Check EASY_START_GUIDE.md troubleshooting.  
**Ready?** Run `./start.sh` now! 🚀

---

## 📞 Documentation Quick Links

```
For Different Needs:

Beginners              → EASY_START_GUIDE.md
Developers            → DEVELOPER_GUIDE.html
Managers/Admins       → FINAL_SUMMARY.md
Complete Reference    → README.md
Features Explained    → ENHANCEMENTS.md
Quick Commands        → QUICK_START.md
```

---

**Welcome to Smart Travel Planner! 🌍✈️🏖️**

Now go run that startup script! →
```
./start.sh (Mac/Linux)
start.bat (Windows)
```
