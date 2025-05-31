package com.example.eatzy_buyer.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.eatzy_buyer.UserViewModel
import com.example.eatzy_buyer.ui.screen.cart.CartScreen
import com.example.eatzy_buyer.ui.screen.favorite.FavoriteScreen
import com.example.eatzy_buyer.ui.screen.history.HistoryScreen
import com.example.eatzy_buyer.ui.screen.home.HomeScreen
import com.example.eatzy_buyer.ui.screen.editProfile.EditProfile
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

@Serializable
object EditProfile

fun NavGraphBuilder.mainGraph(navController: NavController, viewModel: UserViewModel) {
    navigation<MainGraph>(startDestination = Home) { // Use MainGraph as the parent route
        composable<Home> {
            HomeScreen(
                navController = navController,
                onNavigateToSearchScreen = { navController.navigate(Search) },
                onNavigateToListMenuScreen = { canteenId ->
                    navController.navigate(
                        ListMenu(canteenId = canteenId)
                    )
                }
            )
        }
        composable<History> {
            HistoryScreen(navController = navController)
        }
        composable<Cart> {
            CartScreen(navController = navController)
        }
        composable<Favorite> {
            FavoriteScreen(navController = navController)
        }
        composable<Profile> {
            ProfileScreen(
                navController = navController,
                viewModel = viewModel,
                onNavigateToEdit = { navController.navigate(EditProfile) },
                onLogout = {
                    viewModel.logout()
                    navController.navigate(AuthGraph) { // Navigate back to auth flow on logout
                        popUpTo<MainGraph> { inclusive = true }
                    }
                }
            )
        }
        composable<EditProfile> {
            EditProfile(navController = navController, viewModel = viewModel)
        }
    }
}