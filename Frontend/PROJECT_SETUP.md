# Smart Travel Planner - Frontend Documentation

## Project Overview
A comprehensive web application for planning smart travels based on budget, temperature, and user interests. This project is part of a full-stack application with Angular Frontend, Java Backend, and MySQL Database.

## Tech Stack
- **Framework**: Angular 19 (Standalone Components)
- **Language**: TypeScript
- **Styling**: CSS
- **Package Manager**: npm
- **Build Tool**: Angular CLI

## Project Structure

```
smart-travel-planner/
├── src/
│   ├── app/
│   │   ├── models/              # TypeScript interfaces for data models
│   │   │   ├── destination.model.ts
│   │   │   ├── hotel.model.ts
│   │   │   ├── trip.model.ts
│   │   │   ├── review.model.ts
│   │   │   ├── safety-alert.model.ts
│   │   │   ├── food.model.ts
│   │   │   ├── package-mode.model.ts
│   │   │   └── index.ts
│   │   ├── services/            # Angular Services for data management
│   │   │   ├── destination.service.ts
│   │   │   ├── hotel.service.ts
│   │   │   ├── trip.service.ts
│   │   │   ├── review.service.ts
│   │   │   ├── safety.service.ts
│   │   │   ├── food.service.ts
│   │   │   └── index.ts
│   │   ├── components/          # Standalone components
│   │   │   ├── home/
│   │   │   ├── destination/
│   │   │   ├── hotels/
│   │   │   ├── trip-planner/
│   │   │   ├── reviews/
│   │   │   ├── safety-alerts/
│   │   │   ├── food/
│   │   │   ├── chatbot/
│   │   │   ├── header/
│   │   │   ├── footer/
│   │   │   └── shared/
│   │   ├── assets/
│   │   │   └── data/            # Dummy JSON data files
│   │   │       ├── destinations.json
│   │   │       └── hotels.json
│   │   ├── app.ts               # Root component
│   │   ├── app.html
│   │   ├── app.css
│   │   └── app.routes.ts        # Route configuration
│   ├── styles.css               # Global styles
│   └── main.ts
├── public/
├── angular.json
├── package.json
└── tsconfig.json
```

## Key Features (Phase 1 - Frontend with Dummy Data)

### 1. Destination Discovery
- **Models**: `Destination` interface with properties like budget, temperature, rating, activities
- **Services**: `DestinationService`
- **Data**: Dummy data for 5 destinations (Goa, Manali, Kerala, Jaipur, Ladakh)
- **Features**: 
  - Search by budget
  - Filter by interests
  - Filter by temperature
  - Filter by rating
  - Display popular destinations

### 2. Hotel Management
- **Models**: `Hotel` interface
- **Services**: `HotelService`
- **Data**: 10 hotel entries across destinations
- **Features**:
  - Search hotels by destination
  - Filter by price range
  - Filter by amenities (WiFi, parking, gym, restaurant)
  - Filter by rating

### 3. Trip Planning
- **Models**: `Trip`, `ItineraryDay`, `Activity`, `Meal`, `CostBreakdown`
- **Services**: `TripService`
- **Features**:
  - Create custom trips
  - Generate itinerary based on duration
  - Calculate total cost breakdown
  - Share trips with friends
  - Search history
  - Split payment functionality

### 4. Reviews & Ratings
- **Models**: `Review` interface
- **Services**: `ReviewService`
- **Data**: Sample reviews with user feedback
- **Features**:
  - View destination reviews
  - Filter by trip type (family, solo, couple, adventure)
  - Rate reviews as helpful
  - Average rating calculation

### 5. Safety & Emergency Alerts
- **Models**: `SafetyAlert`, `TravelTip`
- **Services**: `SafetyService`
- **Data**: Hospitals, police stations, emergency contacts
- **Features**:
  - Nearby hospitals
  - Police stations
  - Emergency contact numbers
  - Travel tips by category (weather, entry, culture, etc.)

