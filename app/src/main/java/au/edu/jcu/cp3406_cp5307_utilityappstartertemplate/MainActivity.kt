package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.CryptoViewModel
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.home_view.composables.CoinCard
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui.theme.CP3406_CP5603UtilityAppStarterTemplateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { CP3406_CP5603UtilityAppStarterTemplateTheme { UtilityApp() } }
    }
}

@Preview(showBackground = true)
@Composable
fun UtilityAppPreview() {
    CP3406_CP5603UtilityAppStarterTemplateTheme { UtilityApp() }
}

@Composable
fun UtilityApp() {
    var selectedTab by remember { mutableStateOf("Utility") }
    val viewModel: CryptoViewModel = viewModel()

    Scaffold(
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                            selected = selectedTab == "Home",
                            onClick = { selectedTab = "Home" },
                            icon = { Icon(Icons.Default.Home, contentDescription = null) },
                            label = { Text("Home") }
                    )

                    NavigationBarItem(
                            selected = selectedTab == "Settings",
                            onClick = { selectedTab = "Settings" },
                            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                            label = { Text("Settings") }
                    )
                }
            }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                "Home" -> UtilityScreen(viewModel)
                "Settings" -> SettingsScreen(viewModel)
            }
        }
    }
}

@Composable
fun UtilityScreen(viewModel: CryptoViewModel) {

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "CryptoTracker", style = MaterialTheme.typography.headlineMedium)

        Text(text = "Live cryptocurrency prices", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.padding(8.dp))

        if (viewModel.isLoading) {

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(viewModel.coins) { coin ->
                    CoinCard(
                            name = coin.name,
                            price = coin.currentPrice,
                            currency = viewModel.currency,
                            change24h = coin.change24h,
                            change7d = coin.change7d,
                            change30d = coin.change30d,
                            change1y = coin.change1y
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(viewModel: CryptoViewModel) {

    Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Settings", style = MaterialTheme.typography.headlineMedium)

        Text(
                "Current Currency: ${viewModel.currency.uppercase()}",
                style = MaterialTheme.typography.titleMedium
        )

        Button(onClick = { viewModel.updateCurrency("usd") }) { Text("🇺🇸 USD") }

        Button(onClick = { viewModel.updateCurrency("eur") }) { Text("🇪🇺 EUR") }

        Button(onClick = { viewModel.updateCurrency("aud") }) { Text("🇦🇺 AUD") }

        Button(onClick = { viewModel.updateCurrency("sgd") }) { Text("🇸🇬 SGD") }
    }
}
