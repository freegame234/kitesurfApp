package com.example.kitesurf.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.kitesurf.ui.screen.HomeScreen
import com.example.kitesurf.ui.screen.LoginScreen
import com.example.kitesurf.ui.screen.RegisterScreen
import com.example.kitesurf.ui.screen.StreamingScreen
import com.example.kitesurf.ui.screen.VideoListScreen
import com.example.kitesurf.ui.screen.VideoPlayerScreen


@Composable
fun AppNavigation(startDestination: String) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {

        composable("login_screen") {
            LoginScreen(navController = navController)
        }
        composable("register_screen") {
            RegisterScreen(navController = navController)
        }
        composable("home_screen") {
            HomeScreen(navController = navController)
        }
        composable("video_list_screen") {
            VideoListScreen(navController = navController)
        }
        composable("streaming_screen") {
            StreamingScreen(navController = navController)
        }
        composable(
            route = "video_player_screen/{videoUrl}",
            arguments = listOf(navArgument("videoUrl") { type = NavType.StringType })
        ) { backStackEntry ->
            val videoUrl = backStackEntry.arguments?.getString("videoUrl") ?: ""
            VideoPlayerScreen(videoUrl = Uri.decode(videoUrl))
        }
    }
}
