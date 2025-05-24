package com.example.eatzy_buyer.data.model

import com.google.gson.annotations.SerializedName

data class Canteen(
    @SerializedName("canteen_id")
    val id: Int = 0,

    @SerializedName("canteen_name")
    val name: String = "",

    @SerializedName("canteen_image")
    val image: String = "",
)
