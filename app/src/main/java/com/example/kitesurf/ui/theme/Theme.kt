package com.example.kitesurf.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color // Importez Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// --- Définition des couleurs pour le thème océan ---
// Vous pouvez déplacer ces définitions dans un fichier Color.kt
val BlueOceanLight = Color(0xFFB3E5FC) // Bleu clair
val BlueOceanMedium = Color(0xFF81D4FA) // Bleu moyen
val BlueOceanDark = Color(0xFF0277BD)  // Bleu foncé
val WhiteSand = Color(0xFFFFF9C4)      // Couleur sable clair
val DarkBlueGray = Color(0xFF1C1B1F)   // Couleur foncée pour le texte/fond
val LightSurface = Color(0xFFFFFBFE)  // Couleur claire pour les surfaces

// --- Schéma de couleurs sombres (Dark Color Scheme) adapté au thème océan ---
private val DarkColorScheme = darkColorScheme(
    primary = BlueOceanLight,      // Couleur principale (souvent utilisée pour les éléments interactifs)
    secondary = BlueOceanMedium,   // Couleur secondaire
    tertiary = BlueOceanDark,      // Couleur tertiaire
    background = DarkBlueGray,     // Couleur de fond sombre
    surface = DarkBlueGray,        // Couleur de surface sombre
    onPrimary = DarkBlueGray,      // Couleur du contenu sur primary
    onSecondary = Color.White,     // Couleur du contenu sur secondary
    onTertiary = Color.White,      // Couleur du contenu sur tertiary
    onBackground = LightSurface,   // Couleur du contenu sur background sombre
    onSurface = LightSurface       // Couleur du contenu sur surface sombre
)

// --- Schéma de couleurs claires (Light Color Scheme) adapté au thème océan ---
private val LightColorScheme = lightColorScheme(
    primary = BlueOceanDark,       // Couleur principale
    secondary = BlueOceanMedium,   // Couleur secondaire
    tertiary = BlueOceanLight,     // Couleur tertiaire
    background = LightSurface,     // Couleur de fond claire
    surface = LightSurface,        // Couleur de surface claire
    onPrimary = WhiteSand,         // Couleur du contenu sur primary (par ex. texte du bouton)
    onSecondary = DarkBlueGray,    // Couleur du contenu sur secondary
    onTertiary = DarkBlueGray,     // Couleur du contenu sur tertiary
    onBackground = DarkBlueGray,   // Couleur du contenu sur background clair
    onSurface = DarkBlueGray       // Couleur du contenu sur surface claire

    /* Autres couleurs par défaut à remplacer si nécessaire
    error = Color(0xFFB00020),
    onError = Color.White,
    */
)

@Composable
fun kitesurfTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // La couleur dynamique est disponible sur Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Utilise la couleur de fond du schéma de couleurs pour la barre de statut
            window.statusBarColor = colorScheme.background.toArgb()
            // S'assurer que les icônes de la barre de statut sont visibles
            // Si le thème est clair, les icônes doivent être foncées (AppearanceLightStatusBars = true)
            // Si le thème est sombre, les icônes doivent être claires (AppearanceLightStatusBars = false)
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Suppose que Typography est défini dans ui.theme
        content = content
    )
}