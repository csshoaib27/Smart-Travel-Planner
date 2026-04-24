# 🚀 Smart Travel Planner - Combined Single Command Startup

## Start Everything With ONE Command!

No need to open multiple terminal tabs anymore! Everything starts together.

---

## **Mac/Linux: One Command**

```bash
chmod +x start-all.sh
./start-all.sh
```

That's it! ✅

---

## **Windows: One Click**

Double-click: `start-all.bat`

Or in Command Prompt:
```bash
start-all.bat
```

That's it! ✅

---

## ✨ What Happens

The script automatically:

1. ✅ **Starts MySQL Database**
   - Stops any old container
   - Starts fresh MySQL
   - Sets up database schema
   - Loads sample data
   - Waits for readiness

2. ✅ **Starts Spring Boot Backend**
   - Runs in background
   - Builds and starts on port 8080
   - Waits for readiness

3. ✅ **Starts Angular Frontend**
   - Installs dependencies (if needed)
   - Starts dev server on port 4200
   - Opens in browser

---

## 📊 What You'll See

```
╔════════════════════════════════════════════════════════════╗
║   Smart Travel Planner - Combined Startup                 ║
║   Starting: MySQL + Backend + Frontend                    ║
╚════════════════════════════════════════════════════════════╝

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Step 1: Starting MySQL Database
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

✓ MySQL container started
  Waiting for database to be ready...
✓ MySQL is ready!

Setting up database schema and sample data...
✓ Database ready with sample data

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Step 2: Starting Spring Boot Backend
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

✓ Backend starting (PID: 12345)
  Waiting for backend to be ready on port 8080...
✓ Backend is ready on http://localhost:8080

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Step 3: Starting Angular Frontend
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

✓ Starting frontend on port 4200
✓ Frontend starting (PID: 12346)

╔════════════════════════════════════════════════════════════╗
║          ✅ ALL SERVICES STARTED SUCCESSFULLY!            ║
╠════════════════════════════════════════════════════════════╣
║                                                            ║
║  🌐 Frontend:    http://localhost:4200                    ║
║  📡 Backend:     http://localhost:8080                    ║
║  📚 API Docs:    http://localhost:8080/swagger-ui.html    ║
║  🗄️  Database:    localhost:3306                           ║
║                                                            ║
║  📝 Default Login:                                         ║
║     Username: admin                                        ║
║     Password: admin                                        ║
║                                                            ║
║  ⏹️  To stop all services: Press Ctrl+C                    ║
║                                                            ║
╚════════════════════════════════════════════════════════════╝

Press Ctrl+C to stop all services
```

---

## 🌐 Access the Application

Once you see "ALL SERVICES STARTED SUCCESSFULLY!":

| Service | URL |
|---------|-----|
| **Frontend** | http://localhost:4200 |
| **Backend API** | http://localhost:8080 |
| **API Docs** | http://localhost:8080/swagger-ui.html |

---

## 🔐 Login

```
Username: admin
Password: admin
```

---

## ⏹️ Stop Everything

### **Mac/Linux:**
```bash
Press Ctrl+C in the terminal
```

The script will:
- ✅ Kill backend process
- ✅ Kill frontend process
- ✅ Stop MySQL container
- ✅ Clean up

### **Windows:**
```
Close all 3 windows:
1. Close the backend window
2. Close the frontend window
3. Close the main script window

MySQL will stop automatically
```

---

## 🆘 Troubleshooting

### Script won't execute (Mac/Linux)

```bash
chmod +x start-all.sh
./start-all.sh
```

### Docker not found

```bash
# Make sure Docker is running
# On Mac, click Docker icon in top menu
# On Linux, check: sudo service docker status
```

### Port already in use?

```bash
# Kill the process using the port
# Mac/Linux:
lsof -i :4200    # Frontend
lsof -i :8080    # Backend
lsof -i :3306    # MySQL

# Kill with:
kill -9 <PID>
```

### MySQL connection error?

```bash
# Check if MySQL is running
docker ps

# If not, restart the script
docker stop smart-travel-mysql
docker rm smart-travel-mysql
./start-all.sh
```

---

## 📝 Features of Combined Start

✅ **Single Command** - No multiple terminals needed  
✅ **Automatic Setup** - Database schema created automatically  
✅ **Health Checks** - Waits for each service to be ready  
✅ **Sample Data** - Loads test data automatically  
✅ **Graceful Shutdown** - Ctrl+C stops everything cleanly  
✅ **Clear Logging** - See what's happening at each step  
✅ **Cross-Platform** - Works on Mac, Linux, and Windows  

---

## 🎯 Next Steps

1. Run the startup script
2. Wait for "ALL SERVICES STARTED SUCCESSFULLY!"
3. Open http://localhost:4200 in browser
4. Login with admin/admin
5. Enjoy! 🎉

---

**That's it! Simple, fast, and effective!** ⚡
