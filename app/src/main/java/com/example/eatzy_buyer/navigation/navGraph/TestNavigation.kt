package com.example.eatzy_buyer.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.eatzy_buyer.ui.screen.canteen.CanteenScreen
import com.example.eatzy_buyer.ui.screen.test.TestApiScreen
import kotlinx.serialization.Serializable

@Serializable
object Test

fun NavGraphBuilder.testGraph(navController: NavController) {
    composable<Test> {
        TestApiScreen()
    }
}