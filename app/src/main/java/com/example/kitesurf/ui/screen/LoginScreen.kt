package com.example.kitesurf.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.kitesurf.R
import com.example.kitesurf.domaine.model.UserRequest
import com.example.kitesurf.network.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary, // Bleu foncé
                        MaterialTheme.colorScheme.secondary // Bleu clair
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.kitesurf_logo), // Image dans drawable/
                contentDescription = "Icône de l'application",
                modifier = Modifier
                    .size(160.dp)
                    .padding(top = 40.dp,bottom = 16.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Connexion",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Mot de passe") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (message.isNotEmpty()) {
                        Text(
                            text = message,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Button(
                        onClick = {
                            val user = UserRequest(email, password)
                            CoroutineScope(Dispatchers.IO).launch {
                                try {
                                    val response = RetrofitInstance.api.login(user)
                                    withContext(Dispatchers.Main) {
                                        if (response.isSuccessful) {
                                            val responseBody = response.body()
                                            if (responseBody?.message != null) {
                                                message = ""
                                                navController.navigate("home_screen") {
                                                    popUpTo("login_screen") { inclusive = true }
                                                }
                                            } else {
                                                message = responseBody?.error ?: "Email ou mot de passe incorrect"
                                            }
                                        } else {
                                            message = "Email ou mot de passe incorrect"
                                        }
                                    }
                                } catch (e: Exception) {
                                    withContext(Dispatchers.Main) {
                                        message = "Erreur réseau : ${e.localizedMessage ?: "inconnue"}"
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Connexion")
                    }

                    TextButton(
                        onClick = { navController.navigate("register_screen") },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Créer un compte")
                    }
                }
            }
        }
    }
}