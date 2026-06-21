package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.WeatherUIModel

/**
 * Card displaying detailed weather information
 */
@Composable
fun WeatherCard(weather: WeatherUIModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                shape = MaterialTheme.shapes.large
            )
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // City and Country
        Text(
            "${weather.cityName}, ${weather.country}",
            style = MaterialTheme.typography.headlineSmall
        )

        // Current Temperature (large)
        Text(
            "${weather.temperature}°C",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary
        )

        // Weather condition
        Text(
            weather.condition,
            style = MaterialTheme.typography.titleMedium
        )

        // Additional details in a grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            WeatherDetail("Feels Like", "${weather.feelsLike}°C")
            WeatherDetail("Humidity", "${weather.humidity}%")
            WeatherDetail("Wind", "%.1f m/s".format(weather.windSpeed))
        }

        // Temperature range
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            WeatherDetail("Min", "${weather.tempMin}°C")
            WeatherDetail("Max", "${weather.tempMax}°C")
        }
    }
}

/**
 * Small component for individual weather details
 */
@Composable
fun WeatherDetail(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, style = MaterialTheme.typography.labelSmall)
        Text(value, style = MaterialTheme.typography.labelLarge)
    }
}
