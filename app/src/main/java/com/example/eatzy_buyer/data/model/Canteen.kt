package com.example.eatzy_buyer.data.model

import com.google.gson.annotations.SerializedName

data class Canteen(
    @SerializedName("canteen_id")
    val id: Int = 1,
    @SerializedName("canteen_name")
    val name: String = "",
    @SerializedName("canteen_image")
    val imageUrl: String = "",
    @SerializedName("canteen_is_open")
    val isOpen: Boolean = true,
    val menuCategoryId: List<Int>? = null
)
//data class Canteen(
//    @SerializedName("canteen_id")
//    val id: Int,
//
//    @SerializedName("canteen_name")
//    val name: String,
//
//    @SerializedName("canteen_image")
//    val url: String,
//
//    @SerializedName("canteen_is_open")
//    val isOpen: Int
//)
