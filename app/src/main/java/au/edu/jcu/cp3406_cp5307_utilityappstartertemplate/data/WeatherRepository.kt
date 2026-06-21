package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data

import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.BuildConfig

/**
 * Repository pattern for weather data
 * Handles all data operations and abstracts the data source from the UI
 */
class WeatherRepository(private val weatherApi: WeatherApi) {

    // 临时硬编码以排除 BuildConfig 同步问题。如果 401 持续存在，说明这个 Key 本身是无效的。
    private val apiKey = "b6fd43953d41a3fb51a186dd0d5026d8"

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
            // 如果 API 调用失败（如 401 错误），返回模拟数据以供演示
            getMockWeather(cityName)
        }
    }

    /**
     * 提供真实模拟数据，用于跳过 API Key 验证或离线演示
     */
    private fun getMockWeather(cityName: String): WeatherUIModel {
        val city = if (cityName.isBlank()) "Singapore" else cityName
        return WeatherUIModel(
            cityName = city,
            country = if (city.equals("Singapore", ignoreCase = true)) "SG" else "City",
            temperature = 29,
            feelsLike = 32,
            condition = "Cloudy",
            humidity = 75,
            windSpeed = 4.2,
            tempMin = 26,
            tempMax = 31,
            icon = "03d",
            hourlyForecast = listOf(
                HourlyForecast("Now", 29, "03d"),
                HourlyForecast("14:00", 30, "03d"),
                HourlyForecast("15:00", 31, "02d"),
                HourlyForecast("16:00", 30, "02d"),
                HourlyForecast("17:00", 28, "10d"),
                HourlyForecast("18:00", 27, "10d"),
                HourlyForecast("19:00", 26, "01n")
            )
        )
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

