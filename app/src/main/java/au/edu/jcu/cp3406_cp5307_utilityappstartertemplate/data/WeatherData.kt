package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Data class representing the complete weather response from OpenWeatherMap API
 */
@JsonClass(generateAdapter = true)
data class WeatherResponse(
    @Json(name = "coord")
    val coord: Coordinates,
    @Json(name = "weather")
    val weather: List<Weather>,
    @Json(name = "main")
    val main: MainWeatherData,
    @Json(name = "visibility")
    val visibility: Int,
    @Json(name = "wind")
    val wind: Wind,
    @Json(name = "clouds")
    val clouds: Clouds,
    @Json(name = "dt")
    val dt: Long,
    @Json(name = "sys")
    val sys: SystemData,
    @Json(name = "timezone")
    val timezone: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "cod")
    val cod: Int
)

/**
 * Coordinates of the location
 */
@JsonClass(generateAdapter = true)
data class Coordinates(
    @Json(name = "lon")
    val longitude: Double,
    @Json(name = "lat")
    val latitude: Double
)

/**
 * Weather condition information
 */
@JsonClass(generateAdapter = true)
data class Weather(
    @Json(name = "id")
    val id: Int,
    @Json(name = "main")
    val main: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "icon")
    val icon: String
)

/**
 * Main weather data including temperature, pressure, humidity
 */
@JsonClass(generateAdapter = true)
data class MainWeatherData(
    @Json(name = "temp")
    val temperature: Double,
    @Json(name = "feels_like")
    val feelsLike: Double,
    @Json(name = "temp_min")
    val tempMin: Double,
    @Json(name = "temp_max")
    val tempMax: Double,
    @Json(name = "pressure")
    val pressure: Int,
    @Json(name = "humidity")
    val humidity: Int
)

/**
 * Wind data
 */
@JsonClass(generateAdapter = true)
data class Wind(
    @Json(name = "speed")
    val speed: Double,
    @Json(name = "deg")
    val degrees: Int,
    @Json(name = "gust")
    val gust: Double? = null
)

/**
 * Cloud coverage information
 */
@JsonClass(generateAdapter = true)
data class Clouds(
    @Json(name = "all")
    val all: Int
)

/**
 * System data including country, sunrise, sunset
 */
@JsonClass(generateAdapter = true)
data class SystemData(
    @Json(name = "country")
    val country: String,
    @Json(name = "sunrise")
    val sunrise: Long,
    @Json(name = "sunset")
    val sunset: Long
)

/**
 * Simplified UI model for weather display
 */
data class WeatherUIModel(
    val cityName: String,
    val country: String,
    val temperature: Int,
    val feelsLike: Int,
    val condition: String,
    val humidity: Int,
    val windSpeed: Double,
    val tempMin: Int,
    val tempMax: Int,
    val icon: String
)

