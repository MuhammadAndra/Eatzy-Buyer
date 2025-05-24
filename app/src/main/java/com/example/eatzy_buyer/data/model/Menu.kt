package com.example.eatzy_buyer.data.model

import com.google.gson.annotations.SerializedName

data class Menu(
    @SerializedName("menu_id")
    val id: Int = 0,

    @SerializedName("menu_name")
    val name: String = "",

    @SerializedName("menu_category")
    val category: MenuCategory? = null,

    @SerializedName("preparation_time")
    val preparationTime: Int = 0,

    @SerializedName("addon_category_id")
    val addOnCategoryId: List<Int> = emptyList(),

    @SerializedName("menu_image")
    val imageUrl: String = "",

    @SerializedName("is_available")
    val isAvailable: Boolean = true,

    @SerializedName("menu_price")
    val price: Double = 0.00,
)
