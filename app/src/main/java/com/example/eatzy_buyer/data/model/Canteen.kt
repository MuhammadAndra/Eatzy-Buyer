package com.example.eatzy_buyer.data.model

data class Canteen(
    val id: Int,
    val name: String,
    val url: String,
    val menuCategoryId:List<Int>,
    val status:Boolean
)
