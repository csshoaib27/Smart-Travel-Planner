#!/bin/bash

# Smart Travel Planner - Easy Startup Script (Linux/Mac)
# This script starts all services with Docker Compose

set -e

echo "╔════════════════════════════════════════════════════════════╗"
echo "║     Smart Travel Planner - One-Click Startup              ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    echo "❌ Docker is not installed. Please install Docker first."
    echo "   https://docs.docker.com/get-docker/"
    exit 1
fi

# Check if Docker Compose is installed
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose is not installed. Please install it first."
    echo "   https://docs.docker.com/compose/install/"
    exit 1
fi

echo "✓ Docker and Docker Compose are installed"
echo ""

# Navigate to project directory
cd "$(dirname "$0")"

# Check if docker-compose.yml exists
if [ ! -f "docker-compose.yml" ]; then
    echo "❌ docker-compose.yml not found in current directory"
    exit 1
fi

echo "🚀 Starting Smart Travel Planner..."
echo ""

# Build and start containers
echo "📦 Building Docker images (this may take a few minutes on first run)..."
docker-compose build --no-cache

echo ""
echo "🏃 Starting services..."
docker-compose up -d

echo ""
echo "⏳ Waiting for services to be ready..."
sleep 10

# Check service health
echo ""
echo "🔍 Checking service status..."
echo ""

# Check MySQL
if docker-compose exec -T mysql mysqladmin ping -h localhost &> /dev/null; then
    echo "✓ MySQL Database is running on localhost:3306"
else
    echo "⚠ MySQL may still be starting..."
fi

# Check Backend
if curl -s http://localhost:8080/actuator/health &> /dev/null; then
    echo "✓ Spring Boot Backend is running on http://localhost:8080"
else
    echo "⚠ Backend may still be starting..."
fi

# Check Frontend
if curl -s http://localhost:4200 &> /dev/null; then
    echo "✓ Angular Frontend is running on http://localhost:4200"
else
    echo "⚠ Frontend may still be starting..."
fi

echo ""
echo "╔════════════════════════════════════════════════════════════╗"
echo "║              ✅ All Services Started!                      ║"
echo "╠════════════════════════════════════════════════════════════╣"
echo "║                                                            ║"
echo "║  🌐 Frontend:    http://localhost:4200                    ║"
echo "║  📡 Backend:     http://localhost:8080                    ║"
echo "║  📚 API Docs:    http://localhost:8080/swagger-ui.html    ║"
echo "║  🗄️  Database:    localhost:3306                           ║"
echo "║                                                            ║"
echo "║  📝 Default Login:                                         ║"
echo "║     Username: admin                                        ║"
echo "║     Password: admin                                        ║"
echo "║                                                            ║"
echo "║  📖 Documentation:                                         ║"
echo "║     - QUICK_START.md (5-minute guide)                     ║"
echo "║     - README.md (complete guide)                          ║"
echo "║     - DEVELOPER_GUIDE.html (technical details)            ║"
echo "║                                                            ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

echo "⏳ Services may take 10-30 seconds to fully initialize..."
echo ""
echo "🔗 Opening application in 5 seconds..."
sleep 5

# Try to open in browser
if command -v xdg-open &> /dev/null; then
    # Linux
    xdg-open http://localhost:4200
elif command -v open &> /dev/null; then
    # macOS
    open http://localhost:4200
fi

echo ""
echo "📋 Useful Commands:"
echo "   - View logs:           docker-compose logs -f"
echo "   - Stop services:       docker-compose down"
echo "   - Restart services:    docker-compose restart"
echo "   - Clean up:            docker-compose down -v"
echo ""
echo "✨ Happy coding! Enjoy Smart Travel Planner! ✨"
