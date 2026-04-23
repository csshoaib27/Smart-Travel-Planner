## 📸 How to Add Images to Cities

### Step 1: Prepare Your Images
1. **Find high-quality images** of each city (800x600px or 1200x800px recommended)
2. **Optimize images** for web (compress to under 200KB each)
3. **Use proper naming**: `{city-name}.jpg` (lowercase, hyphens for spaces)

### Step 2: Add Images to Project
1. Place your city images in: `Frontend/src/app/assets/images/destinations/`
2. Example file names:
   - `goa.jpg`
   - `manali.jpg`
   - `kerala.jpg`
   - `jaipur.jpg`
   - `delhi.jpg`
   - etc.

### Step 3: Update Destination Data
Update the `image` field in both files:
- `Frontend/src/app/services/destination.service.ts`
- `Frontend/src/app/assets/data/destinations.json`

Change from:
```json
"image": "https://via.placeholder.com/400x300?text=CityName"
```

To:
```json
"image": "assets/images/destinations/city-name.jpg"
```

### Step 4: Rebuild Frontend
```bash
docker-compose build frontend
docker-compose up -d
```

### Image Sources (Free & Legal):
- **Unsplash**: https://unsplash.com/ (search for city names)
- **Pexels**: https://pexels.com/ (free stock photos)
- **Pixabay**: https://pixabay.com/ (royalty-free images)
- **Wikimedia Commons**: https://commons.wikimedia.org/ (public domain)

### Best Practices:
- Use landscape orientation (horizontal images)
- Ensure images are well-lit and visually appealing
- Include landmarks or iconic city views
- Maintain consistent image quality across all cities
- Test on different screen sizes

### Example Image URLs for Testing:
If you want to use external URLs temporarily:
- Goa: `https://images.unsplash.com/photo-1512343879784-a960bf40e7f2?w=800`
- Manali: `https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800`
- Kerala: `https://images.unsplash.com/photo-1602216056096-3b40cc0c9944?w=800`