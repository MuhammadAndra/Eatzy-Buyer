package com.example.eatzy_buyer.ui.screen.addMenu

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import com.example.eatzy_buyer.data.model.getMenuById

@Composable
fun AddMenuScreen(
    modifier: Modifier = Modifier,
    idCategoryMenu: Int,
    idMenu: Int
) {
    val menu = getMenuById(idCategoryMenu = idCategoryMenu,idMenu = idMenu)
    Text(menu.name)
}