// navigation/AppNavigation.kt
package com.example.eatzy_buyer.navigation

import android.util.Log
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
import com.example.eatzy_buyer.UserViewModel
import com.example.eatzy_buyer.UserViewModelFactory
import com.example.eatzy_buyer.navigation.navGraph.*

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
    }
}