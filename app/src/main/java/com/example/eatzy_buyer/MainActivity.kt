package com.example.eatzy_buyer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import com.example.eatzy_buyer.navigation.AppNavigation
import com.example.eatzy_buyer.ui.theme.EatzyBuyerTheme
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

//tokennya taro sini, kalo apinya butuh authorisasi
const val token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NywiZW1haWwiOiJ3YXNoaW5ndG9uQGV4YW1wbGUuY29tIiwicm9sZSI6ImJ1eWVyIiwiaWF0IjoxNzQ3OTI0MDM5LCJleHAiOjE3NjM0NzYwMzl9.mkhNtLvJQr4hOwSnxgtZXbDheXEEpKtl27_A9bUCZLU"
class MainActivity() : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
    }

}




