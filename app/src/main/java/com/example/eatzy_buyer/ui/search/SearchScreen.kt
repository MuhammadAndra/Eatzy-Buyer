package com.example.eatzy_buyer.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eatzy_buyer.ui.components.TopBar

@Composable
fun SearchScreen(modifier: Modifier = Modifier, onNavigateUp: () -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopBar(
                title = "Cari",
                onNavigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        Column(Modifier
            .padding(innerPadding)
            .padding(horizontal = 16.dp)) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                value = searchQuery,
                placeholder = { Text("Search", color = Color.Gray, fontSize = 13.sp) },
                onValueChange = { searchQuery = it },
                shape = RoundedCornerShape(50),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFFF59A2F), // Warna kuning saat fokus
                    unfocusedIndicatorColor = Color.Gray,
                    focusedLabelColor = Color(0xFFF59A2F),  // Warna label saat fokus (opsional)
                    cursorColor = Color(0xFFF59A2F)         // Warna kursor saat mengetik (opsional)
                ),
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen(onNavigateUp = {})
}