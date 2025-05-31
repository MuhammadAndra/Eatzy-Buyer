package com.example.eatzy_buyer.data.model

import com.google.gson.annotations.SerializedName

data class OrderItem(
    @SerializedName("order_item_id")
    val id: Int = 0,

    @SerializedName("order_id")
    val orderId: Int = 0,

    @SerializedName("menu_id")
    val menuId: Int = 0,

    @SerializedName("item_details")
    val details: String? = null,

    @SerializedName("menu_name")
    val menuName: String = "",

    @SerializedName("menu_image")
    val imageUrl: String = "",

    @SerializedName("menu")
    val menu: Menu = Menu(),

    val orderItemAddons:List<OrderItemAddon> = emptyList(),

    @SerializedName("order_item_addons")
    val addOns:List<AddOn> = emptyList()
)
