package com.example.eatzy_buyer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.eatzy_buyer.navigation.AppNavigation
import com.example.eatzy_buyer.ui.theme.EatzyBuyerTheme

const val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiZW1haWwiOiJhbXlAZXhhbXBsZS5jb20iLCJyb2xlIjoiYnV5ZXIiLCJpYXQiOjE3NDc3MjI0NDgsImV4cCI6MTc2MzI3NDQ0OH0.DbNdHUTKxZqoXML7IIHgN9g1pkNj7pxSaZqquZ50Oro"

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




