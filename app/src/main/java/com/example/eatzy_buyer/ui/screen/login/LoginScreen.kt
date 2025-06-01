// ui/screen/login/LoginScreen.kt
package com.example.eatzy_buyer.ui.screen.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eatzy_buyer.R
import com.example.eatzy_buyer.UserViewModel
import com.example.eatzy_buyer.ui.components.PasswordTextField
import com.example.eatzy_buyer.ui.components.PrimaryButton
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit = {},
    onNavigateToForgotPassword: () -> Unit = {},
    onNavigateToHome: () -> Unit,
    viewModel: UserViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var deviceToken by remember { mutableStateOf("") }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val loginState by viewModel.loginState.collectAsState()
    val userState by viewModel.userState.collectAsState()

    // Use both loginState and userState as keys to ensure the effect re-runs when either changes
    LaunchedEffect(loginState.success, userState.user) {
        Log.d("LoginScreen", "LaunchedEffect triggered: loginState.success=${loginState.success}, userState.user=${userState.user}")
        if (loginState.success && userState.user != null) {
            Log.d("LoginScreen", "Login successful, navigating to Profile")
            scope.launch { Toast.makeText(context, "Login berhasil", Toast.LENGTH_SHORT).show() }
            onNavigateToHome()
            viewModel.resetAuthStates()
        } else if (loginState.error != null) {
            Log.d("LoginScreen", "Login error: ${loginState.error}")
            scope.launch { Toast.makeText(context, loginState.error, Toast.LENGTH_SHORT).show() }
        }
    }

    LaunchedEffect(userState.error) {
        if (userState.error != null) {
            Log.d("LoginScreen", "User state error: ${userState.error}")
            scope.launch { Toast.makeText(context, userState.error, Toast.LENGTH_SHORT).show() }
        }
    }

    LaunchedEffect(Unit) {
        Firebase.messaging.token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }

            deviceToken = task.result
            Log.d("FCM", "FCM Token: $deviceToken")
        }
    }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_login),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Masuk",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(24.dp))
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Email") },
                        singleLine = true,
                        enabled = !loginState.isLoading
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    PasswordTextField(
                        value = password,
                        onValueChange = { password = it },
                        passwordVisible = passwordVisible,
                        onVisibilityToggle = { passwordVisible = !passwordVisible },
                        label = { Text("Kata Sandi") }
                    )
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Lupa Kata Sandi?",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFF59A2F),
                            fontSize = 14.sp,
                            modifier = Modifier
                                .clickable(enabled = !loginState.isLoading) { onNavigateToForgotPassword() }
                                .padding(top = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
                if (loginState.isLoading) {
                    CircularProgressIndicator()
                } else {
                    PrimaryButton(
                        text = "Masuk",
                        onClick = { viewModel.login(email, password, deviceToken) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !loginState.isLoading
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Belum punya akun?",
                        color = Color(0xFFACBED8),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Daftar",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF59A2F),
                        fontSize = 14.sp,
                        modifier = Modifier.clickable(enabled = !loginState.isLoading) { onNavigateToRegister() }
                    )
                }
            }
        }
    }
}