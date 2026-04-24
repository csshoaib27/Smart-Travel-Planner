@echo off
REM Smart Travel Planner - Easy Startup Script (Windows)
REM This script starts all services with Docker Compose

setlocal enabledelayedexpansion

echo.
echo ╔════════════════════════════════════════════════════════════╗
echo ║     Smart Travel Planner - One-Click Startup              ║
echo ╚════════════════════════════════════════════════════════════╝
echo.

REM Check if Docker is installed
docker --version >nul 2>&1
if errorlevel 1 (
    echo ❌ Docker is not installed. Please install Docker Desktop first.
    echo    https://docs.docker.com/get-docker/
    pause
    exit /b 1
)

echo ✓ Docker is installed
echo.

REM Check if Docker Compose is installed
docker-compose --version >nul 2>&1
if errorlevel 1 (
    echo ❌ Docker Compose is not installed.
    echo    Docker Desktop usually includes it. Please reinstall Docker Desktop.
    pause
    exit /b 1
)

echo ✓ Docker Compose is installed
echo.

REM Check if docker-compose.yml exists
if not exist docker-compose.yml (
    echo ❌ docker-compose.yml not found in current directory
    echo    Please run this script from the project root directory
    pause
    exit /b 1
)

echo 🚀 Starting Smart Travel Planner...
echo.

REM Build and start containers
echo 📦 Building Docker images (this may take a few minutes on first run)...
docker-compose build --no-cache
if errorlevel 1 (
    echo ❌ Failed to build Docker images
    pause
    exit /b 1
)

echo.
echo 🏃 Starting services...
docker-compose up -d
if errorlevel 1 (
    echo ❌ Failed to start services
    pause
    exit /b 1
)

echo.
echo ⏳ Waiting for services to be ready (30 seconds)...
timeout /t 30 /nobreak

echo.
echo ╔════════════════════════════════════════════════════════════╗
echo ║              ✅ All Services Started!                      ║
echo ╠════════════════════════════════════════════════════════════╣
echo ║                                                            ║
echo ║  🌐 Frontend:    http://localhost:4200                    ║
echo ║  📡 Backend:     http://localhost:8080                    ║
echo ║  📚 API Docs:    http://localhost:8080/swagger-ui.html    ║
echo ║  🗄️  Database:    localhost:3306                           ║
echo ║                                                            ║
echo ║  📝 Default Login:                                         ║
echo ║     Username: admin                                        ║
echo ║     Password: admin                                        ║
echo ║                                                            ║
echo ║  📖 Documentation:                                         ║
echo ║     - QUICK_START.md (5-minute guide)                     ║
echo ║     - README.md (complete guide)                          ║
echo ║     - DEVELOPER_GUIDE.html (technical details)            ║
echo ║                                                            ║
echo ╚════════════════════════════════════════════════════════════╝
echo.

echo 🔗 Opening application in browser...
start http://localhost:4200

echo.
echo 📋 Useful Commands:
echo    - View logs:           docker-compose logs -f
echo    - Stop services:       docker-compose down
echo    - Restart services:    docker-compose restart
echo    - Clean up:            docker-compose down -v
echo.
echo ✨ Happy coding! Enjoy Smart Travel Planner! ✨
echo.
pause
