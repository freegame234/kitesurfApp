package com.example.kitesurf.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitesurf.domaine.model.WeatherResponse
import com.example.kitesurf.network.RetrofitInstance.weatherApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val _weather = MutableStateFlow<WeatherResponse?>(null)
    val weather = _weather.asStateFlow()

    fun loadWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            try {
                val result = weatherApi.getWeatherByLocation(lat, lon, "72af36670b94fe10e6ae28f3bd896425")
                _weather.value = result
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Erreur météo", e)
            }
        }
    }
}
