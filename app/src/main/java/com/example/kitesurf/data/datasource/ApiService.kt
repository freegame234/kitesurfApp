package com.example.kitesurf.data.datasource

import com.example.kitesurf.domaine.model.Calendrier
import com.example.kitesurf.domaine.model.Classement
import com.example.kitesurf.domaine.model.Competition
import com.example.kitesurf.domaine.model.Kitesurfer
import com.example.kitesurf.domaine.model.Position
import com.example.kitesurf.domaine.model.UserResponse
import com.example.kitesurf.domaine.model.UserRequest
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.kitesurf.domaine.model.Video
import com.example.kitesurf.domaine.model.WeatherResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {

    @GET("classement/{competitionId}")
    suspend fun getClassement(@Path("competitionId") competitionId: Int): List<Classement>

    @GET("calendrier/{competitionId}")
    suspend fun getCalendrier(@Path("competitionId") competitionId: Int): List<Calendrier>

    @GET("competition")
    suspend fun getCompetitions(): List<Competition>

    @GET("kitesurfer")
    suspend fun getKitesurfers(): List<Kitesurfer>

    @GET("weather")
    suspend fun getWeatherByLocation(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "fr"
    ): WeatherResponse

    @GET("videos")
    suspend fun getVideos(): List<Video>


    @GET("positions")
    suspend fun getPositions(): List<Position>

    @POST("/register")
    suspend fun register(@Body request: UserRequest): retrofit2.Response<UserResponse>

    @POST("/login")
    suspend fun login(@Body request: UserRequest): retrofit2.Response<UserResponse>

}
