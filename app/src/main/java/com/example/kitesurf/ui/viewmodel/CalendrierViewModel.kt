package com.example.kitesurf.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitesurf.domaine.model.Calendrier
import com.example.kitesurf.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CalendrierViewModel : ViewModel() {

    private val _calendrier = MutableStateFlow<List<Calendrier>>(emptyList())
    val calendrier: StateFlow<List<Calendrier>> = _calendrier

    fun fetchCalendrier(competitionId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCalendrier(competitionId)
                _calendrier.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
