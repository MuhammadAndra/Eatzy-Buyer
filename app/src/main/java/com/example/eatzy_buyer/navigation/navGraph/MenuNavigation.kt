package com.example.eatzy_buyer.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import kotlinx.serialization.Serializable
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.eatzy_buyer.ui.screen.addMenu.AddMenuScreen
import com.example.eatzy_buyer.ui.screen.listMenuScreen.ListMenuScreen
import com.example.eatzy_buyer.ui.search.SearchScreen


@Serializable
data class ListMenu(val canteenId: Int)

@Serializable
data class AddMenu(
    val idCategoryMenu: Int?,
    val menuId: Int,
    val canteenId: Int,
    val orderId: Int? = null,
    val orderItemId: Int? = null,
    val count: Int? = null,
    val orderItemIds: List<Int>? = emptyList()
)

@Serializable
object Search

fun NavGraphBuilder.menuGraph(navController: NavController) {
    composable<ListMenu> { backStackEntry ->
        val listMenu: ListMenu = backStackEntry.toRoute()
        ListMenuScreen(
            navController = navController,
            onNavigateUp = { navController.popBackStack() },
            canteenId = listMenu.canteenId,
            onNavigateToAddMenu = { idCategoryMenu: Int?, menuId: Int, canteenId: Int, orderId: Int?, orderItemId: Int?, count: Int?, orderItemIds: List<Int>? ->
                navController.navigate(
                    AddMenu(
                        idCategoryMenu = idCategoryMenu,
                        menuId = menuId,
                        canteenId = canteenId,
                        orderId = orderId,
                        orderItemId = orderItemId,
                        count = count,
                        orderItemIds = orderItemIds
                    )
                )
            },
            onNavigateToCart = { orderId ->
                navController.navigate(route = "confirmation/$orderId")
            }
        )
    }
    composable<AddMenu> { backStackEntry ->
        val addMenu: AddMenu = backStackEntry.toRoute()
        AddMenuScreen(
            navController = navController,
            idCategoryMenu = addMenu.idCategoryMenu,
            menuId = addMenu.menuId,
            canteenId = addMenu.canteenId,
            orderId = addMenu.orderId,
            orderItemId = addMenu.orderItemId,
            count = addMenu.count,
            orderItemIds = addMenu.orderItemIds,
            onNavigateUp = { navController.popBackStack() }
        )
    }
    composable<Search> {
        SearchScreen(
            onNavigateUp = { navController.navigateUp() },
            onNavigateToListMenuScreen = { canteenId ->
                navController.navigate(
                    ListMenu(canteenId = canteenId)
                )
            }
        )
    }
}