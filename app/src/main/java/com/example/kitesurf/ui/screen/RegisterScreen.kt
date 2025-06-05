package com.example.kitesurf.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kitesurf.R
import com.example.kitesurf.domaine.model.UserRequest
import com.example.kitesurf.network.RetrofitInstance
import kotlinx.coroutines.launch

fun isPasswordValid(password: String): Boolean {
    val passwordRegex = Regex("^(?=.*[A-Z])(?=.*[!@#\$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#\$%^&*(),.?\":{}|<>]{8,}\$")
    return password.matches(passwordRegex)
}

@Composable
fun RegisterScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }
    var showWelcomeMessage by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    fun validateInputs(): Boolean {
        if (email.isBlank()) {
            message = "L'email ne peut pas être vide."
            return false
        }
        if (!isPasswordValid(password)) {
            message = "Le mot de passe doit contenir au moins 8 caractères, une majuscule et un caractère spécial."
            return false
        }
        if (password != confirmPassword) {
            message = "Les mots de passe ne correspondent pas."
            return false
        }
        return true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
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
            AnimatedVisibility(
                visible = showWelcomeMessage,
                enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
                exit = fadeOut(animationSpec = tween(durationMillis = 500))
            ) {
                Text(
                    text = "Bienvenue dans l'application Kitesurf",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = FontFamily(Font(R.font.roboto_bold)),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 24.sp
                    ),
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(8.dp)
                        .background(MaterialTheme.colorScheme.background)
                )
            }

            // Icône de l'application
            Image(
                painter = painterResource(id = R.drawable.kitesurf_logo),
                contentDescription = "Icône de l'application",
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 16.dp)
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
                        text = "Créer un compte",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Mot de passe") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirmer le mot de passe") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            message = null
                            if (!validateInputs()) return@Button

                            val user = UserRequest(email, password)

                            coroutineScope.launch {
                                try {
                                    val response = RetrofitInstance.api.register(user)
                                    val body = response.body()
                                    if (response.isSuccessful && body?.message != null) {
                                        message = body.message
                                        // Navigation après un court délai pour voir le message
                                        navController.navigate("login_screen") {
                                            popUpTo("register_screen") { inclusive = true }
                                        }
                                    } else {
                                        message = body?.error ?: "Erreur lors de l'inscription."
                                    }
                                } catch (e: Exception) {
                                    message = "Erreur réseau : ${e.localizedMessage}"
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Créer un compte")
                    }
                    TextButton(
                        onClick = { navController.navigate("login_screen") },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("J'ai déjà un compte")
                    }

                    message?.let {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }

    // Afficher le message de bienvenue après un délai
    LaunchedEffect(Unit) {
        showWelcomeMessage = true
    }
}