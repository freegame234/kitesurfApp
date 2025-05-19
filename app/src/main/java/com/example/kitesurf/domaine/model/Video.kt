package com.example.kitesurf.domaine.model

data class Video(
    val id: Int,
    val competition_id: Int?,
    val url: String,
    val date_upload: String?,
    val description: String?
)