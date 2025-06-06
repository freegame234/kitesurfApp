package com.example.kitesurf.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.kitesurf.domaine.model.Classement
import com.example.kitesurf.data.datasource.ApiService
import com.example.kitesurf.network.RetrofitInstance

class ClassementViewModel : ViewModel() {

    private val _classement = MutableStateFlow<List<Classement>>(emptyList())
    val classement: StateFlow<List<Classement>> = _classement

    fun fetchClassement(competitionId: Int) {
        viewModelScope.launch {
            try {
                Log.d("ClassementViewModel", "Chargement classement pour compétition ID: $competitionId")
                val response = RetrofitInstance.api.getClassement(competitionId)
                _classement.value = response
            } catch (e: Exception) {
                Log.e("ClassementViewModel", "Erreur chargement classement", e)
            }
        }
    }
}
