package com.example.kitesurf.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import android.util.Log
import com.example.kitesurf.domaine.model.Position
import com.google.android.gms.maps.CameraUpdateFactory

@Composable
fun LocalisationTabScreen() {
    var positions by remember { mutableStateOf<List<Position>>(emptyList()) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(48.0, 2.0), 5f)
    }

    LaunchedEffect(Unit) {
        try {
            positions = RetrofitInstance.api.getPositions()
            Log.d("LocalisationTab", "Positions reçues: ${positions.size}")
            positions.forEach {
                Log.d("LocalisationTab", "Position: ${it.latitude}, ${it.longitude}")
            }
        } catch (e: Exception) {
            Log.e("LocalisationTab", "Erreur chargement positions", e)
        }
    }

    LaunchedEffect(positions) {
        if (positions.isNotEmpty()) {
            val avgLat = positions.map { it.latitude }.average()
            val avgLng = positions.map { it.longitude }.average()
            val update = CameraUpdateFactory.newLatLngZoom(LatLng(avgLat, avgLng), 12f)
            cameraPositionState.animate(update)
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
    ) {
        positions.filter { it.latitude != null && it.longitude != null }.forEach { position ->
            Marker(
                state = MarkerState(position = LatLng(position.latitude, position.longitude)),
                title = "Kitesurfer #${position.kitesurfer_id}",
                snippet = "Dernière mise à jour: ${position.timestamp}"
            )
        }
    }
}