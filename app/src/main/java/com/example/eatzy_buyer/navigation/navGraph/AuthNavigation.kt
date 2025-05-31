// navigation/navGraph/AuthGraph.kt
package com.example.eatzy_buyer.navigation.navGraph

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.eatzy_buyer.UserViewModel
import com.example.eatzy_buyer.ui.screen.forgotpassword.ForgotPasswordScreen
import com.example.eatzy_buyer.ui.screen.login.LoginScreen
import com.example.eatzy_buyer.ui.screen.verificationOtp.OtpVerificationScreen
import com.example.eatzy_buyer.ui.screen.register.RegisterScreen
import com.example.eatzy_buyer.ui.screen.welcomingPage.WelcomingPageScreen
import com.example.eatzy_buyer.ui.screen.changePassword.ResetPasswordScreen
import kotlinx.serialization.Serializable

@Serializable
object Welcome

@Serializable
object Login

@Serializable
object Register

@Serializable
data class OtpVerification(val email: String)

@Serializable
object ForgotPassword

@Serializable
data class ResetPassword(val email: String)

@Serializable
object AuthGraph

@Serializable
object MainGraph // Define MainGraph as a serializable route

fun NavGraphBuilder.authGraph(navController: NavHostController, viewModel: UserViewModel) {
    navigation<AuthGraph>(startDestination = Welcome) {
        composable<Welcome> {
            WelcomingPageScreen(
                onWelcomePageClick = {
                    navController.navigate(Login) {
                        popUpTo<Welcome> { inclusive = true }
                    }
                }
            )
        }
        composable<Login> {
            LoginScreen(
                onNavigateToRegister = { navController.navigate(Register) },
                onNavigateToForgotPassword = { navController.navigate(ForgotPassword) },
                onNavigateToHome = {
                    Log.d("AuthGraph", "Navigating to MainGraph from LoginScreen")
                    navController.navigate(MainGraph) { // Navigate to MainGraph instead of Profile directly
                        popUpTo<AuthGraph> { inclusive = true }
                    }
                },
                viewModel = viewModel
            )
        }
        composable<Register> {
            RegisterScreen(
                onNavigateToLogin = { navController.navigate(Login) },
                onNavigateToOtp = { email -> navController.navigate(OtpVerification(email)) },
                viewModel = viewModel
            )
        }
        composable<OtpVerification> { backStackEntry ->
            val route = backStackEntry.toRoute<OtpVerification>()
            OtpVerificationScreen(
                email = route.email,
                onNavigateToLogin = { navController.navigate(Login) },
                viewModel = viewModel
            )
        }
        composable<ForgotPassword> {
            ForgotPasswordScreen(
                viewModel = viewModel,
                onNavigateToResetPassword = { email -> navController.navigate(ResetPassword(email)) }
            )
        }
        composable<ResetPassword> { backStackEntry ->
            val route = backStackEntry.toRoute<ResetPassword>()
            ResetPasswordScreen(
                email = route.email,
                viewModel = viewModel,
                onNavigateToLogin = { navController.navigate(Login) }
            )
        }
    }
}