# Docker Setup Guide - For Your Friend with Zero Installation

This guide helps anyone run the Smart Travel Planner project **without installing Java, Node.js, MySQL, or any other tools**. Only Docker is needed!

## 🎯 What is Docker?

Think of Docker as a **box that contains everything your application needs**:
- Java Runtime
- Node.js
- MySQL Database
- All dependencies and configurations

Your friend just needs to have Docker installed, and they can run the entire application!

---

## 📋 Prerequisites

### Option 1: Desktop Docker (Easiest)
- **Docker Desktop** (includes both Docker Engine and Docker Compose)
- Download from: https://www.docker.com/products/docker-desktop/
- Works on macOS, Windows, and Linux

### Option 2: Docker Command Line
- **Docker**: https://docs.docker.com/get-docker/
- **Docker Compose**: https://docs.docker.com/compose/install/

---

## 🚀 Quick Start (Copy & Paste Instructions)

### Step 1: Clone the Repository
```bash
git clone https://github.com/csshoaib27/Smart-Travel-Planner.git
cd Smart-Travel-Planner
```

### Step 2: Download and Install Docker Desktop
- Visit: https://www.docker.com/products/docker-desktop/
- Install for your operating system (macOS, Windows, or Linux)
- Launch Docker Desktop

### Step 3: Run Everything with One Command
```bash
docker-compose up
```

Wait for 2-3 minutes while Docker builds and starts all services...

When you see this message, everything is ready:
```
backend    | Started SmartTravelPlannerApplication
frontend   | Listening on 0.0.0.0:4200
```

### Step 4: Open Your Browser
- **Website:** http://localhost:4200
- **API:** http://localhost:8080
- **Database:** localhost:3306

---

## 🛑 Stopping the Application

### Option 1: Press Ctrl+C in Terminal
This gracefully stops all containers

### Option 2: Docker Desktop GUI
Click "Stop" button in Docker Desktop

### Option 3: Terminal Command
```bash
docker-compose down
```

---

## 🔄 Restart the Application

### Start Again:
```bash
docker-compose up
```

### From Scratch (Remove All Data):
```bash
docker-compose down -v
docker-compose up
```

---

## 📊 What's Running?

Three containers start automatically:

| Container | Port | Purpose |
|-----------|------|---------|
| **MySQL Database** | 3306 | Stores all trip data |
| **Spring Boot Backend** | 8080 | Server & API |
| **Angular Frontend** | 4200 | Website UI |

---

## 🔧 Database Access

If your friend wants to directly access the database:

### MySQL Credentials:
- **Host:** localhost
- **Port:** 3306
- **Database:** smart_travel_planner
- **Username:** traveler
- **Password:** traveler123

### Using MySQL Command Line:
```bash
mysql -h localhost -u traveler -p smart_travel_planner
# When prompted, enter password: traveler123
```

### Using MySQL Workbench:
1. Install MySQL Workbench: https://dev.mysql.com/downloads/workbench/
2. Create connection with above credentials
3. Connect and browse data

---

## 💾 Persistent Data

**Important:** When you run `docker-compose up` again, **all data is preserved** (trips, hotels, reviews). Data is stored in a Docker volume named `mysql_data`.

### To Completely Reset Data:
```bash
docker-compose down -v
docker-compose up
```

The `-v` flag removes all volumes (databases), so fresh sample data loads.

---

## 🐛 Troubleshooting

### Problem: "docker: command not found"
**Solution:** Docker is not installed. Download Docker Desktop: https://www.docker.com/products/docker-desktop/

### Problem: "Port 3306 already in use"
**Solution:** Another MySQL is running. Either:
- Close other MySQL: `mysql.server stop`
- Or change port in docker-compose.yml (change first `3306` to `3307`)

### Problem: "docker-compose: command not found"
**Solution:** Use `docker compose` instead (newer Docker):
```bash
docker compose up
```

### Problem: Website shows "Unable to connect to API"
**Solution:** Wait 60 seconds for backend to fully start. Check logs:
```bash
docker-compose logs backend
```

