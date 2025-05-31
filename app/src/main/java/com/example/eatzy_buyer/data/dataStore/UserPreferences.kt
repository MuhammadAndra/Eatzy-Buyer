package com.example.eatzy_buyer.data.dataStore


import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")
object UserPreferences {
    private val TOKEN_KEY = stringPreferencesKey("auth_token")


    suspend fun saveUserData(context: Context, token: String, ) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token

        }
    }

    fun getToken(context: Context): Flow<String?> = context.dataStore.data.map { it[TOKEN_KEY] }

    suspend fun clearUserData(context: Context) {
        context.dataStore.edit {
            it.remove(TOKEN_KEY)
        }
    }
}