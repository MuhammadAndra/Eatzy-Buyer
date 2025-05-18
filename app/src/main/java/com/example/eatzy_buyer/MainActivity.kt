package com.example.eatzy_buyer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.eatzy_buyer.navigation.AppNavigation
import com.example.eatzy_buyer.ui.theme.EatzyBuyerTheme
const val token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiZW1haWwiOiJhbXlAZXhhbXBsZS5jb20iLCJyb2xlIjoiYnV5ZXIiLCJpYXQiOjE3NDc1NTE1ODksImV4cCI6MTc2MzEwMzU4OX0.Jz0XAzuQTFdmC35xx6dBzTlfLxgrbVtZhjEdCysiteo"
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




