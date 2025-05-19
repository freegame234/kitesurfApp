package com.example.kitesurf.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.kitesurf.domaine.model.Competition
import com.example.kitesurf.data.datasource.ApiService


class CompetitionViewModel : ViewModel() {

    private val _competitions = MutableStateFlow<List<Competition>>(emptyList())
    val competitions: StateFlow<List<Competition>> = _competitions

    fun fetchCompetitions() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCompetitions()
                _competitions.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
