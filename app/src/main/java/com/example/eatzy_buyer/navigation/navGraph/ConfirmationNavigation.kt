package com.example.eatzy_buyer.navigation.navGraph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.eatzy_buyer.ui.screen.confirmation.ConfirmationScreen

fun NavGraphBuilder.confirmationGraph(
    navController: NavHostController,
) {
    composable(
        route = "confirmation/{order_id}",
        arguments = listOf(navArgument("order_id") { type = NavType.IntType })
    ) { backStackEntry ->
        val order_id = backStackEntry.arguments?.getInt("order_id") ?: 0
        ConfirmationScreen(
            navController = navController,
            order_id = order_id,
            onOrderClick = {
                navController.navigate("success/$order_id")
            },
            onNavigateToListMenu = { canteenId ->
                navController.navigate(ListMenu(canteenId = canteenId))
            }
        )
    }

}
