package com.example.eatzy_buyer.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            // Hanya izinkan angka dan maksimal 6 digit
            if (newValue.all { it.isDigit() } && newValue.length <= 6) {
                onValueChange(newValue)
            }
        },
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = "Masukkan kode OTP 6 digit",
                color = Color.Gray
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        singleLine = true,
        enabled = enabled,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFF59A2F),
            unfocusedBorderColor = Color.Gray,
            disabledBorderColor = Color.LightGray
        )
    )
}