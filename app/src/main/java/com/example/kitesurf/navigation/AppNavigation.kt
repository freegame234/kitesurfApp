package com.example.kitesurf.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost // NavHost est le container pour navigation
import androidx.navigation.compose.composable // Pour définir les routes
import androidx.navigation.compose.rememberNavController // Pour se souvenir du NavController
import androidx.navigation.navArgument
import com.example.kitesurf.ui.screen.HomeScreen
import com.example.kitesurf.ui.screen.YouTubeStreamingScreen // Votre composable YouTubeStreamingScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController() // Créer et se souvenir du NavController

    NavHost(navController = navController, startDestination = "home_screen") { // Définir le NavHost
        composable("home_screen") { // Route pour l'écran d'accueil
            HomeScreen(navController = navController) // Passer le navController à l'écran
        }

        // Route spécifiquement pour les vidéos YouTube via ID pour l'écran basé sur WebView/IFrame API
        composable(
            route = "youtube_streaming_screen/{videoId}", // Le chemin avec l'argument videoId
            arguments = listOf( // Définir les arguments attendus
                navArgument("videoId") { // Le nom de l'argument doit correspondre au placeholder
                    type = NavType.StringType // Le type de l'argument est une chaîne de caractères (l'ID)
                    nullable = false // L'argument est obligatoire
                }
            )
        ) { backStackEntry ->
            // Extraire l'argument "videoId" de l'entrée de la pile de retour
            val videoId = backStackEntry.arguments?.getString("videoId")

            // S'assurer que l'ID n'est pas nul avant d'appeler l'écran de streaming YouTube
            if (videoId != null) {
                YouTubeStreamingScreen(videoId = videoId) // Appeler votre composable YouTubeStreamingScreen avec l'ID
            } else {
                // Gérer le cas où l'ID est manquant
                Text("Erreur : ID de la vidéo YouTube manquant")
            }
        }

        // Si vous avez d'autres écrans, définissez leurs composable ici
        // composable("autre_ecran") {
        //    AutreEcran(...)
        // }
    }
}