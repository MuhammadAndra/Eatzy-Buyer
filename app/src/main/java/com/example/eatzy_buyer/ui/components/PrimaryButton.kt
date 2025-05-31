package com.example.eatzy_buyer.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true // ← tambahkan parameter enabled
) {
    Button(
        onClick = onClick,
        enabled = enabled, // ← gunakan parameter enabled di sini
        modifier = modifier
            .height(48.dp),
        shape = RoundedCornerShape(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF455E84),
            contentColor = Color.White,
            disabledContainerColor = Color(0xFFB0B0B0), // ← warna tombol jika disabled
            disabledContentColor = Color.White
        )
    ) {
        Text(text = text)
    }
}

