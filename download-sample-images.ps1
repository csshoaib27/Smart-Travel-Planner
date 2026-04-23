# PowerShell script to download sample city images from Unsplash
# Run this script to get started with some free sample images

$imageUrls = @{
    "goa.jpg" = "https://images.unsplash.com/photo-1512343879784-a960bf40e7f2?w=800&h=600&fit=crop"
    "manali.jpg" = "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800&h=600&fit=crop"
    "kerala.jpg" = "https://images.unsplash.com/photo-1602216056096-3b40cc0c9944?w=800&h=600&fit=crop"
    "jaipur.jpg" = "https://images.unsplash.com/photo-1477587458883-47145ed94245?w=800&h=600&fit=crop"
    "delhi.jpg" = "https://images.unsplash.com/photo-1587135941948-670b381f08ce?w=800&h=600&fit=crop"
}

$destinationPath = "src/app/assets/images/destinations"

if (!(Test-Path $destinationPath)) {
    New-Item -ItemType Directory -Path $destinationPath -Force
}

Write-Host "Downloading sample city images..."

foreach ($image in $imageUrls.GetEnumerator()) {
    $outputPath = Join-Path $destinationPath $image.Key
    Write-Host "Downloading $($image.Key)..."
    try {
        Invoke-WebRequest -Uri $image.Value -OutFile $outputPath
        Write-Host "✓ Downloaded $($image.Key)"
    } catch {
        Write-Host "✗ Failed to download $($image.Key): $($_.Exception.Message)"
    }
}

Write-Host "Sample images downloaded! Update your destination data to use these images."