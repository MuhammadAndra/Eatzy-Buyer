package com.example.eatzy_buyer.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarSearch(
    title: String, onNavigateUp: () -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    isSearching: Boolean,
    onToggleSearch: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            if (isSearching) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    placeholder = { Text("Cari...", fontSize = 14.sp) },
                    textStyle = TextStyle(fontSize = 14.sp),
                    singleLine = true,
                    shape = RoundedCornerShape(30.dp),
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent,
                        errorBorderColor = Color.Transparent
                    )
                )
            } else {
                Text(text = title, fontSize = 20.sp)
            }
        },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Kembali",
                    tint = Color(0XFF4B4544)
                )
            }
        },
        actions = {
            IconButton(
                onClick = onToggleSearch,
                colors = IconButtonDefaults.iconButtonColors(
                    Color(0xff455E84)
                )
            ) {
                Icon(
                    imageVector = if (isSearching) Icons.Filled.Close else Icons.Filled.Search,
                    contentDescription = if (isSearching) "Tutup Pencarian" else "Cari",
                    tint = Color.White
                )
            }
        }
    )
}

@Preview
@Composable
private fun TopBarSearchPreview() {
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }

    TopBarSearch(
        title = "Kantin Tahu Telor",
        onNavigateUp = { /* aksi back */ },
        searchQuery = searchQuery,
        onSearchQueryChange = { searchQuery = it },
        isSearching = isSearching,
        onToggleSearch = {
            isSearching = !isSearching
            if (!isSearching) searchQuery = ""
        }
    )
}