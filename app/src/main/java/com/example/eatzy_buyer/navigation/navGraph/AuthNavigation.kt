package com.example.eatzy_buyer.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.eatzy_buyer.ui.screen.changePassword.ChangePasswordScreen
import com.example.eatzy_buyer.ui.screen.login.LoginScreen
import com.example.eatzy_buyer.ui.screen.register.RegisterScreen
import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Register

@Serializable
object ChangePassword

fun NavGraphBuilder.authGraph(navController: NavController) {
    composable<Login> {
        LoginScreen(onNavigateToRegister = { navController.navigate(Register) })
    }
    composable<Register> {
        RegisterScreen(onNavigateToChangePassword = {
            navController.navigate(
                ChangePassword
            )
        })
    }
    composable<ChangePassword> {
        ChangePasswordScreen(
            onNavigateToLogin = { navController.navigate(Login) },
            onNavigateToCanteen = { navController.navigate(Canteen) })
    }
}