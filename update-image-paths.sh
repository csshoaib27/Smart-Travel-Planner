#!/bin/bash
# Script to update destination image paths from placeholders to local assets
# Run this script from the Frontend directory

echo "Updating destination image paths..."

# Update destination.service.ts
sed -i 's|https://via.placeholder.com/400x300?text=|assets/images/destinations/|g' src/app/services/destination.service.ts
sed -i 's/"$/".jpg"/g' src/app/services/destination.service.ts

# Update destinations.json
sed -i 's|https://via.placeholder.com/400x300?text=|assets/images/destinations/|g' src/app/assets/data/destinations.json
sed -i 's/"$/".jpg"/g' src/app/assets/data/destinations.json

echo "Image paths updated! Now add the actual image files to src/app/assets/images/destinations/"