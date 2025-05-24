package com.example.eatzy_buyer.data.model

import com.google.gson.annotations.SerializedName

data class OrderItem(
    @SerializedName("order_item_id")
    val id: Int = 0,

    @SerializedName("order_id")
    val orderId: Int = 0,

    @SerializedName("menu")
    val menu: Menu = Menu(),

    @SerializedName("item_details")
    val details: String = "",

    @SerializedName("addons")
    val addOns: List<AddOn> = emptyList(),
)
