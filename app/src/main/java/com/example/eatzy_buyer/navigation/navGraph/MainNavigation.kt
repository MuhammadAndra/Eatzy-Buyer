package com.example.eatzy_buyer.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.eatzy_buyer.ui.screen.cart.CartScreen
import com.example.eatzy_buyer.ui.screen.favorite.FavoriteScreen
import com.example.eatzy_buyer.ui.screen.home.HomeScreen
import com.example.eatzy_buyer.ui.screen.history.HistoryScreen
import com.example.eatzy_buyer.ui.screen.profile.ProfileScreen
import kotlinx.serialization.Serializable

@Serializable
object Home
@Serializable
object History
@Serializable
object Cart
@Serializable
object Favorite
@Serializable
object Profile

fun NavGraphBuilder.mainGraph(navController: NavController) {
    composable<Home>{
        HomeScreen(navController = navController)
    }
    composable<History>{
        HistoryScreen(
            navController = navController,
            onNavigateToMyOrder = { orderId ->
                navController.navigate(
                    MyOrder(orderId)
                )
            }
        )
    }
    composable<Cart>{
        CartScreen(
            navController = navController,
            onNavigateToMyOrder = { navController.navigate(MyOrder(1)) }
        )
    }
    composable<Favorite>{
        FavoriteScreen(
            navController = navController,
            onNavigateToOrder = { navController.navigate(Cart) }
        )
    }
    composable<Profile>{
        ProfileScreen(navController = navController)
    }
}