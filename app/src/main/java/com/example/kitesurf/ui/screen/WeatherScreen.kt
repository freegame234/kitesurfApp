package com.example.kitesurf.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.kitesurf.getLastLocation
import com.example.kitesurf.ui.viewmodel.WeatherViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.kitesurf.ui.theme.BlueOceanDark
import com.example.kitesurf.ui.theme.BlueOceanLight

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val context = LocalContext.current
    val weather by viewModel.weather.collectAsState()

    // Récupération de la localisation une fois
    LaunchedEffect(Unit) {
        getLastLocation(context) { lat, lon ->
            viewModel.loadWeather(lat, lon)
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        color = BlueOceanLight
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (weather != null) {
                WeatherWidget(weather!!)
            } else {
                CircularProgressIndicator(color = BlueOceanDark)
            }
        }
    }
}