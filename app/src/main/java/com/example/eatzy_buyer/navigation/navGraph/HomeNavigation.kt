package com.example.eatzy_buyer.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.eatzy_buyer.ui.screen.canteen.CanteenScreen

import kotlinx.serialization.Serializable

@Serializable
object Search

@Serializable
object Canteen

fun NavGraphBuilder.homeGraph(navController: NavController) {
    composable<Search> {

    }
    composable<Canteen> {
        CanteenScreen(onNavigateToLogin = { navController.navigate(Login) })
    }
}