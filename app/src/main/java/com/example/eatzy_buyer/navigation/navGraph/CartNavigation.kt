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
            onCheckoutClick = { order_id ->
                navController.navigate("confirmation/$order_id")
            }
        )
    }
}