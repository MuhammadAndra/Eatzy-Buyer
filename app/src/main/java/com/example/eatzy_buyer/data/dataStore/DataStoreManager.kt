package com.example.eatzy_buyer.data.dataStore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extension property untuk DataStore
private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class DataStoreManager(private val context: Context) {

    companion object {
        private val JWT_TOKEN_KEY = stringPreferencesKey("jwt_token")
    }

    // Simpan token
    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[JWT_TOKEN_KEY] = token
        }
    }

    // Ambil token sebagai Flow
    val tokenFlow: Flow<String?> = context.dataStore.data
        .map { prefs ->
            prefs[JWT_TOKEN_KEY]
        }

    // Hapus token
    suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(JWT_TOKEN_KEY)
        }
    }
}
