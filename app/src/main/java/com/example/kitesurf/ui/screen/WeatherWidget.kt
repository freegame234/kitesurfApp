package com.example.kitesurf.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.kitesurf.domaine.model.WeatherResponse
import com.example.kitesurf.ui.theme.BlueOceanDark
import com.example.kitesurf.ui.theme.BlueOceanLight
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WeatherWidget(weather: WeatherResponse?) {
    if (weather != null) {
        // Convertir le timestamp UNIX (en secondes) en date/heure lisible
        val dateTime = remember(weather.dt) {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            sdf.format(Date(weather.dt * 1000L))
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = BlueOceanLight),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val iconUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}@2x.png"
                AsyncImage(
                    model = iconUrl,
                    contentDescription = "Weather Icon",
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "🌍 ${weather.name}",
                    style = MaterialTheme.typography.titleLarge,
                    color = BlueOceanDark
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "🌡 ${weather.main.temp} °C",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "☁️ ${weather.weather[0].description.replaceFirstChar { it.uppercase() }}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "📅 Observé le : $dateTime",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Chargement météo…", color = Color.Gray)
        }
    }
}