package com.example.kitesurf.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.kitesurf.getLastLocation
import com.example.kitesurf.ui.viewmodel.WeatherViewModel

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val context = LocalContext.current
    val weather by viewModel.weather.collectAsState()

    LaunchedEffect(Unit) {
        getLastLocation(context) { lat, lon ->
            viewModel.loadWeather(lat, lon)
        }
    }
    WeatherWidget(weather)
}