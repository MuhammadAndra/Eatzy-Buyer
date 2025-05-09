package com.example.eatzy_buyer.ui.screen.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eatzy_buyer.R
import com.example.eatzy_buyer.ui.components.EmailTextField
import com.example.eatzy_buyer.ui.components.PasswordTextField
import com.example.eatzy_buyer.ui.components.PrimaryButton

@Preview(showBackground = true)
@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit = {},
    onRegisterClick: (String, String, String) -> Unit = { _, _, _ -> },
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

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
                // Judul
                Text(
                    text = "Daftar",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Form Login + Fitur tambahan
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Email
                    Text(text = "Username")
                    EmailTextField(
                        value = username,
                        onValueChange = { username = it },
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Email
                    Text(text = "Email")
                    EmailTextField(
                        value = email,
                        onValueChange = { email = it },
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password
                    Text(text = "Kata Sandi")
                    PasswordTextField(
                        value = password,
                        onValueChange = { password = it },
                        passwordVisible = passwordVisible,
                        onVisibilityToggle = { passwordVisible = !passwordVisible }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                }

                Text(
                    text = "By continuing, you agree to \n" +
                            "Terms of Use and Privacy Policy.",
                    color = Color(0xff4b4544),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Tombol Daftar
                PrimaryButton(
                    text = "Daftar",
                    onClick = { onRegisterClick(username, email, password) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Text
                Row {
                    Text(
                        text = "Sudah punya akun?",
                        color = Color(0xFFACBED8),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Masuk",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF59A2F),
                        fontSize = 14.sp,
                        modifier = Modifier.clickable { onNavigateToLogin() }
                    )
                }
            }
        }
    }
}
