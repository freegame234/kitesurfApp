package com.example.kitesurf.domaine.model

data class Calendrier(
    val id: Int,
    val competition_id: Int,
    val date_debut: String,
    val date_fin: String,
    val localisation: String
)

