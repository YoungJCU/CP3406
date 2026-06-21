# Weather Forecast Utility App – CP3406 / CP5307

A modern Android weather forecasting application built with Kotlin and Jetpack Compose for **Assessment 1: Utility App** in CP3406/CP5307.  
This app demonstrates best practices in mobile app architecture, API integration, and Material Design 3 UI principles.

---

## Getting Started

### How to Run
1. Clone or download this repo  
2. Open in Android Studio  
3. Run on an emulator or physical device (API 26+ recommended)  

---

## 📱 Application Features

### Features
- **Real-time Weather Data**: Fetches current weather from OpenWeatherMap API
- **Secure API Key**: Managed via `BuildConfig` for better security
- **Temperature Display**: Large, easy-to-read current temperature
- **Weather Details**: Humidity, wind speed, feels-like temperature, min/max temperatures
- **City Search**: Search weather for any city worldwide
- **Refresh Functionality**: Update weather data on demand
- **Loading & Error States**: User-friendly feedback during data loading and errors

### Settings Screen
- **Temperature Unit Toggle**: Switch between Celsius (metric) and Fahrenheit (imperial)
- **App Information**: Display version and API attribution
- **Persistent Preferences**: Settings applied across session (can be extended with DataStore)

## Composables

### UtilityApp()
- Main scaffold with bottom navigation (Weather & Settings tabs)
- Manages tab state and content switching
- Uses Material Design 3 NavigationBar

### WeatherScreen()
- Displays weather information and search functionality
- Observes ViewModel state using collectAsStateWithLifecycle()
- Shows loading spinner, error messages, or weather card based on UI state
- Includes city search input and refresh button

### WeatherCard()
- Displays formatted weather information in an attractive card
- Shows current temperature prominently
- Organized layout with weather details in rows
- Uses Material Design 3 styling and colors

### SettingsScreen()
- Temperature unit selection with toggle buttons
- Displays current unit selection
- Shows app information section
- Clean, organized layout following Material Design 3  

---

## 🏗️ Architecture & Key Concepts Covered

| Week | Concept                        | Implementation                          |
|------|--------------------------------|----------------------------------|
| 1    | Kotlin + Android Studio         | MainActivity.kt, all data/viewmodel classes |
| 2    | Jetpack Compose Layouts         | WeatherScreen(), SettingsScreen(), WeatherCard()   |
| 3    | Material Design 3               | Theme, NavigationBar, Button, TextField, Material colors/typography |
| 4    | App Architecture (ViewModel, DI, Repository) | WeatherViewModel, ServiceLocator, WeatherRepository pattern |
| 5    | Web APIs using Retrofit         | WeatherApi.kt, OpenWeatherMap API integration, Moshi JSON parsing |

### Architecture Layers

**Data Layer** (`/data`)
- `WeatherData.kt`: Data classes and UI models
- `WeatherApi.kt`: Retrofit API interface
- `WeatherRepository.kt`: Repository pattern for data abstraction

**ViewModel Layer** (`/viewmodel`)
- `WeatherViewModel.kt`: State management with StateFlow, Coroutines, UI state sealed class

**Dependency Injection** (`/di`)
- `ServiceLocator.kt`: Simple service locator pattern for object creation

**UI Layer** (`/ui`)
- `WeatherScreen.kt`: Main weather display screen
- `SettingsScreen.kt`: User preferences screen
- `WeatherComponents.kt`: Reusable UI components
- `theme/`: Material Design 3 theme configuration

## 🚀 Future Enhancement Ideas

### Immediate Improvements
- [ ] Persistent settings using DataStore
- [ ] Unit tests for Repository and ViewModel
- [ ] GPS-based location detection
- [ ] Multi-day weather forecast
- [ ] Recently searched cities history

### Technical Improvements
- [ ] Implement Hilt for dependency injection
- [ ] Add comprehensive logging
- [ ] Performance profiling and optimization
- [ ] Offline caching of weather data
- [ ] Accessibility improvements  

## 📋 Project Structure

```
app/src/main/
├── AndroidManifest.xml
├── java/au/edu/jcu/cp3406_cp5307_utilityappstartertemplate/
│   ├── MainActivity.kt (App entry point)
│   ├── data/
│   │   ├── WeatherData.kt (Data models)
│   │   ├── WeatherApi.kt (Retrofit interface)
│   │   └── WeatherRepository.kt (Repository pattern)
│   ├── viewmodel/
│   │   └── WeatherViewModel.kt (State management)
│   ├── di/
│   │   └── ServiceLocator.kt (Dependency injection)
│   └── ui/
│       ├── WeatherScreen.kt (Weather display)
│       ├── SettingsScreen.kt (Settings display)
│       ├── WeatherComponents.kt (Reusable components)
│       └── theme/ (App theme configuration)
└── res/ (Resources: drawable, values, xml)
```

## 🛠️ Technology Stack

- **Language**: Kotlin 2.0.21
- **UI Framework**: Jetpack Compose with Material Design 3
- **Networking**: Retrofit 2 + OkHttp 3
- **JSON Parsing**: Moshi
- **Concurrency**: Kotlin Coroutines
- **Architecture**: MVVM + Repository Pattern
- **Minimum SDK**: API 24 (Android 7.0)
- **Target SDK**: API 36

## 📚 License
This app is developed for educational use in CP3406 Mobile Computing.

---

**For detailed implementation details, see SELF_REFLECTION.md for architectural decisions and learning outcomes.**
