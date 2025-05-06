package com.example.eatzy_buyer.ui.screen.login

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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eatzy_buyer.R
import com.example.eatzy_buyer.ui.components.EmailTextField
import com.example.eatzy_buyer.ui.components.PasswordTextField
import com.example.eatzy_buyer.ui.components.PrimaryButton

@Preview(showBackground = true)
@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit = {},
    onLoginClick: (String, String) -> Unit = { _, _ -> },
    onForgotPasswordClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg_login), // Ganti dengan nama file Anda
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
                    text = "Masuk",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Form Login + Fitur tambahan
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
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

                    Spacer(modifier = Modifier.height(12.dp))

                    // Checkbox "Ingat Saya"
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(start = 4.dp) // Sejajarkan dengan TextField
                            .fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = rememberMe,
                            onCheckedChange = { rememberMe = it }
                        )
                        Text(text = "Ingat Saya")
                    }

                    // Text "Lupa Kata Sandi?"
                    Text(
                        text = "Lupa Kata Sandi?",
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .clickable { onForgotPasswordClick() }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Tombol Masuk
                PrimaryButton(
                    text = "Masuk",
                    onClick = { onLoginClick(email, password) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Text "Belum punya akun? Daftar" dengan dua warna dan hanya "Daftar" yang bisa diklik
                Row {
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
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable { onNavigateToRegister() }
                    )
                }
            }
        }

    }
}
