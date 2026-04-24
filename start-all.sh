#!/bin/bash

# Smart Travel Planner - One Command Startup
# Requires: Docker installed
# That's it!

echo ""
echo "╔════════════════════════════════════════════════════════════╗"
echo "║   Smart Travel Planner - Docker Compose Startup            ║"
echo "║   Starting: MySQL + Backend + Frontend                     ║"
echo "╚════════════════════════════════════════════════════════════╝"
echo ""

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    echo "❌ Docker is not installed. Please install Docker first."
    echo "   https://docs.docker.com/get-docker/"
    exit 1
fi

echo "✓ Docker is installed"
echo ""

# Clean up any old containers
echo "Cleaning up old containers..."
docker-compose down 2>/dev/null || true

echo ""
echo "🚀 Starting all services with Docker Compose..."
echo ""

# Start all services
docker-compose up

