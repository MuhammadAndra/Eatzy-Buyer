package com.example.eatzy_buyer.data.model

data class Menu(
    val id: Int,
    val name: String,
    val preparationTime: String,
    val menuImage: String,
    val isAvailable: Boolean,
    val menuPrice: Double,
)
