# Smart Travel Planner - SIMPLE Startup Guide

## 🚀 Quick Start (No Docker Complexity)

This is the simplest way to get everything running!

---

## **Step 1: Rename Folder (One Time)**

```bash
cd /Users/arunreddy/Documents/Claude/Projects/
mv "Smart-travel-planner (1)" Smart-Travel-Planner
cd Smart-Travel-Planner
```

---

## **Step 2: Start MySQL Only with Docker**

```bash
# Start just the database
docker run --name smart-travel-mysql \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=smart_travel_db \
  -p 3306:3306 \
  -d mysql:8.0
```

Then setup the database:

```bash
# Wait 5 seconds for MySQL to start
sleep 5

# Create tables
mysql -h 127.0.0.1 -u root -proot smart_travel_db < database/database_schema.sql

# Add sample data
mysql -h 127.0.0.1 -u root -proot smart_travel_db < database/sample_data.sql
```

---

## **Step 3: Start Backend (Spring Boot)**

Open a **NEW TERMINAL TAB** and run:

```bash
cd Smart-Travel-Planner
cd backend
mvn spring-boot:run
```

Wait for:
```
Tomcat started on port(s): 8080
```

---

## **Step 4: Start Frontend (Angular)**

Open **ANOTHER NEW TERMINAL TAB** and run:

```bash
cd Smart-Travel-Planner
cd frontend
npm install
npm start
```

Wait for:
```
Compiled successfully
```

Application opens at: **http://localhost:4200**

---

## ✅ Done!

You now have all 3 services running:
- ✅ MySQL on localhost:3306
- ✅ Backend on localhost:8080
- ✅ Frontend on localhost:4200

---

## 🔐 Login

```
Username: admin
Password: admin
```

---

## 🛑 Stop Everything

When done, stop in this order:

```bash
# Terminal 1 (Frontend) - Press Ctrl+C
# Terminal 2 (Backend) - Press Ctrl+C
# Terminal 3 - Run:
docker stop smart-travel-mysql
docker rm smart-travel-mysql
```

---

## 🆘 If Something Goes Wrong

### MySQL already running?
```bash
docker stop smart-travel-mysql
docker rm smart-travel-mysql
# Then start again
```

### Backend won't start?
```bash
# Make sure MySQL is running
mysql -h 127.0.0.1 -u root -proot -e "SELECT 1;"

# Check Java is installed
java -version

# Try again
cd backend
mvn spring-boot:run
```

### Frontend won't start?
```bash
# Clear cache
cd frontend
rm -rf node_modules
npm install
npm start
```

---

## 📚 That's It!

This is the simplest way to run the app without Docker complexity.

Just 3 terminal tabs, that's all you need!
