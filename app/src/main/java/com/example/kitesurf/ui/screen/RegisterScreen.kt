package com.example.kitesurf.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Mot de passe") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
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

        message?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }
}