### 6. Traditional Food Guide
- **Models**: `TraditionalFood`
- **Services**: `FoodService`
- **Features**:
  - Cuisine information by destination
  - Dietary options (vegetarian, vegan, non-veg)
  - Best places to try
  - Estimated costs
  - Seasonal availability

### 7. Package Modes
- **Family Package**: Family-friendly itineraries
- **Solo Travel**: Solo traveler recommendations
- **Couple Package**: Romantic itineraries
- **Adventure Package**: Adventure-focused activities

## Routes

| Route | Component | Purpose |
|-------|-----------|---------|
| / | HomeComponent | Landing page |
| /home | HomeComponent | Home/Dashboard |
| /destinations | DestinationListComponent | Browse destinations |
| /destinations/:id | DestinationDetailComponent | View destination details |
| /hotels/:destinationId | HotelListComponent | Browse hotels |
| /trip-planner | TripPlannerComponent | Create custom trips |
| /trips | TripListComponent | View saved trips |
| /trips/:id | TripDetailComponent | View trip details |
| /reviews/:destinationId | ReviewListComponent | View destination reviews |
| /safety/:destinationId | SafetyDetailComponent | View safety info |
| /food/:destinationId | FoodListComponent | View traditional food |
| /chatbot | ChatbotComponent | AI travel assistant |

## Dummy Data Structure

### Destinations (5 entries)
- Goa
- Manali
- Kerala
- Jaipur
- Ladakh

### Hotels (10 entries)
- 2 hotels per major destination
- Mix of luxury and budget options
- Amenities and pricing information

### Reviews, Safety Alerts, Food Items
- Sample data for demonstration
- Realistic but fictional content

## Component Architecture

### Standalone Components
All components are built as standalones with lazy loading for better performance.

```typescript
@Component({
  selector: 'app-example',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './example.component.html',
  styleUrl: './example.component.css'
})
export class ExampleComponent { }
```

## Data Flow

```
User Input (in Component)
         ↓
Service (with dummy data)
         ↓
Observable Return (RxJS)
         ↓
Component (subscribe)
         ↓
Template (display with *ngFor, *ngIf)
```

## Next Steps for Full Development

### Phase 2: Backend Integration
- Replace dummy data with API calls to Java backend
- Implement HttpClientModule
- Handle authentication & authorization
- Real database with MySQL

### Phase 3: Advanced Features
- Smart Chatbot with NLP
- Payment integration (split payment)
- User profiles & saved preferences
- Real-time notifications
- Image uploads

### Phase 4: UI/UX Enhancements
- Responsive design improvements
- Mobile app version
- Progressive Web App (PWA)
-Accessibility improvements

## Running the Project

```bash
# Install dependencies
npm install

# Start development server
ng serve

# Build for production
ng build

# Run tests
ng test
```

## Git Workflow

**Team Members**: Shoaib (Initial Setup), 3 others (Features)

**Guidelines**:
1. Create feature branches from main
2. Commit with clear messages
3. Create Pull Requests for code review
4. Merge after approval

## File Structure for Each Component

```
component-name/
├── component-name.component.ts       # Component logic
├── component-name.component.html     # Template
├── component-name.component.css      # Styles
└── component-name.component.spec.ts  # Unit tests (future)
```

## Future Enhancements

1. **AI Chatbot**: Recommend trips based on user queries
2. **Machine Learning**: Personalized recommendations
3. **Real-time collaboration**: Live trip planning with friends
4. **Mobile optimization**: Responsive design & PWA
5. **Social features**: Trip sharing, user profiles
6. **Booking integration**: Direct hotel/flight booking
7. **Map integration**: Google Maps for destinations

## Notes for Team

- Use consistent naming conventions (camelCase for properties, PascalCase for types)
- Always use typed data with models/interfaces
- Keep components focused (single responsibility)
- Use services for all data logic
- Lazy load routes for better performance
- Document complex logic with comments

---

**Created**: March 27, 2026
**Version**: 1.0 (Initial Setup)
**Status**: Ready for feature development
