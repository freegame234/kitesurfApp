package com.example.kitesurf.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController // For navigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(
    navController: NavController // NavController is provided by the navigation component
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Streaming vidéo") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) { // Button to go back
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Voici le second écran !")

            Button(
                onClick = {
                    // You could navigate back to the home screen or elsewhere
                    navController.popBackStack() // Go back to the previous screen
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Retour")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SecondScreenPreview() {
    // Preview needs a dummy NavController
    // Text("Second Screen Preview (Dummy)") // A simple text preview
    // Or a more structured preview, but navigation won't work
}