package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.viewmodel.WeatherViewModel

/**
 * Settings screen to configure app preferences
 */
@Composable
fun SettingsScreen(viewModel: WeatherViewModel) {
    val temperatureUnit by viewModel.temperatureUnit.collectAsStateWithLifecycle()
    var notificationsEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineLarge)

        // Account Section
        Text("Account", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        ListItem(
            headlineContent = { Text("Profile") },
            leadingContent = { Icon(Icons.Default.Person, contentDescription = null) }
        )
        HorizontalDivider()

        // Preferences Section
        Text("Preferences", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        
        Text(
            "Temperature Unit",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(top = 8.dp)
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

        ListItem(
            headlineContent = { Text("Weather Notifications") },
            leadingContent = { Icon(Icons.Default.Notifications, contentDescription = null) },
            trailingContent = {
                Switch(
                    checked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it }
                )
            }
        )
        
        ListItem(
            headlineContent = { Text("Share Weather Report") },
            leadingContent = { Icon(Icons.Default.Share, contentDescription = null) }
        )

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()

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
                "Weather Forecast App v1.2",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                "Built for CP3406 Assessment",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
