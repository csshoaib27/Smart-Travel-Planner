# PowerShell script to update destination image paths from placeholders to local assets
# Run this script from the Frontend directory

Write-Host "Updating destination image paths..."

# Update destination.service.ts
$content = Get-Content "src/app/services/destination.service.ts" -Raw
$content = $content -replace 'https://via\.placeholder\.com/400x300\?text=([^'']*)', 'assets/images/destinations/$1.jpg'
$content = $content -replace 'https://via\.placeholder\.com/400x300\?text=([^"]*)', 'assets/images/destinations/$1.jpg'
$content | Set-Content "src/app/services/destination.service.ts"

# Update destinations.json
$content = Get-Content "src/app/assets/data/destinations.json" -Raw
$content = $content -replace 'https://via\.placeholder\.com/400x300\?text=([^"]*)', 'assets/images/destinations/$1.jpg'
$content | Set-Content "src/app/assets/data/destinations.json"

Write-Host "Image paths updated! Now add the actual image files to src/app/assets/images/destinations/"