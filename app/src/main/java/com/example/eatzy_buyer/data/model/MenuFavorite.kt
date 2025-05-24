package com.example.eatzy_buyer.data.model

import com.google.gson.annotations.SerializedName


data class MenuFavorite(
    @SerializedName("menu_id")
    val id: Int,

    @SerializedName("menu_name")
    val name: String,

    @SerializedName("menu_image")
    val imageUrl: String,

    @SerializedName("menu_price")
    val price: String,

    @SerializedName("menu_category")
    val menuCategory: MenuCategory,

//    @SerializedName("canteen_name")
//    val canteenName: String,
)
