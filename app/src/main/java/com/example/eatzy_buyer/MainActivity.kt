package com.example.eatzy_buyer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.eatzy_buyer.navigation.AppNavigation
import com.example.eatzy_buyer.ui.theme.EatzyBuyerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EatzyBuyerTheme {
                AppNavigation()
            }
        }
    }
}




