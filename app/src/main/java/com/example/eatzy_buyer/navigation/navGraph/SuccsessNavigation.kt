package com.example.eatzy_buyer.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.eatzy_buyer.ui.screen.successful.SuccessfulScreen

fun NavGraphBuilder.successGraph(navController: NavController) {
    composable("success") {
        SuccessfulScreen(navController)
    }
}
