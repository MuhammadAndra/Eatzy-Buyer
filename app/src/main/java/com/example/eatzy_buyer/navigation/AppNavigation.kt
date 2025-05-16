package com.example.eatzy_buyer.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eatzy_buyer.ui.screen.cart.CartScreen
import com.example.eatzy_buyer.ui.screen.confirmation.ConfirmationScreen
import com.example.eatzy_buyer.navigation.navGraph.Home
import com.example.eatzy_buyer.navigation.navGraph.authGraph
import com.example.eatzy_buyer.navigation.navGraph.cartGraph
import com.example.eatzy_buyer.navigation.navGraph.confirmationGraph
import com.example.eatzy_buyer.navigation.navGraph.homeGraph
import com.example.eatzy_buyer.navigation.navGraph.mainGraph
import com.example.eatzy_buyer.navigation.navGraph.successGraph
import com.example.eatzy_buyer.navigation.navGraph.testGraph
import com.example.eatzy_buyer.ui.screen.successful.SuccessfulScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Home) {
        authGraph(navController)
        homeGraph(navController)
        testGraph(navController)
        mainGraph(navController)
        cartGraph(navController)
        confirmationGraph(navController)
        successGraph(navController)

        //composable("successful") {
            //SuccessfulScreen(navController = navController)
        }
    }
//}
