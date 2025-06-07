package com.example.kitesurf.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
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
            val selected = selectedTabIndex == index

            NavigationBarItem(
                selected = selected,
                onClick = { onTabSelected(index) },
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        modifier = Modifier.size(if (selected) 35.dp else 24.dp),
                        tint = if (selected) BlueOceanDark else Color.Gray
                    )
                },
                label = {
                    if (selected) {
                        Text(
                            text = title,
                            maxLines = 1,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                },
                alwaysShowLabel = false
            )
        }
    }
}
