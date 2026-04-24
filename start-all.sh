#!/bin/bash

# Smart Travel Planner - Startup Script

echo "╔════════════════════════════════════════════════════════════╗"
echo "║     Smart Travel Planner - Startup Script                 ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

# Check prerequisites
echo "Checking prerequisites..."

if ! command -v java &> /dev/null; then
    echo "✗ Java not found. Please install Java 17+"
    exit 1
fi
echo "✓ Java: $(java -version 2>&1 | awk 'NR==1{print $3}' | tr -d '"')"

if ! command -v mvn &> /dev/null; then
    echo "✗ Maven not found. Please install Maven 3.8+"
    exit 1
fi
echo "✓ Maven installed"

if ! command -v node &> /dev/null; then
    echo "✗ Node.js not found. Please install Node.js 18+"
    exit 1
fi
echo "✓ Node.js: $(node -v)"

echo ""

# Check MySQL
echo "Checking MySQL..."
if mysqladmin ping -h 127.0.0.1 --silent 2>/dev/null; then
    echo "✓ MySQL is running"
else
    echo "⚠ MySQL is not responding on localhost:3306."
    echo "  Please start MySQL, then run:"
    echo "    mysql -u root -p < database/database_schema.sql"
    echo "    mysql -u root -p < database/sample_data.sql"
    exit 1
fi

echo ""
echo "Starting Backend (Spring Boot)..."
cd backend
mvn spring-boot:run &
BACKEND_PID=$!
cd ..

echo "Waiting for backend to initialize (30 seconds)..."
sleep 30

echo ""
echo "Starting Frontend (Angular)..."
cd frontend
npm install --silent
npm start &
FRONTEND_PID=$!
cd ..

echo ""
echo "╔════════════════════════════════════════════════════════════╗"
echo "║              All Services Starting!                        ║"
echo "╠════════════════════════════════════════════════════════════╣"
echo "║                                                            ║"
echo "║  Frontend:    http://localhost:4200                       ║"
echo "║  Backend:     http://localhost:8080                       ║"
echo "║  API Docs:    http://localhost:8080/swagger-ui.html       ║"
echo "║  Database:    localhost:3306                              ║"
echo "║                                                            ║"
echo "║  Default Login:                                            ║"
echo "║     Username: admin                                        ║"
echo "║     Password: admin                                        ║"
echo "║                                                            ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""
echo "Press Ctrl+C to stop all services"

wait $BACKEND_PID $FRONTEND_PID
