package com.example.kitesurf.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitesurf.domaine.model.Video
import com.example.kitesurf.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideoViewModel : ViewModel() {

    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> = _videos

    fun fetchVideos() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getVideos()
                _videos.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
