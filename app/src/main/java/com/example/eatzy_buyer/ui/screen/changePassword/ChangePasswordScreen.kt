package com.example.eatzy_buyer.ui.screen.changePassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eatzy_buyer.R
import com.example.eatzy_buyer.ui.components.EmailTextField
import com.example.eatzy_buyer.ui.components.PasswordTextField
import com.example.eatzy_buyer.ui.components.PrimaryButton

@Preview(showBackground = true)
@Composable
fun ChangePasswordScreen(
    onChangePasswordClick: (String, String) -> Unit= { _, _-> },
) {
    var password by remember { mutableStateOf("") }
    var passwordConfirm by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

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
                    text = "Lupa Kata Sandi",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Form Login + Fitur tambahan
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Ubah Kata Sandi",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF59A2F),
                        fontSize = 20.sp,
                    )

                    Text(
                        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. ",
                        color = Color(0xff4b4544),
                        fontSize = 14.sp,
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

                    Spacer(modifier = Modifier.height(16.dp))


                    Text(text = "Konfirmasi Kata Sandi")
                    EmailTextField(
                        value = passwordConfirm,
                        onValueChange = { passwordConfirm = it },
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                }

                Spacer(modifier = Modifier.height(16.dp))

                // Tombol Daftar
                PrimaryButton(
                    text = "Reset Kata Sandi",
                    onClick = { onChangePasswordClick(password, passwordConfirm) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }
}





