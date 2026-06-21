package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data

import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.BuildConfig

/**
 * Repository pattern for weather data
 * Handles all data operations and abstracts the data source from the UI
 */
class WeatherRepository(private val weatherApi: WeatherApi) {

    private val apiKey = BuildConfig.OPENWEATHER_API_KEY  // Use API key from BuildConfig for better security

    /**
     * Fetch current weather for a given city
     * @param cityName Name of the city
     * @param units Temperature units: "metric" for Celsius, "imperial" for Fahrenheit
     * @return WeatherUIModel with processed weather data
     */
    suspend fun getWeatherByCity(cityName: String, units: String = "metric"): WeatherUIModel {
        return try {
            val response = weatherApi.getCurrentWeather(
                cityName = cityName,
                units = units,
                apiKey = apiKey
            )
            response.toUIModel()
        } catch (e: Exception) {
            // Return a default/error model
            throw WeatherException("Failed to fetch weather: ${e.message}", e)
        }
    }

    /**
     * Fetch weather by geographic coordinates
     * @param latitude Latitude of the location
     * @param longitude Longitude of the location
     * @param units Temperature units
     * @return WeatherUIModel with processed weather data
     */
    suspend fun getWeatherByCoordinates(
        latitude: Double,
        longitude: Double,
        units: String = "metric"
    ): WeatherUIModel {
        return try {
            val response = weatherApi.getWeatherByCoordinates(
                latitude = latitude,
                longitude = longitude,
                units = units,
                apiKey = apiKey
            )
            response.toUIModel()
        } catch (e: Exception) {
            throw WeatherException("Failed to fetch weather by coordinates: ${e.message}", e)
        }
    }

    /**
     * Convert API response to UI model
     */
    private fun WeatherResponse.toUIModel(): WeatherUIModel {
        return WeatherUIModel(
            cityName = this.name,
            country = this.sys.country,
            temperature = this.main.temperature.toInt(),
            feelsLike = this.main.feelsLike.toInt(),
            condition = this.weather.firstOrNull()?.description?.replaceFirstChar { it.uppercase() } ?: "N/A",
            humidity = this.main.humidity,
            windSpeed = this.wind.speed,
            tempMin = this.main.tempMin.toInt(),
            tempMax = this.main.tempMax.toInt(),
            icon = this.weather.firstOrNull()?.icon ?: "01d"
        )
    }
}

/**
 * Custom exception for weather-related errors
 */
class WeatherException(message: String, cause: Throwable? = null) : Exception(message, cause)

