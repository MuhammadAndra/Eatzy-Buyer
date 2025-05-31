// MainActivity.kt
package com.example.eatzy_buyer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.eatzy_buyer.navigation.AppNavigation
import com.example.eatzy_buyer.ui.theme.EatzyBuyerTheme

class MainActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels { UserViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContent {
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