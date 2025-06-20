package com.example.eatzy_buyer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.eatzy_buyer.navigation.AppNavigation
import com.example.eatzy_buyer.ui.theme.EatzyBuyerTheme

//tokennya taro sini, kalo apinya butuh authorisasi
const val token ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6NiwiZW1haWwiOiJqYWVAZ21haWwuY29tIiwicm9sZSI6ImJ1eWVyIiwiaWF0IjoxNzQ4NjY4NzAyLCJleHAiOjE3NjQyMjA3MDJ9.qVuuBVMBCGwXE2wuFwZsl1hYoG99EG4ck7tPpNFCFF0"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EatzyBuyerTheme {
                AppNavigation()
            }
        }
    }
}