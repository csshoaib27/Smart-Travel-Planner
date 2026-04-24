# Smart Travel Planner - Simple Startup Guide

## Prerequisites

- Java 17+
- Maven 3.8+
- Node.js 18+
- MySQL 8.0+ (running locally)

---

## Step 1: Setup Database

Start MySQL and import the schema:

```bash
mysql -u root -p < database/database_schema.sql
mysql -u root -p < database/sample_data.sql
```

Verify:
```bash
mysql -u root -p -e "USE smart_travel_db; SELECT COUNT(*) FROM users;"
```

---

## Step 2: Start Backend (Spring Boot)

Open a terminal and run:

```bash
cd backend
mvn spring-boot:run
```

Wait for:
```
Tomcat started on port(s): 8080
```

---

## Step 3: Start Frontend (Angular)

Open a **new terminal** and run:

```bash
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

## Done!

You now have all 3 services running:
- MySQL on localhost:3306
- Backend on localhost:8080
- Frontend on localhost:4200

---

## Login

```
Username: admin
Password: admin
```

---

## Stop Everything

Press `Ctrl+C` in each terminal to stop the backend and frontend.

To stop MySQL, use your system's service manager (e.g., `sudo service mysql stop` on Linux, or stop it via MySQL Workbench / Windows Services on Windows).

---

## Troubleshooting

### Backend won't start?
```bash
# Check MySQL is running and credentials are correct in backend/src/main/resources/application.properties
mysql -h 127.0.0.1 -u root -p -e "SELECT 1;"

# Check Java version
java -version  # Should be 17+
```

### Frontend won't start?
```bash
cd frontend
rm -rf node_modules
npm install
npm start
```

### Port already in use?
```bash
# Find process on port 8080 (Backend)
lsof -ti:8080 | xargs kill -9

# Find process on port 4200 (Frontend)
lsof -ti:4200 | xargs kill -9
```
