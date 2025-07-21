# Hotel Finder 3D - Android App

A modern Android hotel booking app with 3D visualization, city search, and price comparison features.

## Features

### 🏨 Hotel Search & Discovery
- **City Search by Letter**: Type any letter (e.g., "L") to find hotels in cities starting with that letter (London, Los Angeles, etc.)
- **Smart Filtering**: Filter hotels by price range, rating, and location
- **Multiple Sort Options**: Sort by price (low to high/high to low), rating, popularity, or distance

### 💰 Price Comparison
- **Multi-Platform Pricing**: Compare prices from Booking.com, Expedia, Hotels.com
- **Best Deal Highlighting**: Easily identify the best price for each hotel
- **Real-time Price Updates**: Get the latest pricing information

### 🏗️ 3D Hotel Visualization
- **Interactive 3D Models**: View hotels in immersive 3D format
- **OpenGL ES Rendering**: Smooth, hardware-accelerated 3D graphics
- **Touch Controls**: Rotate, zoom, and explore hotel models
- **Loading Animations**: Professional loading experience

### 🎨 Modern UI/UX
- **Material Design 3**: Latest Material Design guidelines
- **Jetpack Compose**: Modern declarative UI framework
- **Dark/Light Theme**: Automatic theme switching
- **Responsive Design**: Optimized for all screen sizes

### 📱 Hotel Information
- **High-Quality Images**: Beautiful hotel photography with image galleries
- **Detailed Information**: Comprehensive hotel descriptions, amenities, and location details
- **Star Ratings**: Visual star rating system with user reviews
- **Interactive Maps**: Location visualization with coordinates

## Famous Hotels Included

The app features data for world-renowned hotels including:

- **London**: The Ritz London, Claridge's
- **Paris**: Hotel Ritz Paris, Le Bristol Paris
- **New York**: The Plaza Hotel, The St. Regis New York
- **Los Angeles**: The Beverly Hills Hotel, Chateau Marmont
- **Tokyo**: The Ritz-Carlton Tokyo
- **Dubai**: Burj Al Arab

## Technical Architecture

### 🏗️ Architecture Pattern
- **MVVM (Model-View-ViewModel)**: Clean separation of concerns
- **Repository Pattern**: Centralized data management
- **Kotlin Coroutines**: Asynchronous programming
- **StateFlow**: Reactive state management

### 🛠️ Technologies Used
- **Kotlin**: Primary programming language
- **Jetpack Compose**: Modern UI toolkit
- **Material Design 3**: Design system
- **OpenGL ES 2.0**: 3D graphics rendering
- **Coil**: Image loading and caching
- **Coroutines**: Asynchronous operations
- **ViewModel & LiveData**: Architecture components

### 📦 Dependencies
- AndroidX Core KTX
- Jetpack Compose BOM
- Material Design 3
- Navigation Compose
- Coil for image loading
- OpenGL ES for 3D rendering
- Coroutines for async operations

## Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 24 (Android 7.0) or higher
- Kotlin 1.9.10 or later

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd hotel-app
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the project directory and open it

3. **Build and Run**
   - Wait for Gradle sync to complete
   - Click "Run" or press Shift+F10
   - Select your target device/emulator

### Building APK
```bash
./gradlew assembleDebug
```

The APK will be generated in `app/build/outputs/apk/debug/`

## Usage Guide

### 🔍 Searching for Hotels
1. **Open the app** and you'll see the main search screen
2. **Type a letter** in the search bar (e.g., "L" for London, Paris, Los Angeles)
3. **Apply filters** using the filter button for price range and ratings
4. **Sort results** using the quick sort chips (Price ↑, Rating)

### 🏨 Viewing Hotel Details
1. **Tap on any hotel card** to view detailed information
2. **Swipe through images** in the gallery
3. **View amenities, location, and description**
4. **Compare prices** from different booking platforms
5. **Tap "Book Now"** to proceed with booking

### 🎮 3D Hotel Experience
1. **Tap the "3D View" button** on any hotel card or detail page
2. **Wait for the 3D model to load** (loading animation will show)
3. **Interact with the model**:
   - Drag to rotate the hotel building
   - Pinch to zoom in/out
   - Double-tap to reset view
4. **Use the controls guide** in the bottom-left corner

### ⚙️ Filtering Options
- **Price Range**: Set minimum and maximum price limits
- **Rating Filter**: Use slider to set minimum star rating
- **Sort Options**: Choose from multiple sorting criteria
- **Clear Filters**: Reset all filters to default

## Project Structure

```
app/
├── src/main/java/com/hotelapp/
│   ├── data/
│   │   ├── Hotel.kt                 # Hotel data models
│   │   └── HotelRepository.kt       # Data repository
│   ├── viewmodel/
│   │   └── HotelViewModel.kt        # Business logic
│   ├── ui/
│   │   ├── components/
│   │   │   ├── HotelCard.kt         # Hotel card component
│   │   │   ├── SearchBar.kt         # Search component
│   │   │   └── FilterBottomSheet.kt # Filter UI
│   │   └── theme/
│   │       ├── Theme.kt             # App theme
│   │       └── Type.kt              # Typography
│   ├── MainActivity.kt              # Main search screen
│   ├── HotelDetailActivity.kt       # Hotel details screen
│   └── Hotel3DViewActivity.kt       # 3D visualization screen
└── src/main/res/
    ├── values/
    │   ├── strings.xml              # App strings
    │   ├── colors.xml               # Color definitions
    │   └── themes.xml               # Theme definitions
    └── xml/
        ├── data_extraction_rules.xml
        └── backup_rules.xml
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Future Enhancements

- [ ] Real API integration for live hotel data
- [ ] User authentication and booking history
- [ ] Offline mode with cached data
- [ ] Push notifications for price alerts
- [ ] Advanced 3D models with interior views
- [ ] Augmented Reality (AR) features
- [ ] Social sharing capabilities
- [ ] Multi-language support
- [ ] Accessibility improvements

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support, email support@hotelapp.com or create an issue in the repository.

---

**Hotel Finder 3D** - Experience hotels like never before! 🏨✨