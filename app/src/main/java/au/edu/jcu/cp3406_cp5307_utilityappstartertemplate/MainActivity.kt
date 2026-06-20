package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.di.ServiceLocator
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.theme.CP3406_CP5603UtilityAppStarterTemplateTheme
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.viewmodel.WeatherUiState
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.viewmodel.WeatherViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CP3406_CP5603UtilityAppStarterTemplateTheme {
                UtilityApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UtilityAppPreview() {
    CP3406_CP5603UtilityAppStarterTemplateTheme {
        UtilityApp()
    }
}

@Composable
fun UtilityApp() {
    val viewModel = remember { ServiceLocator.createWeatherViewModel() }
    var selectedTab by remember { mutableStateOf("Utility") }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Cloud, contentDescription = "Weather") },
                    label = { Text("Weather") },
                    selected = selectedTab == "Utility",
                    onClick = { selectedTab = "Utility" }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    selected = selectedTab == "Settings",
                    onClick = { selectedTab = "Settings" }
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                "Utility" -> WeatherScreen(viewModel)
                "Settings" -> SettingsScreen(viewModel)
            }
        }
    }
}

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentCity by viewModel.currentCity.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Weather Forecast",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // City input
        var inputCity by remember { mutableStateOf(currentCity) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = inputCity,
                onValueChange = { inputCity = it },
                label = { Text("Enter city name") },
                modifier = Modifier.weight(1f)
            )
            Button(onClick = { viewModel.fetchWeather(inputCity) }) {
                Text("Search")
            }
        }

        // Weather display
        when (uiState) {
            is WeatherUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is WeatherUiState.Success -> {
                val weather = (uiState as WeatherUiState.Success).weather
                WeatherCard(weather)
            }
            is WeatherUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.errorContainer)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        (uiState as WeatherUiState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        Button(
            onClick = { viewModel.refreshWeather() },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Refresh")
        }
    }
}

@Composable
fun WeatherCard(weather: au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.data.WeatherUIModel) {
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

@Composable
fun WeatherDetail(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, style = MaterialTheme.typography.labelSmall)
        Text(value, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun SettingsScreen(viewModel: WeatherViewModel) {
    val temperatureUnit by viewModel.temperatureUnit.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineLarge)

        Text(
            "Temperature Unit",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        // Temperature unit buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { viewModel.setTemperatureUnit("metric") },
                modifier = Modifier.weight(1f),
                enabled = temperatureUnit != "metric"
            ) {
                Text("Celsius (°C)")
            }
            Button(
                onClick = { viewModel.setTemperatureUnit("imperial") },
                modifier = Modifier.weight(1f),
                enabled = temperatureUnit != "imperial"
            ) {
                Text("Fahrenheit (°F)")
            }
        }

        Text(
            "Current Unit: ${if (temperatureUnit == "metric") "Celsius" else "Fahrenheit"}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        // App info
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.surfaceContainer,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("About", style = MaterialTheme.typography.titleSmall)
            Text(
                "Weather Forecast App v1.0",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                "Powered by OpenWeatherMap API",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}