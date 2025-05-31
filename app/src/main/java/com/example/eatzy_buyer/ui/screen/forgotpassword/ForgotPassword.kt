package com.example.eatzy_buyer.ui.screen.forgotpassword

import android.widget.Toast
import androidx.compose.foundation.Image
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
import com.example.eatzy_buyer.ui.components.EmailTextField
import com.example.eatzy_buyer.ui.components.PrimaryButton

@Composable
fun ForgotPasswordScreen(
    viewModel: UserViewModel,
    onNavigateToResetPassword: (String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val forgotPasswordState by viewModel.forgotPasswordState.collectAsState()
    val context = LocalContext.current

    // Handle forgot password response
    LaunchedEffect(forgotPasswordState) {
        when {
            forgotPasswordState.success -> {
                isLoading = false
                Toast.makeText(context, "OTP telah dikirim ke email Anda", Toast.LENGTH_LONG).show()
                onNavigateToResetPassword(email)
                viewModel.resetAuthStates()
            }
            forgotPasswordState.error != null -> {
                isLoading = false
                Toast.makeText(context, forgotPasswordState.error, Toast.LENGTH_LONG).show()
                viewModel.resetAuthStates()
            }
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
                    text = "Lupa Kata Sandi",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFFF59A2F),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Masukkan email Anda untuk menerima kode OTP reset password",
                    color = Color(0xff4b4544),
                    fontSize = 14.sp,
                )

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Email",
                        fontWeight = FontWeight.Medium,
                        color = Color(0xff4b4544)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    EmailTextField(
                        value = email,
                        onValueChange = { email = it },
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }

                PrimaryButton(
                    text = if (isLoading) "Mengirim..." else "Kirim OTP",
                    onClick = {
                        if (email.isNotBlank()) {
                            isLoading = true
                            viewModel.forgotPassword(email)
                        } else {
                            Toast.makeText(context, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading && email.isNotBlank()
                )

                if (isLoading) {
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator(
                        color = Color(0xFFF59A2F)
                    )
                }
            }
        }
    }
}