package com.example.kitesurf.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kitesurf.ui.screen.HomeScreen
import com.example.kitesurf.ui.screen.VideoListScreen
import com.example.kitesurf.ui.screen.VideoPlayerScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home_screen") {
        // Écran d'accueil
        composable("home_screen") {
            HomeScreen(navController = navController)
        }

        // Écran lecteur vidéo (vidéos locales via API Flask)
        composable("video_list_screen") {
            VideoListScreen()
        }
    }
}
