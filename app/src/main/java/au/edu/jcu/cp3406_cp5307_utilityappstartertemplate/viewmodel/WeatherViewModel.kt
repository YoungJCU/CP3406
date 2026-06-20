package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.WeatherRepository
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.WeatherUIModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * UI State for Weather Screen
 */
sealed class WeatherUiState {
    data object Loading : WeatherUiState()
    data class Success(val weather: WeatherUIModel) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}

/**
 * ViewModel for Weather functionality
 * Manages weather data fetching and UI state
 */
class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState

    private val _currentCity = MutableStateFlow<String>("Brisbane")
    val currentCity: StateFlow<String> = _currentCity

    private val _temperatureUnit = MutableStateFlow<String>("metric")
    val temperatureUnit: StateFlow<String> = _temperatureUnit

    init {
        // Load initial weather
        fetchWeather(_currentCity.value)
    }

    /**
     * Fetch weather for a given city
     */
    fun fetchWeather(cityName: String) {
        _currentCity.value = cityName
        _uiState.value = WeatherUiState.Loading

        viewModelScope.launch {
            try {
                val weather = repository.getWeatherByCity(
                    cityName = cityName,
                    units = _temperatureUnit.value
                )
                _uiState.value = WeatherUiState.Success(weather)
            } catch (e: Exception) {
                _uiState.value = WeatherUiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    /**
     * Update temperature unit (metric/imperial)
     */
    fun setTemperatureUnit(unit: String) {
        _temperatureUnit.value = unit
        // Refresh weather with new unit
        fetchWeather(_currentCity.value)
    }

    /**
     * Refresh current weather
     */
    fun refreshWeather() {
        fetchWeather(_currentCity.value)
    }
}

