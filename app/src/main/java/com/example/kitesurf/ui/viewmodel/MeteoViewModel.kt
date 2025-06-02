package com.example.kitesurf.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitesurf.domaine.model.Meteo
import com.example.kitesurf.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MeteoViewModel : ViewModel() {

    private val _meteo = MutableStateFlow<List<Meteo>>(emptyList())
    val meteo: StateFlow<List<Meteo>> = _meteo

    fun fetchMeteo(start: String? = null, end: String? = null) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getMeteo(start, end)
                _meteo.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
