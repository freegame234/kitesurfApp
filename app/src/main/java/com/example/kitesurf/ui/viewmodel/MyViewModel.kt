package com.example.kitesurf.ui.viewmodel

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open // Annotate with @HiltViewModel for Hilt to know how to create it
class MyViewModel @Inject constructor(
    // Inject dependencies here if needed
    // مثلاً: private val repository: MyRepository
) : ViewModel() {

    private val _data = MutableStateFlow("Chargement...") // MutableStateFlow for mutable state
    open val data: State<String> = _data as State<String> // Expose as immutable StateFlow

    init {
        // Simulate loading some data when the ViewModel is created
        viewModelScope.launch {
            delay(2000) // Simulate network delay
            _data.value = "Données chargées avec succées !"
        }
    }

}