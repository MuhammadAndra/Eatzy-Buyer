// ui/screen/register/RegisterScreen.kt
package com.example.eatzy_buyer.ui.screen.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.widget.Toast
import androidx.compose.ui.layout.ContentScale
// Hapus import viewModel() jika sudah di-inject dari AuthGraph
// import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eatzy_buyer.R
import com.example.eatzy_buyer.UserViewModel
import com.example.eatzy_buyer.ui.components.PasswordTextField
import com.example.eatzy_buyer.ui.components.PrimaryButton
// import kotlinx.coroutines.launch // Tidak perlu jika Toast di dalam LaunchedEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit = {},
    onNavigateToOtp: (String) -> Unit = {},
    viewModel: UserViewModel // Terima ViewModel sebagai parameter
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirm by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) } // State terpisah

    val context = LocalContext.current
    // val scope = rememberCoroutineScope() // Tidak perlu jika Toast di dalam LaunchedEffect
    val registerState by viewModel.registerState.collectAsState()

    LaunchedEffect(registerState) {
        if (registerState.success) {
            Toast.makeText(context, "Registrasi berhasil, periksa email untuk OTP", Toast.LENGTH_SHORT).show()
            onNavigateToOtp(email) // Pastikan email yang dikirim adalah yang diinput
            viewModel.resetAuthStates() // Reset state setelah sukses
        } else if (registerState.error != null) {
            Toast.makeText(context, registerState.error, Toast.LENGTH_SHORT).show()
            // viewModel.clearRegisterError() // Panggil fungsi untuk reset error spesifik
        }
    }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_login), // Pastikan drawable ada
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
                    text = "Daftar",
                    style = MaterialTheme.typography.headlineMedium // Sesuaikan
                )

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Nama") },
                        singleLine = true,
                        enabled = !registerState.isLoading
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Email") },
                        singleLine = true,
                        enabled = !registerState.isLoading
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    PasswordTextField(
                        value = password,
                        onValueChange = { password = it },
                        passwordVisible = passwordVisible,
                        onVisibilityToggle = { passwordVisible = !passwordVisible },
                        label = { Text("Kata Sandi") },
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    PasswordTextField(
                        value = passwordConfirm,
                        onValueChange = { passwordConfirm = it },
                        passwordVisible = confirmPasswordVisible, // Gunakan state terpisah
                        onVisibilityToggle = { confirmPasswordVisible = !confirmPasswordVisible }, // Gunakan state terpisah
                        label = { Text("Konfirmasi Kata Sandi") },
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                }

                if (registerState.isLoading) {
                    CircularProgressIndicator()
                } else {
                    PrimaryButton(
                        text = "Daftar",
                        onClick = {
                            if (password.isBlank() || email.isBlank() || name.isBlank() || passwordConfirm.isBlank()) {
                                Toast.makeText(context, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                            } else if (password != passwordConfirm) {
                                Toast.makeText(context, "Konfirmasi kata sandi tidak cocok", Toast.LENGTH_SHORT).show()
                            } else {
                                viewModel.register(name, email, password)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !registerState.isLoading
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(), // Agar clickable area lebih luas
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Sudah punya akun?",
                        color = Color(0xFFACBED8), // Pertimbangkan Theme Color
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Masuk",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF59A2F), // Pertimbangkan Theme Color
                        fontSize = 14.sp,
                        modifier = Modifier.clickable(enabled = !registerState.isLoading) { onNavigateToLogin() }
                    )
                }
            }
        }
    }
}