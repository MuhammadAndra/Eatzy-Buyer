package com.example.eatzy_buyer.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.eatzy_buyer.ui.screen.myOrder.MyOrderScreen
import kotlinx.serialization.Serializable

@Serializable
object MyOrder

fun NavGraphBuilder.orderGraph(navController: NavController) {
    composable<MyOrder>{
        MyOrderScreen(navController = navController)
    }
}