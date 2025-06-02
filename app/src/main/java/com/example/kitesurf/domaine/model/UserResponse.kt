package com.example.kitesurf.domaine.model

data class UserResponse(
    val message: String,
    val user_id: Int? = null,
    val error: String? = null
)