package com.example.eatzy_buyer.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.eatzy_buyer.ui.screen.changePassword.ChangePasswordScreen
import com.example.eatzy_buyer.ui.screen.forgotpassword.ForgotPassword
import com.example.eatzy_buyer.ui.screen.login.LoginScreen
import com.example.eatzy_buyer.ui.screen.register.RegisterScreen
import com.example.eatzy_buyer.ui.screen.welcomingPage.WelcomingPageScreen
import kotlinx.serialization.Serializable

@Serializable
object Welcome

@Serializable
object Login

@Serializable
object Register

@Serializable
object ForgotPassword

fun NavGraphBuilder.authGraph(navController: NavController) {
    composable<Login> {
        LoginScreen(
            onNavigateToRegister = { navController.navigate(Register) },
            onLoginClick = { email, password ->
                navController.navigate(Profile)
            },
            onForgotPasswordClick = {
                navController.navigate(ForgotPassword)
            }
        )
    }
    composable<Register> {
        RegisterScreen(
            onNavigateToLogin = { navController.navigate(Login) },
            onRegisterClick = { name, email, password ->
                navController.navigate(Login)
            }
        )
    }
    composable<Welcome> {
        WelcomingPageScreen(
            onWelcomePageClick = { navController.navigate(Login) }
        )
    }
    composable<ForgotPassword> {
        ForgotPassword(

        )
    }
}