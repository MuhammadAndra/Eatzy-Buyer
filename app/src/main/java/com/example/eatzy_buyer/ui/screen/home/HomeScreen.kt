package com.example.eatzy_buyer.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.eatzy_buyer.ui.components.BottomNavBar

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController) {
    Scaffold(bottomBar = { BottomNavBar(navController) }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text("Home")
        }

    }
}