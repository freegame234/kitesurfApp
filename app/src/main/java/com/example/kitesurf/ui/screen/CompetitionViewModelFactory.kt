package com.example.kitesurf.ui.screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kitesurf.ui.viewmodel.CompetitionViewModel

class CompetitionViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CompetitionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CompetitionViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
