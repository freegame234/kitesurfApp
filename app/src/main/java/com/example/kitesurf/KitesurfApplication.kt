package com.example.kitesurf

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.example.kitesurf.ui.screen.NotificationHelper

@HiltAndroidApp
class KitesurfApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Crée le canal pour les notifications de compétitions
        NotificationHelper.createNotificationChannel(this)
    }

}