### Problem: Need to rebuild everything
**Solution:**
```bash
docker-compose down -v
docker image prune -a
docker-compose up --build
```

---

## 📁 File Structure

```
Smart-Travel-Planner/
├── docker-compose.yml         ← Main configuration file
├── Frontend/
│   └── smart-travel-planner/
│       ├── Dockerfile         ← Frontend container instructions
│       ├── nginx.conf         ← Web server configuration
│       └── ...
├── Backend/
│   ├── Dockerfile             ← Backend container instructions
│   ├── pom.xml
│   └── ...
├── Database/
│   └── schema.sql
└── ...
```

---

## 🎓 For Absolute Beginners - What's Happening?

When you run `docker-compose up`:

```
1. Docker reads docker-compose.yml
2. Creates a private network for containers
3. Starts MySQL container with empty database
4. Runs schema.sql to create tables and sample data
5. Starts Backend container (Java/Spring Boot)
6. Backend connects to MySQL and starts API
7. Starts Frontend container (Angular)
8. Frontend compiled and served by Nginx
9. All containers can talk to each other
10. You can access the website at http://localhost:4200
```

---

## 🌐 Accessing Services

### The Website
- **URL:** http://localhost:4200
- **What you see:** The Smart Travel Planner UI
- **Runs in:** Browser (Safari, Chrome, Firefox, Edge)

### The Backend API
- **URL:** http://localhost:8080
- **What it does:** Handles all data operations
- **Example:** http://localhost:8080/api/destinations

### The Database
- **Host:** localhost:3306
- **Access:** MySQL clients (MySQL Workbench, CLI, etc.)

---

## 📝 Useful Commands Reference

```bash
# Start everything
docker-compose up

# Start in background (detached mode)
docker-compose up -d

# View logs
docker-compose logs

# View logs for specific service
docker-compose logs backend
docker-compose logs frontend
docker-compose logs mysql

# Stop everything
docker-compose down

# Stop and remove all data
docker-compose down -v

# Restart a specific service
docker-compose restart backend

# Build images again
docker-compose up --build

# Execute command in running container
docker-compose exec backend bash
docker-compose exec mysql mysql -u traveler -p

# View running containers
docker-compose ps

# View container resource usage
docker stats
```

---

## 🔐 Security Notes

**These are default credentials for local development only:**
- Username: traveler / Password: traveler123
- Root Password: root123

**For production, ALWAYS change:**
1. Edit docker-compose.yml environment variables
2. Update Backend application.properties
3. Use strong passwords

---

## 🎯 Next Steps for Your Friend

1. ✅ Install Docker Desktop
2. ✅ Clone the repository
3. ✅ Run `docker-compose up`
4. ✅ Visit http://localhost:4200
5. ✅ Explore the application
6. ✅ Check the Backend API at http://localhost:8080/api/destinations
7. ✅ Read the main README.md for project details

---

## 📚 Learn More

- **Docker Docs:** https://docs.docker.com/
- **Docker Compose:** https://docs.docker.com/compose/
- **Spring Boot Docker:** https://spring.io/guides/gs/spring-boot-docker/
- **Angular Docker:** https://angular.io/guide/deployment

---

## ✅ Verification Checklist

After running `docker-compose up`, verify everything works:

- [ ] Can open http://localhost:4200 in browser
- [ ] Website loads without errors
- [ ] Can see destinations, hotels list
- [ ] Backend API responses at http://localhost:8080/api/destinations
- [ ] Database contains sample data

If all above are checked, congratulations! 🎉

---

## 📞 Help & Support

If something doesn't work:

1. Check Docker is running: `docker ps`
2. Check logs: `docker-compose logs`
3. Restart everything: `docker-compose down -v && docker-compose up`
4. Check GitHub Issues: https://github.com/csshoaib27/Smart-Travel-Planner/issues

---

**That's it! Your friend can now develop and test the entire application without installing anything except Docker!**
