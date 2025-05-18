package com.example.kitesurf.domaine.model

import com.google.gson.annotations.SerializedName

data class Meteo(
    @SerializedName("date_heure")
    val date: String,

    @SerializedName("temperature")
    val temperature: Double,

    @SerializedName("vent_vitesse")
    val ventVitesse: Double,

    @SerializedName("vent_direction")
    val ventDirection: String,

    @SerializedName("condition_meteo")
    val condition: String
)