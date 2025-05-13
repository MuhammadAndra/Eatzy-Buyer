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
    data class AddMenu(val idCategoryMenu: Int, val idMenu: Int)

    @Serializable
    object Search

    fun NavGraphBuilder.menuGraph(navController: NavController) {
        composable<ListMenu> { backStackEntry ->
            val listMenu: ListMenu = backStackEntry.toRoute()
            ListMenuScreen(
                navController = navController,
                onNavigateUp = {  navController.popBackStack()},
                canteenId = listMenu.canteenId,
                onNavigateToAddMenu = { idCategoryMenu: Int, idMenu: Int ->
                    navController.navigate(
                        AddMenu(idCategoryMenu = idCategoryMenu, idMenu = idMenu)
                    )
                },
                onNavigateToCart = {}
            )
        }
        composable<AddMenu> { backStackEntry ->
            val addMenu: AddMenu = backStackEntry.toRoute()
            AddMenuScreen(
                navController = navController,
                idCategoryMenu = addMenu.idCategoryMenu,
                idMenu = addMenu.idMenu,
                onNavigateUp = {navController.popBackStack()}
            )
        }
        composable<Search>{
            SearchScreen(onNavigateUp = { navController.navigateUp() })
        }
    }