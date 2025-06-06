package com.example.kitesurf.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.kitesurf.domaine.model.Competition
import com.example.kitesurf.network.RetrofitInstance
import com.example.kitesurf.NotificationHelper


class CompetitionViewModel(private val context: Context) : ViewModel() {
    private val _competitions = MutableStateFlow<List<Competition>>(emptyList())
    val competitions: StateFlow<List<Competition>> = _competitions

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private var previousCompetitions = emptyList<Competition>()

    init {
        NotificationHelper.createNotificationChannel(context)
    }

    fun fetchCompetitions() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCompetitions()
                val newComps = response.filter { newComp ->
                    previousCompetitions.none { it.id == newComp.id }
                }
                if (newComps.isNotEmpty()) {
                    NotificationHelper.sendNotification(
                        context,
                        "Nouvelle compétition ajoutée",
                        "Il y a ${newComps.size} nouvelle(s) compétition(s)."
                    )
                }
                previousCompetitions = response
                _competitions.value = response
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage ?: "Erreur inconnue"
            }
        }
    }
}
