package com.example.eatzy_buyer.data.model

import com.google.gson.annotations.SerializedName

data class AddOn(
    @SerializedName("addon_id")
    val id: Int = 0,

    @SerializedName("menu_id")
    val menuId: Int = 0,

    @SerializedName("addon_name")
    val name: String = "",

    @SerializedName("addon_price")
    val price: Double = 0.00
)
