package com.example.eatzy_buyer.ui.screen.changePassword

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
import com.example.eatzy_buyer.ui.components.OtpTextField
import com.example.eatzy_buyer.ui.components.PasswordTextField
import com.example.eatzy_buyer.ui.components.PrimaryButton

@Composable
fun ResetPasswordScreen(
    email: String,
    viewModel: UserViewModel,
    onNavigateToLogin: () -> Unit
) {
    var otp by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val resetPasswordState by viewModel.resetPasswordState.collectAsState()
    val context = LocalContext.current

    // Handle reset password response
    LaunchedEffect(resetPasswordState) {
        when {
            resetPasswordState.success -> {
                isLoading = false
                Toast.makeText(context, "Password berhasil direset", Toast.LENGTH_LONG).show()
                onNavigateToLogin()
                viewModel.resetAuthStates()
            }
            resetPasswordState.error != null -> {
                isLoading = false
                Toast.makeText(context, resetPasswordState.error, Toast.LENGTH_LONG).show()
                viewModel.resetAuthStates()
            }
        }
    }

    fun validateInputs(): String? {
        return when {
            otp.isBlank() -> "Kode OTP tidak boleh kosong"
            otp.length != 6 -> "Kode OTP harus 6 digit"
            newPassword.isBlank() -> "Password baru tidak boleh kosong"
            newPassword.length < 6 -> "Password minimal 6 karakter"
            confirmPassword.isBlank() -> "Konfirmasi password tidak boleh kosong"
            newPassword != confirmPassword -> "Password dan konfirmasi tidak sama"
            else -> null
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
                    text = "Reset Kata Sandi",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFFF59A2F),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Masukkan kode OTP yang telah dikirim ke $email dan password baru Anda",
                    color = Color(0xff4b4544),
                    fontSize = 14.sp,
                )

                Spacer(modifier = Modifier.height(32.dp))

                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // OTP Field
                    Text(
                        text = "Kode OTP",
                        fontWeight = FontWeight.Medium,
                        color = Color(0xff4b4544)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OtpTextField(
                        value = otp,
                        onValueChange = { if (it.length <= 6) otp = it },
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // New Password Field
                    Text(
                        text = "Password Baru",
                        fontWeight = FontWeight.Medium,
                        color = Color(0xff4b4544)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    PasswordTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        passwordVisible = passwordVisible,
                        onVisibilityToggle = { passwordVisible = !passwordVisible },
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Confirm Password Field
                    Text(
                        text = "Konfirmasi Password",
                        fontWeight = FontWeight.Medium,
                        color = Color(0xff4b4544)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    PasswordTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        passwordVisible = confirmPasswordVisible,
                        onVisibilityToggle = { confirmPasswordVisible = !confirmPasswordVisible },
                    )

                    Spacer(modifier = Modifier.height(32.dp))
                }

                PrimaryButton(
                    text = if (isLoading) "Mereset..." else "Reset Password",
                    onClick = {
                        val validation = validateInputs()
                        if (validation != null) {
                            Toast.makeText(context, validation, Toast.LENGTH_SHORT).show()
                        } else {
                            isLoading = true
                            viewModel.resetPassword(email, otp, newPassword)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
                )

                if (isLoading) {
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator(
                        color = Color(0xFFF59A2F)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Resend OTP option
                TextButton(
                    onClick = {
                        viewModel.forgotPassword(email)
                        Toast.makeText(context, "OTP baru telah dikirim", Toast.LENGTH_SHORT).show()
                    },
                    enabled = !isLoading
                ) {
                    Text(
                        text = "Kirim ulang OTP",
                        color = Color(0xFFF59A2F)
                    )
                }
            }
        }
    }
}