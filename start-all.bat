@echo off
REM Smart Travel Planner - One Command Startup
REM Requires: Docker installed
REM That's it!

echo.
echo ╔════════════════════════════════════════════════════════════╗
echo ║   Smart Travel Planner - Docker Compose Startup            ║
echo ║   Starting: MySQL + Backend + Frontend                     ║
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

REM Clean up any old containers
echo Cleaning up old containers...
docker-compose down 2>nul

echo.
echo 🚀 Starting all services with Docker Compose...
echo.

REM Start all services
docker-compose up

pause
