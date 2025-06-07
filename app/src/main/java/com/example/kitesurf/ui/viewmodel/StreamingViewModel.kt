package com.example.kitesurf.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import android.util.Log
import retrofit2.Response
import com.example.kitesurf.network.RetrofitInstance
import com.example.kitesurf.domaine.model.StreamResponse

class StreamingViewModel : ViewModel() {
    var streamUrl by mutableStateOf<String?>(null)
        private set

    init {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getStreamingUrl()
                if (response.isSuccessful) {
                    val url = response.body()?.url
                    if (url != null) {
                        streamUrl = url  // ✅ ici c'était "streamingUrl", ça doit être "streamUrl"
                    }
                } else {
                    Log.e("StreamingViewModel", "Erreur HTTP: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("StreamingViewModel", "Erreur: ${e.message}")
            }
        }
    }
}