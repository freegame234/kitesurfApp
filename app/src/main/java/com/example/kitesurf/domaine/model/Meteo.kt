package com.example.kitesurf.domaine.model

data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>,
    val name: String,
    val dt: Long
)

data class Main(
    val temp: Float,
    val humidity: Int
)

data class Weather(
    val description: String,
    val icon: String
)