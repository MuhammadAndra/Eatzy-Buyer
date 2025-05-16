package com.example.eatzy_buyer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.eatzy_buyer.navigation.navGraph.History
import com.example.eatzy_buyer.navigation.navGraph.Home
import com.example.eatzy_buyer.navigation.navGraph.MyOrder
import com.example.eatzy_buyer.navigation.navGraph.Test
import com.example.eatzy_buyer.navigation.navGraph.authGraph
import com.example.eatzy_buyer.navigation.navGraph.homeGraph
import com.example.eatzy_buyer.navigation.navGraph.mainGraph
import com.example.eatzy_buyer.navigation.navGraph.orderGraph
import com.example.eatzy_buyer.navigation.navGraph.testGraph


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Test) {
        authGraph(navController)
        homeGraph(navController)
        testGraph(navController)
        mainGraph(navController)
        orderGraph(navController)
    }
}