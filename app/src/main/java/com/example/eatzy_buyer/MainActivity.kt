package com.example.eatzy_buyer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import com.example.eatzy_buyer.navigation.AppNavigation
import com.example.eatzy_buyer.ui.theme.EatzyBuyerTheme
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

//tokennya taro sini, kalo apinya butuh authorisasi
const val token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTMsImVtYWlsIjoiY2F0dGxleWExNzI0QGdtYWlsLmNvbSIsInJvbGUiOiJidXllciIsImlhdCI6MTc0ODc0OTQwNywiZXhwIjoxNzY0MzAxNDA3fQ.H5Zy_L3hgFTV-lzjoUzEnW523EhRTqFOMXT1AYDiPjU"
class MainActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels { UserViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContent {
                LaunchedEffect(Unit) {
                    Firebase.messaging.token.addOnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                            return@addOnCompleteListener
                        }

                        val token = task.result
                        Log.d("FCM", "FCM Token: $token")
                    }
                }
                EatzyBuyerTheme {
                    AppNavigation()
                }
            }
        } catch (e: Exception) {
            Log.e("MainActivity", "Error in onCreate: ${e.message}", e)
            throw e
        }
    }
}




