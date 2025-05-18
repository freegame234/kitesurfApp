package com.example.kitesurf.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitesurf.domaine.model.Kitesurfer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class KitesurferViewModel : ViewModel() {

    private val _kitesurfers = MutableStateFlow<List<Kitesurfer>>(emptyList())
    val kitesurfers: StateFlow<List<Kitesurfer>> = _kitesurfers

    fun fetchKitesurfers() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getKitesurfers()
                _kitesurfers.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
