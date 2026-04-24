@echo off
setlocal

echo ╔════════════════════════════════════════════════════════════╗
echo ║     Smart Travel Planner - Startup Script                 ║
echo ╚════════════════════════════════════════════════════════════╝
echo.

REM Check Java
java -version >nul 2>&1
if errorlevel 1 (
    echo X Java not found. Please install Java 17+
    pause
    exit /b 1
)
echo + Java is installed

REM Check Maven
mvn -version >nul 2>&1
if errorlevel 1 (
    echo X Maven not found. Please install Maven 3.8+
    pause
    exit /b 1
)
echo + Maven is installed

REM Check Node.js
node -v >nul 2>&1
if errorlevel 1 (
    echo X Node.js not found. Please install Node.js 18+
    pause
    exit /b 1
)
echo + Node.js is installed

echo.
echo Checking MySQL...
mysqladmin ping -h 127.0.0.1 --silent >nul 2>&1
if errorlevel 1 (
    echo ! MySQL is not responding on localhost:3306.
    echo   Please start MySQL, then run:
    echo     mysql -u root -p ^< database\database_schema.sql
    echo     mysql -u root -p ^< database\sample_data.sql
    pause
    exit /b 1
)
echo + MySQL is running

echo.
echo Starting Backend in new window...
start "Smart Travel Backend" cmd /k "cd backend && mvn spring-boot:run"

echo Waiting for backend to initialize (30 seconds)...
timeout /t 30 /nobreak >nul

echo Starting Frontend in new window...
start "Smart Travel Frontend" cmd /k "cd frontend && npm install && npm start"

echo.
echo ╔════════════════════════════════════════════════════════════╗
echo ║              All Services Starting!                        ║
echo ╠════════════════════════════════════════════════════════════╣
echo ║                                                            ║
echo ║  Frontend:    http://localhost:4200                       ║
echo ║  Backend:     http://localhost:8080                       ║
echo ║  API Docs:    http://localhost:8080/swagger-ui.html       ║
echo ║  Database:    localhost:3306                              ║
echo ║                                                            ║
echo ║  Default Login:                                            ║
echo ║     Username: admin                                        ║
echo ║     Password: admin                                        ║
echo ║                                                            ║
echo ╚════════════════════════════════════════════════════════════╝
echo.
echo Backend and Frontend are running in separate windows.
echo Close those windows or press Ctrl+C in them to stop.
echo.
pause
