package com.example.eatzy_buyer.data.model

data class Menu(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val addOnCategoryId:List<Int>,
    val price: Double,
    val isAvailable:Boolean,
    val preparationTime:Int
)
