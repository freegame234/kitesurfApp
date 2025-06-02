package com.example.kitesurf.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import coil.compose.AsyncImage
import com.example.kitesurf.domaine.model.WeatherResponse
import java.util.jar.Manifest

@Composable
fun WeatherWidget(weather: WeatherResponse?) {
    if (weather != null) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Ville : ${weather.name}")
            Text("Température : ${weather.main.temp} °C")
            Text("Condition : ${weather.weather[0].description}")
            val iconUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}@2x.png"
            AsyncImage(model = iconUrl, contentDescription = null)
        }
    } else {
        Text("Chargement météo…")
    }
}