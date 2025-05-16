package com.example.eatzy_buyer.navigation.navGraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.eatzy_buyer.ui.screen.cart.CartScreen

fun NavGraphBuilder.cartGraph(
    navController: NavHostController,
) {
    composable("cart") {
        CartScreen(
            navController = navController,
            // Navigasi pakai onCheckoutClick agar bisa pindah ke confirmation
            onCheckoutClick = {
                navController.navigate("confirmation")
            }
        )
    }
}