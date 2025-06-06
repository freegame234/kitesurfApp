package com.example.kitesurf.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kitesurf.ui.screen.HomeScreen
import com.example.kitesurf.ui.screen.LoginScreen
import com.example.kitesurf.ui.screen.RegisterScreen
import com.example.kitesurf.ui.screen.VideoListScreen

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
    }
}
