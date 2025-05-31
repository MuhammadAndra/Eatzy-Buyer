package com.example.eatzy_buyer.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eatzy_buyer.ui.screen.cart.CartScreen
import com.example.eatzy_buyer.ui.screen.confirmation.ConfirmationScreen
import com.example.eatzy_buyer.navigation.navGraph.Favorite
import com.example.eatzy_buyer.navigation.navGraph.History
import com.example.eatzy_buyer.navigation.navGraph.Home
import com.example.eatzy_buyer.navigation.navGraph.MyOrder
import com.example.eatzy_buyer.navigation.navGraph.Test
import com.example.eatzy_buyer.navigation.navGraph.authGraph
import com.example.eatzy_buyer.navigation.navGraph.cartGraph
import com.example.eatzy_buyer.navigation.navGraph.confirmationGraph
import com.example.eatzy_buyer.navigation.navGraph.homeGraph
import com.example.eatzy_buyer.navigation.navGraph.mainGraph
import com.example.eatzy_buyer.navigation.navGraph.menuGraph
import com.example.eatzy_buyer.navigation.navGraph.successGraph
import com.example.eatzy_buyer.navigation.navGraph.orderGraph
import com.example.eatzy_buyer.navigation.navGraph.testGraph
import com.example.eatzy_buyer.UserViewModel
import com.example.eatzy_buyer.UserViewModelFactory
import com.example.eatzy_buyer.navigation.navGraph.*
import com.example.eatzy_buyer.ui.screen.successful.SuccessfulScreen


@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val viewModel: UserViewModel = viewModel(
        factory = remember { UserViewModelFactory(context) } // Provide the custom factory
    )

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { route ->
            Log.d("AppNavigation", "Navigating to route: $route")
            when (route) {
                is Login -> navController.navigate(route) { popUpTo(navController.graph.startDestinationId) }
                is AuthGraph -> navController.navigate(route) { popUpTo(navController.graph.startDestinationId) }
                else -> navController.navigate(route.toString()) { popUpTo(navController.graph.startDestinationId) }
            }
        }
    }

    NavHost(navController = navController, startDestination = AuthGraph) {
        authGraph(navController, viewModel)
        mainGraph(navController, viewModel)
        homeGraph(navController )
        testGraph(navController)
        menuGraph(navController)
        cartGraph(navController)
        confirmationGraph(navController)
        successGraph(navController)
        orderGraph(navController)

    }
}