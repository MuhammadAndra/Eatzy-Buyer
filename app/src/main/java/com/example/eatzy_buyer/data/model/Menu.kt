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

    @SerializedName("menu_category")
    val category: MenuCategory? = null,

    @SerializedName("preparation_time")
    val preparationTime: Int = 0,

    @SerializedName("addon_categories")
    val addOnCategories: List<AddOnCategory> = emptyList(),

    @SerializedName("addon_category_id")
    val addOnCategoryId: List<Int> = emptyList(),

    @SerializedName("canteen_id")
    val canteenId:Int =0,

    @SerializedName("canteen_name")
    val canteenName:String ="",

    @SerializedName("canteen_is_open")
    val isOpen: Boolean = true,
)
