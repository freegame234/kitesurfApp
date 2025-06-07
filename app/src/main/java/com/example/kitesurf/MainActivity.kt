package com.example.kitesurf

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.kitesurf.session.getUserId
import com.example.kitesurf.ui.navigation.AppNavigation
import com.example.kitesurf.ui.theme.kitesurfTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(this, "✅ Localisation activée", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "❌ Permission de localisation refusée", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkAndRequestLocationPermission()

        val cameFromNotification = intent.getStringExtra("navigate_to") == "competition"

        lifecycleScope.launch {
            val userId = getUserId(applicationContext)
            val startDestination = if (userId != null) "home_screen" else "login_screen"

            setContent {
                kitesurfTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        AppNavigation(startDestination = startDestination)
                    }
                }
            }
        }

        if (!cameFromNotification) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    NotificationHelper.sendNotification(this, "Nouvelle compétition", "Une compétition est disponible !")
                } else {
                    requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
                }
            } else {
                NotificationHelper.sendNotification(this, "Nouvelle compétition", "Une compétition est disponible !")
            }
        }
    }

    private fun checkAndRequestLocationPermission() {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            locationPermissionLauncher.launch(permission)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            NotificationHelper.sendNotification(this, "Nouvelle compétition", "Une compétition est disponible !")
        }
    }

}