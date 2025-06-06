package com.example.kitesurf.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.kitesurf.ui.theme.BlueOceanDark
import com.example.kitesurf.ui.theme.BlueOceanLight

@Composable
fun BottomNavigationBar(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs: List<Pair<String, ImageVector>> = listOf(
        "Compétition" to Icons.Filled.EmojiEvents,
        "Classement" to Icons.Filled.Leaderboard,
        "Calendrier" to Icons.Filled.Event,
        "Météo" to Icons.Filled.Thermostat,
        "Localisation" to Icons.Filled.LocationOn
    )

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = BlueOceanLight,
        contentColor = Color.Black
    ) {
        tabs.forEachIndexed { index, (title, icon) ->
            NavigationBarItem(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                label = { Text(title) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = if (selectedTabIndex == index) BlueOceanDark else Color.Gray
                    )
                }
            )
        }
    }
}