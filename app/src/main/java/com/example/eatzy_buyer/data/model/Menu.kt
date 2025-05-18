package com.example.eatzy_buyer.data.model

import com.google.gson.annotations.SerializedName

data class Menu(
    @SerializedName("menu_id")
    val id: Int = 0,
    @SerializedName("menu_name")
    val name: String = "",
    @SerializedName("menu_image")
    val imageUrl: String = "",
    @SerializedName("menu_price")
    val price: Double = 0.0,
    @SerializedName("menu_is_available")
    val isAvailable: Boolean = true,
    val preparationTime: Int = 0,
    val addOnCategoryId: List<Int> = emptyList()
)
