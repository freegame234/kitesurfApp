package com.example.kitesurf.session

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore by preferencesDataStore(name = "session")

object SessionKeys {
    val USER_ID = intPreferencesKey("user_id")
}

// Sauvegarde de l'ID utilisateur
suspend fun saveUserId(context: Context, userId: Int) {
    context.dataStore.edit { prefs ->
        prefs[SessionKeys.USER_ID] = userId
    }
}

// Lecture de l'ID utilisateur
suspend fun getUserId(context: Context): Int? {
    val prefs = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }
        .map { it[SessionKeys.USER_ID] }
        .first()

    return prefs
}

// Suppression de la session
suspend fun clearSession(context: Context) {
    context.dataStore.edit { it.clear() }
}
