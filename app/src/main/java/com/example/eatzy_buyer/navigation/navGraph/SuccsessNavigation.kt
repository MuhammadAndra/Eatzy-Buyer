package com.example.eatzy_buyer.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.eatzy_buyer.ui.screen.successful.SuccessfulScreen

fun NavGraphBuilder.successGraph(navController: NavController) {
    composable(
        route = "success/{order_id}",
        arguments = listOf(navArgument("order_id") { type = NavType.IntType })
    ) { backStackEntry ->
        val order_id = backStackEntry.arguments?.getInt("order_id") ?: 0

        SuccessfulScreen(
            navController = navController,
            orderId = order_id,
            onNavigateToMyOrder = { orderId ->
                navController.navigate(MyOrder(orderId))
            })
    }
}
