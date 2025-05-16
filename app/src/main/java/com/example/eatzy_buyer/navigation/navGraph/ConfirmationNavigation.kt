package com.example.eatzy_buyer.navigation.navGraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.eatzy_buyer.ui.screen.confirmation.ConfirmationScreen

fun NavGraphBuilder.confirmationGraph(
    navController: NavHostController,
) {
    composable("confirmation") {
        ConfirmationScreen(
            navController = navController,
            onOrderClick = {
                navController.navigate("success")  // Pastikan route nya sama dengan successGraph
            }
        )
    }
}
