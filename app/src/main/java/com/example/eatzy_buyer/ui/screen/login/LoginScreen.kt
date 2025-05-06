package com.example.eatzy_buyer.ui.screen.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.toozxling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.eatzy_buyer.ui.components.EmailTextField
import com.example.eatzy_buyer.ui.components.PasswordTextField
import com.example.eatzy_buyer.ui.components.PrimaryButton


@Preview(showBackground = true)
@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit = {},
    onLoginClick: (String, String) -> Unit = { _, _ -> }
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Masuk", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Email"
            )

            EmailTextField(
                value = email,
                onValueChange = { email = it },
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Kata Sandi"
            )

            PasswordTextField(
                value = password,
                onValueChange = { password = it },
                passwordVisible = passwordVisible,
                onVisibilityToggle = { passwordVisible = !passwordVisible }
            )

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                text = "Masuk",
                onClick = { onLoginClick(email, password) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = onNavigateToRegister) {
                Text("Belum punya akun? Daftar")
            }
        }
    }
}
