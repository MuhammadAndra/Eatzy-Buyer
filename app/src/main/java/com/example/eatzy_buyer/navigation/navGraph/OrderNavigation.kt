package com.example.eatzy_buyer.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.eatzy_buyer.ui.screen.myOrder.MyOrderScreen
import kotlinx.serialization.Serializable

@Serializable
data class MyOrder(val orderId: Int)

fun NavGraphBuilder.orderGraph(navController: NavController) {
    composable<MyOrder>{ backStackEntry ->
        val myOrder: MyOrder = backStackEntry.toRoute()
        MyOrderScreen(
            navController = navController,
            orderId = myOrder.orderId,
            onNavigateup = { navController.popBackStack() }
        )
    }
}