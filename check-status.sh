#!/bin/bash

echo "╔════════════════════════════════════════════════════════════╗"
echo "║   Smart Travel Planner - Application Status Check          ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m'

# Check MySQL
echo "Checking MySQL..."
if mysqladmin ping -h 127.0.0.1 --silent 2>/dev/null; then
    echo -e "${GREEN}✓${NC} MySQL running on localhost:3306"
else
    echo -e "${RED}✗${NC} MySQL NOT responding on localhost:3306"
fi

# Check Backend
echo "Checking Backend..."
if curl -s http://localhost:8080/actuator/health > /dev/null 2>&1; then
    echo -e "${GREEN}✓${NC} Backend running (Port 8080)"
    BACKEND_RESPONSE=$(curl -s http://localhost:8080/actuator/health)
    echo "  Status: $(echo $BACKEND_RESPONSE | grep -o '"status":"[^"]*"' | cut -d'"' -f4)"
else
    echo -e "${RED}✗${NC} Backend NOT responding on port 8080"
fi

# Check Frontend
echo "Checking Frontend..."
if curl -s http://localhost:4200 > /dev/null 2>&1; then
    echo -e "${GREEN}✓${NC} Frontend running (Port 4200)"
else
    echo -e "${RED}✗${NC} Frontend NOT responding on port 4200"
fi

echo ""
echo "Access URLs:"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "Frontend:   http://localhost:4200"
echo "Backend:    http://localhost:8080"
echo "API Docs:   http://localhost:8080/swagger-ui.html"
echo "Health:     http://localhost:8080/actuator/health"
echo ""
echo "Login Credentials:"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "Email:      admin@smarttravel.com"
echo "Password:   admin"
echo ""
echo "For detailed verification steps, see: VERIFY_APPLICATION.md"
echo ""
