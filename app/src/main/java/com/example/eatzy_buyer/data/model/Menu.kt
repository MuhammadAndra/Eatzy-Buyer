package com.example.eatzy_buyer.data.model

data class Menu(
    val id: Int,
    val name: String,
    val preparationTime: Int,
    val addOnCategoryId: List<Int>,
    val imageUrl: String,
    val isAvailable: Boolean,
    val menuPrice: Double,
)
